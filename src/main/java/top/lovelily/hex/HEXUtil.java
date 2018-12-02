package top.lovelily.hex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/9/25.
 */

public class HEXUtil {

    private Rom rom;
    private List<ROMRegion> regions;
    private List<Frame> frames;
    private int m_region_number;
    private long m_address_region;


    public HEXUtil()
    {
        regions=new ArrayList<ROMRegion>();
        for(int i=0;i<8;i++)
        {
            ROMRegion item=new ROMRegion();
            regions.add(item);
        }
        m_region_number=0;

        rom=new Rom();
    }
    /**
     * 获取解析后的ROM的厂商信息
     * @return
     */
    public int GetParsedROMFactID()
    {

        return rom.device_fact_id;
    }
    /**
     * 获取解析后的ROM的设备信息对应
     * @return
     */
    public int GetParsedROMDevType()
    {

        return rom.device_type;
    }

    /**
     * 处理帧数据
     */
    private void ROM_Process_Frame()
    {
        int framecount=rom.program_frame_count;

        if(framecount==0)return;

        frames=new ArrayList<Frame>();

        for(int i=0;i<framecount;i++)
        {
            Frame frame=new Frame();

            frame.frame_index=i;

            if(i<(framecount-1))
            {
                frame.frame_len=Frame.ROM_FRAME_SIZE;
            }else if(rom.program_len%Frame.ROM_FRAME_SIZE==0)//正好被切分
            {
                frame.frame_len=Frame.ROM_FRAME_SIZE;
            }else
            {
                frame.frame_len=rom.program_len%Frame.ROM_FRAME_SIZE;
            }

            int offset=i*Frame.ROM_FRAME_SIZE;//数据的偏移

            for(int j=0;j<frame.frame_len;j++)
            {
                frame.data[j]=rom.program_data[offset+j];
            }
            //记录偏移值
            frame.frame_addr_offset=offset;

            //package count
            frame.package_count=frame.frame_len/ ROMPackage.ROM_PACKAGE_SIZE;
            frame.package_size=ROMPackage.ROM_PACKAGE_SIZE;
            if(frame.frame_len%ROMPackage.ROM_PACKAGE_SIZE>0)
                frame.package_count++;

            frame.frame_crc= CRC.CRC_CCITT_16(0,frame.data,frame.frame_len);

            frames.add(frame);
        }

        rom.frames = frames;


    }

    /**
     * 处理文件一行
     * @param linetext
     * @return
     */
    private int ParseLine(String linetext)
    {
        char buf[]=linetext.toCharArray();
        int str_len=buf.length;

        if(str_len<6)return -1;
        if(buf[0]!=':')return -2;

        //校验
        byte calc_code=0,chk_code=0;
        int tmp_code;//读取的一个字节
        int offset=1;
        String s_tmp;//读取的字节的字符串
        for(int i=0;i<((str_len-1)/2-1);i++)
        {
            s_tmp=linetext.substring(offset, offset+2);
            offset+=2;
            tmp_code=Integer.valueOf(s_tmp, 16);
            calc_code+=tmp_code;
        }
        s_tmp=linetext.substring(offset, offset+2);
        offset+=2;
        chk_code=(byte)(Integer.valueOf(s_tmp, 16).intValue()&0xFF);
        calc_code=(byte)(256-calc_code);

        if(chk_code!=calc_code)return -3;

        //真正解析

        //行数据长度
        offset=1;//去除冒号
        s_tmp=linetext.substring(offset, offset+2);
        offset+=2;
        int line_datalen=Integer.valueOf(s_tmp, 16);

        //地址域
        s_tmp=linetext.substring(offset, offset+4);
        offset+=4;
        long line_address=Integer.valueOf(s_tmp, 16);

        //记录类型
        s_tmp=linetext.substring(offset, offset+2);
        offset+=2;
        int line_record_type=Integer.valueOf(s_tmp, 16);

        if(line_record_type==0x00)//数据记录
        {
            ROMRegion region_item=regions.get(m_region_number-1);
            if(region_item.get_first_address==false)
            {
                region_item.get_first_address=true;

                region_item.first_address=line_address;
            }

            for(int n=0;n<line_datalen;n++)
            {
                s_tmp=linetext.substring(offset, offset+2);
                offset+=2;
                int data=Integer.valueOf(s_tmp, 16);


                long fst_address=region_item.first_address;
                region_item.data[region_item.data_len++]=(byte)(data&0xff);

            }

        }else if(line_record_type==0x01)//文件结束标记
        {

        }else if(line_record_type==0x02)//扩展段地址记录
        {

        }else if(line_record_type==0x04)//扩展线型地址记录
        {
            s_tmp=linetext.substring(offset, offset+4);
            offset+=4;

            long add_r=Integer.valueOf(s_tmp, 16);
            m_address_region=add_r<<16;
            m_region_number++;
            //记录region地址
            ROMRegion region_item=regions.get(m_region_number-1);
            region_item.start_address=m_address_region;
        }

        return 0;
    }
    /**
     * 处理HEX文件解析结果
     */
    private void ProcessHexResult()
    {
        int rom_program_len=0;
        int rom_param_len=0;
        int i=0,j=0;

        for(i=0;i<m_region_number;i++)
        {
            ROMRegion region_item=regions.get(i);

            if(region_item.start_address==0x08000000L)
            {
                //拷贝程序
                for(j=0;j<region_item.data_len;j++)
                {
                    rom.program_data[rom_program_len+j]=
                            region_item.data[j];
                }
                rom_program_len+=region_item.data_len;
                //程序起始地址
                rom.program_start_address=region_item.start_address;


            }else if(region_item.start_address==0x08010000L)
            {
                //拷贝程序
                for(j=0;j<region_item.data_len;j++)
                {
                    rom.program_data[rom_program_len+j]=
                            region_item.data[j];
                }
                rom_program_len+=region_item.data_len;

            }else if(region_item.start_address==0x08020000L)
            {
                if(region_item.first_address==0x0)//继续是程序部分
                {
                    //拷贝程序
                    for(j=0;j<region_item.data_len;j++)
                    {
                        rom.program_data[rom_program_len+j]=
                                region_item.data[j];
                    }
                    rom_program_len+=region_item.data_len;

                }
                else if(region_item.first_address==0xE000)//确定是参数部分
                {
                    //拷贝参数
                    for(j=0;j<region_item.data_len;j++)
                    {
                        rom.param_data[rom_param_len+j]=
                                region_item.data[j];
                    }
                    rom_param_len+=region_item.data_len;

                    if(region_item.first_address==0xE000)
                    {
                        //参数起始地址
                        rom.param_start_address=region_item.start_address;

                    }
                }
            }

        }

        //程序长度
        rom.program_len=rom_program_len;
        //参数长度
        rom.param_len=rom_param_len;
        //程序帧数量
        if(rom_program_len%Frame.ROM_FRAME_SIZE==0)
        {
            rom.program_frame_count=rom_program_len/Frame.ROM_FRAME_SIZE;
        }else
        {
            rom.program_frame_count=rom_program_len/Frame.ROM_FRAME_SIZE+1;
        }

        //参数帧数量
        if(rom_param_len%Frame.ROM_FRAME_SIZE==0)
        {
            rom.param_frame_count=rom_param_len/Frame.ROM_FRAME_SIZE;
        }else
        {
            rom.param_frame_count=rom_param_len/Frame.ROM_FRAME_SIZE+1;
        }

        //计算程序CRC
        rom.program_crc=(CRC.CRC_CCITT_16(0, rom.program_data, rom.program_len)&0xFFFF);
        //计算参数CRC
        rom.param_crc=(CRC.CRC_CCITT_16(0, rom.param_data, rom.param_len)&0xFFFF);
        //读出软件版本信息
        int a =(int)(((rom.param_data[1]<<8)+rom.param_data[0])&0xFFFF);
        if (rom.param_data[0] < 0){
            rom.software_ver = a + 256;
        }else {
            rom.software_ver = a;
        }

        //读出厂商信息
        rom.device_fact_id=(int)((rom.param_data[2])&0xFF);
        //读取设备型号
        rom.device_type=(int)((rom.param_data[3])&0xFF);

        System.out.printf("Program Len:%d\r\n", rom.program_len);
        System.out.printf("Param Len:%d\r\n", rom.param_len);

        System.out.printf("Program CRC:0x%x\r\n", rom.program_crc);
        System.out.printf("Param CRC:0x%x\r\n", rom.param_crc);

        System.out.printf("Software_VER:0x%x\r\n", rom.software_ver);


        ROM_Process_Frame();
    }

    /**
     * 解析整个hex文件
     * @param file_name
     */
    public Rom ParseFile(String file_name)
    {
        BufferedReader br=null;
        int res=1;
        try
        {
            FileReader fr = new FileReader(file_name);
            br=new BufferedReader(fr);
            String linetext;//读取的一行文本

            while((linetext=br.readLine())!=null)
            {
                if((res=ParseLine(linetext))!=0)break;

            }
            br.close();
            fr.close();

        }catch(IOException e){
            e.printStackTrace();
        }finally
        {
            if(br!=null)
            {
                try{
                    br.close();
                }catch(IOException e1){}

            }

        }

        if(res==0)
        {
            ProcessHexResult();

        }

        return  rom;
    }


}

package top.lovelily.base;


import org.openjdk.jol.info.ClassLayout;
import top.lovelily.User;
import top.lovelily.util.ByteUtil;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by kaiitsugyou on 16/12/8.
 */
public class TestByte {
    public static void main(String[] args) {
        // 与运算
        System.out.println(7 & (8 - 1));
        System.out.println(8 & (8 - 1));

        System.out.println(7 >>> 1);

        String str = new String("L000001");
        System.out.println(str.getBytes().length); // 7

        // 2^0 + 2^1 + .... + 2^n = 2^n+1 - 1

        // 有符号
        byte b = 127;  // 最大的应该是0111 1111，因为第一位是符号位，0表示正数 2^7-1
        byte c = -128; // 最小的应该是1000 0000，因为第一位是符号位，1表示负数 -2^7

        // 无符号 0-255  00000000－－－11111111 2^8-1


        // 有符号
        short d = 32767;  // 2^15-1
        short e = -32768;  // -2^15

        // 无符号  0---65535



        int a = 1; //int 4个字节  -2^31   2^31 - 1

        //1111111111111111111111111111111     2^31 - 1
        //10000000000000000000000000000000    2^31

        //int max = (int) Math.pow(2, 31);


        long max = (long) Math.pow(2, 31) - 1;
        long min = -(long) Math.pow(2, 31);

        min = (long) Math.pow(2, 15);
        max = (long) (Math.pow(2, 15) - 1);

        System.out.println(min + "——" + max);

        // hashmap table[(len-1) & hash(key)]
        System.out.println(1 & 1);

        String zw = "字";
        // utf-8 3个字节 变长编码 1-6个字节
        System.out.println("一个中文UTF-8： " + zw.getBytes(Charset.forName("UTF-8")).length);
        // gbk 2个字节
        System.out.println("一个中文GBK：" + zw.getBytes(Charset.forName("GBK")).length);
        String yw = "a";
        // 1个字节
        System.out.println("一个非中文(UTF-8)："  + yw.getBytes().length);

        // UTF-16 使用2个字节来编码unicode, 遇到英文字母时会浪费空间，中文会比较省空间，比utf-8 少占一个字节
        System.out.println("一个非中文(UTF-16)："  + yw.getBytes(Charset.forName("UTF-16BE")).length);

        StringBuffer stringBuffer = new StringBuffer("CREATE TABLE car_cox (");
        for (int i = 0; i < 200; i++) {
            stringBuffer.append("co_").append(i).append(" int(11) DEFAULT NULL,");
            if (i == 199) {
                stringBuffer.append("co_").append(i).append(" int(11) DEFAULT NULL");
            }
        }
        stringBuffer.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");

        System.out.println(stringBuffer.toString());

        String s = "[{\"lat\":22.94195,\"lng\":113.93898},{\"lat\":22.9419,\"lng\":113.93901},{\"lat\":22.94736,\"lng\":113.93709},{\"lat\":22.94165,\"lng\":113.9392},{\"lat\":22.94158,\"lng\":113.93925},{\"lat\":22.94184,\"lng\":113.93907},{\"lat\":22.94181,\"lng\":113.93909},{\"lat\":22.94178,\"lng\":113.9391},{\"lat\":22.95988,\"lng\":113.94261},{\"lat\":22.94736,\"lng\":113.93708},{\"lat\":22.94175,\"lng\":113.93913},{\"lat\":22.94735,\"lng\":113.93707},{\"lat\":22.96137,\"lng\":113.9426},{\"lat\":22.95999,\"lng\":113.94261},{\"lat\":22.96009,\"lng\":113.94261},{\"lat\":22.94735,\"lng\":113.93707},{\"lat\":22.94173,\"lng\":113.93916},{\"lat\":22.94735,\"lng\":113.93706},{\"lat\":22.94155,\"lng\":113.93928},{\"lat\":22.9602,\"lng\":113.94262},{\"lat\":22.94604,\"lng\":113.92974},{\"lat\":22.94154,\"lng\":113.93929},{\"lat\":22.94604,\"lng\":113.9297},{\"lat\":22.94153,\"lng\":113.9393},{\"lat\":22.94603,\"lng\":113.92965},{\"lat\":22.94603,\"lng\":113.9296},{\"lat\":22.94602,\"lng\":113.92956},{\"lat\":22.94601,\"lng\":113.92952},{\"lat\":22.946,\"lng\":113.92948},{\"lat\":22.94599,\"lng\":113.92944},{\"lat\":22.94597,\"lng\":113.92941},{\"lat\":22.94596,\"lng\":113.92938},{\"lat\":22.94152,\"lng\":113.93931},{\"lat\":22.94152,\"lng\":113.93932},{\"lat\":22.94589,\"lng\":113.92931},{\"lat\":22.94139,\"lng\":113.93943},{\"lat\":22.94594,\"lng\":113.92936},{\"lat\":22.94152,\"lng\":113.93933},{\"lat\":22.94152,\"lng\":113.93934},{\"lat\":22.94135,\"lng\":113.93945},{\"lat\":22.94592,\"lng\":113.92934},{\"lat\":22.94151,\"lng\":113.93935},{\"lat\":22.9459,\"lng\":113.92933},{\"lat\":22.9413,\"lng\":113.93948},{\"lat\":22.94589,\"lng\":113.92932},{\"lat\":22.94151,\"lng\":113.93935},{\"lat\":22.94589,\"lng\":113.92931},{\"lat\":22.94129,\"lng\":113.93949},{\"lat\":22.94589,\"lng\":113.92928},{\"lat\":22.94586,\"lng\":113.92928},{\"lat\":22.94149,\"lng\":113.93937},{\"lat\":22.94125,\"lng\":113.93953},{\"lat\":22.94122,\"lng\":113.93956},{\"lat\":22.94148,\"lng\":113.93937},{\"lat\":22.94146,\"lng\":113.93938},{\"lat\":22.94352,\"lng\":113.9405},{\"lat\":22.94358,\"lng\":113.94053},{\"lat\":22.9437,\"lng\":113.94057},{\"lat\":22.94364,\"lng\":113.94056},{\"lat\":22.94375,\"lng\":113.94057},{\"lat\":22.9438,\"lng\":113.94055},{\"lat\":22.94385,\"lng\":113.9405},{\"lat\":22.9439,\"lng\":113.94046},{\"lat\":22.94583,\"lng\":113.92921},{\"lat\":22.94582,\"lng\":113.92916},{\"lat\":22.94408,\"lng\":113.94027},{\"lat\":22.94588,\"lng\":113.92928},{\"lat\":22.94394,\"lng\":113.94043},{\"lat\":22.94417,\"lng\":113.94018},{\"lat\":22.94585,\"lng\":113.92924},{\"lat\":22.94399,\"lng\":113.94037},{\"lat\":22.94443,\"lng\":113.93995},{\"lat\":22.9458,\"lng\":113.92912},{\"lat\":22.94427,\"lng\":113.94008},{\"lat\":22.94577,\"lng\":113.9291},{\"lat\":22.94432,\"lng\":113.94004},{\"lat\":22.94575,\"lng\":113.92907},{\"lat\":22.94437,\"lng\":113.93999},{\"lat\":22.94476,\"lng\":113.93966},{\"lat\":22.94403,\"lng\":113.94032},{\"lat\":22.94412,\"lng\":113.94022},{\"lat\":22.94498,\"lng\":113.93946},{\"lat\":22.94573,\"lng\":113.92903},{\"lat\":22.94514,\"lng\":113.93931},{\"lat\":22.94422,\"lng\":113.94013},{\"lat\":22.94547,\"lng\":113.93904},{\"lat\":22.94564,\"lng\":113.93891},{\"lat\":22.94573,\"lng\":113.93884},{\"lat\":22.94573,\"lng\":113.92899},{\"lat\":22.94574,\"lng\":113.92895},{\"lat\":22.94591,\"lng\":113.9387},{\"lat\":22.94609,\"lng\":113.93856},{\"lat\":22.94574,\"lng\":113.92891},{\"lat\":22.94618,\"lng\":113.93848},{\"lat\":22.94575,\"lng\":113.92886},{\"lat\":22.94576,\"lng\":113.92882},{\"lat\":22.94635,\"lng\":113.93833},{\"lat\":22.94653,\"lng\":113.93819},{\"lat\":22.94579,\"lng\":113.92878},{\"lat\":22.94449,\"lng\":113.93989},{\"lat\":22.94455,\"lng\":113.93984},{\"lat\":22.94462,\"lng\":113.93978},{\"lat\":22.94582,\"lng\":113.92875},{\"lat\":22.94469,\"lng\":113.93971},{\"lat\":22.94585,\"lng\":113.92872},{\"lat\":22.94484,\"lng\":113.93959},{\"lat\":22.94589,\"lng\":113.92868},{\"lat\":22.94491,\"lng\":113.93952},{\"lat\":22.94594,\"lng\":113.92865},{\"lat\":22.94599,\"lng\":113.92862},{\"lat\":22.94506,\"lng\":113.93938},{\"lat\":22.94522,\"lng\":113.93925},{\"lat\":22.9453,\"lng\":113.93918},{\"lat\":22.94539,\"lng\":113.93911},{\"lat\":22.94556,\"lng\":113.93897},{\"lat\":22.94604,\"lng\":113.92858},{\"lat\":22.94581,\"lng\":113.93878},{\"lat\":22.946,\"lng\":113.93862},{\"lat\":22.94609,\"lng\":113.92854},{\"lat\":22.94627,\"lng\":113.93841},{\"lat\":22.94644,\"lng\":113.93826},{\"lat\":22.94662,\"lng\":113.93811},{\"lat\":22.94671,\"lng\":113.93804},{\"lat\":22.94679,\"lng\":113.93796},{\"lat\":22.94807,\"lng\":113.93667},{\"lat\":22.94812,\"lng\":113.93663},{\"lat\":22.94615,\"lng\":113.9285},{\"lat\":22.94817,\"lng\":113.93659},{\"lat\":22.94621,\"lng\":113.92845},{\"lat\":22.94627,\"lng\":113.9284},{\"lat\":22.94634,\"lng\":113.92834},{\"lat\":22.94641,\"lng\":113.92829},{\"lat\":22.94648,\"lng\":113.92822},{\"lat\":22.9483,\"lng\":113.93643},{\"lat\":22.94829,\"lng\":113.93639},{\"lat\":22.94712,\"lng\":113.92774},{\"lat\":22.94714,\"lng\":113.92773},{\"lat\":22.94827,\"lng\":113.93635},{\"lat\":22.94825,\"lng\":113.93632},{\"lat\":22.9612,\"lng\":113.92915},{\"lat\":22.94824,\"lng\":113.9363},{\"lat\":22.9612,\"lng\":113.92918},{\"lat\":22.96119,\"lng\":113.92922},{\"lat\":22.9482,\"lng\":113.93628},{\"lat\":22.96118,\"lng\":113.92927},{\"lat\":22.96119,\"lng\":113.92931},{\"lat\":22.94813,\"lng\":113.93623},{\"lat\":22.94806,\"lng\":113.93618},{\"lat\":22.9612,\"lng\":113.92936},{\"lat\":22.96123,\"lng\":113.9294},{\"lat\":22.96127,\"lng\":113.92945},{\"lat\":22.96133,\"lng\":113.92949},{\"lat\":22.94772,\"lng\":113.93604},{\"lat\":22.94761,\"lng\":113.93599},{\"lat\":22.96139,\"lng\":113.92953},{\"lat\":22.94756,\"lng\":113.93596},{\"lat\":22.96145,\"lng\":113.92957},{\"lat\":22.9615,\"lng\":113.92962},{\"lat\":22.94751,\"lng\":113.93593},{\"lat\":22.948,\"lng\":113.93617},{\"lat\":22.94721,\"lng\":113.93575},{\"lat\":22.96156,\"lng\":113.92966},{\"lat\":22.94795,\"lng\":113.93614},{\"lat\":22.94719,\"lng\":113.93573},{\"lat\":22.94789,\"lng\":113.93612},{\"lat\":22.94784,\"lng\":113.93609},{\"lat\":22.94778,\"lng\":113.93606},{\"lat\":22.96163,\"lng\":113.9297},{\"lat\":22.9617,\"lng\":113.92973},{\"lat\":22.96177,\"lng\":113.92977},{\"lat\":22.94748,\"lng\":113.93591},{\"lat\":22.96184,\"lng\":113.92979},{\"lat\":22.94744,\"lng\":113.9359},{\"lat\":22.96191,\"lng\":113.92982},{\"lat\":22.96198,\"lng\":113.92986},{\"lat\":22.96233,\"lng\":113.93009},{\"lat\":22.96236,\"lng\":113.93015},{\"lat\":22.94742,\"lng\":113.93589},{\"lat\":22.9474,\"lng\":113.93588},{\"lat\":22.96237,\"lng\":113.9302},{\"lat\":22.94739,\"lng\":113.93587},{\"lat\":22.94738,\"lng\":113.93587},{\"lat\":22.94737,\"lng\":113.93587},{\"lat\":22.96237,\"lng\":113.93027},{\"lat\":22.94736,\"lng\":113.93586},{\"lat\":22.94735,\"lng\":113.93585},{\"lat\":22.96236,\"lng\":113.93032},{\"lat\":22.94734,\"lng\":113.93585},{\"lat\":22.94734,\"lng\":113.93584},{\"lat\":22.94733,\"lng\":113.93584},{\"lat\":22.94732,\"lng\":113.93583},{\"lat\":22.94732,\"lng\":113.93583},{\"lat\":22.94731,\"lng\":113.93582},{\"lat\":22.94718,\"lng\":113.93573},{\"lat\":22.94717,\"lng\":113.93572},{\"lat\":22.94715,\"lng\":113.93572},{\"lat\":22.94714,\"lng\":113.93571},{\"lat\":22.96234,\"lng\":113.93038},{\"lat\":22.96232,\"lng\":113.93045},{\"lat\":22.9623,\"lng\":113.93052},{\"lat\":22.96227,\"lng\":113.93061},{\"lat\":22.96224,\"lng\":113.93069},{\"lat\":22.96204,\"lng\":113.92989},{\"lat\":22.94714,\"lng\":113.93571},{\"lat\":22.96212,\"lng\":113.92992},{\"lat\":22.96218,\"lng\":113.92996},{\"lat\":22.96224,\"lng\":113.92999},{\"lat\":22.94731,\"lng\":113.93582},{\"lat\":22.96229,\"lng\":113.93004},{\"lat\":22.96221,\"lng\":113.93078},{\"lat\":22.9473,\"lng\":113.93581},{\"lat\":22.96216,\"lng\":113.93087},{\"lat\":22.94729,\"lng\":113.93581},{\"lat\":22.94728,\"lng\":113.9358},{\"lat\":22.96212,\"lng\":113.93098},{\"lat\":22.94727,\"lng\":113.93579},{\"lat\":22.96207,\"lng\":113.93108},{\"lat\":22.94651,\"lng\":113.9326},{\"lat\":22.94652,\"lng\":113.93254},{\"lat\":22.94653,\"lng\":113.93247},{\"lat\":22.94644,\"lng\":113.9319},{\"lat\":22.94643,\"lng\":113.93187},{\"lat\":22.96202,\"lng\":113.93119},{\"lat\":22.94653,\"lng\":113.93209},{\"lat\":22.94651,\"lng\":113.93204},{\"lat\":22.96196,\"lng\":113.9313},{\"lat\":22.9619,\"lng\":113.93141},{\"lat\":22.96185,\"lng\":113.93153},{\"lat\":22.96179,\"lng\":113.93165},{\"lat\":22.96173,\"lng\":113.93178},{\"lat\":22.9465,\"lng\":113.932},{\"lat\":22.94648,\"lng\":113.93196},{\"lat\":22.94646,\"lng\":113.93193},{\"lat\":22.96167,\"lng\":113.9319},{\"lat\":22.96161,\"lng\":113.93203},{\"lat\":22.94641,\"lng\":113.93184},{\"lat\":22.9464,\"lng\":113.93181},{\"lat\":22.96155,\"lng\":113.93216},{\"lat\":22.96148,\"lng\":113.93229},{\"lat\":22.94638,\"lng\":113.93178},{\"lat\":22.94636,\"lng\":113.93174},{\"lat\":22.94634,\"lng\":113.93171},{\"lat\":22.94631,\"lng\":113.93167},{\"lat\":22.94629,\"lng\":113.93163},{\"lat\":22.94626,\"lng\":113.93158},{\"lat\":22.94611,\"lng\":113.93122},{\"lat\":22.94609,\"lng\":113.93116},{\"lat\":22.94608,\"lng\":113.93109},{\"lat\":22.94607,\"lng\":113.93102},{\"lat\":22.94605,\"lng\":113.93095},{\"lat\":22.94621,\"lng\":113.93147},{\"lat\":22.94616,\"lng\":113.93135},{\"lat\":22.94604,\"lng\":113.9308},{\"lat\":22.94603,\"lng\":113.93065},{\"lat\":22.94624,\"lng\":113.93153},{\"lat\":22.94618,\"lng\":113.93141},{\"lat\":22.94613,\"lng\":113.93129},{\"lat\":22.94604,\"lng\":113.93087},{\"lat\":22.94603,\"lng\":113.93073},{\"lat\":22.94602,\"lng\":113.93058},{\"lat\":22.94603,\"lng\":113.93052},{\"lat\":22.94603,\"lng\":113.93045},{\"lat\":22.94603,\"lng\":113.93038},{\"lat\":22.94603,\"lng\":113.93032},{\"lat\":22.94603,\"lng\":113.93026},{\"lat\":22.94603,\"lng\":113.93021},{\"lat\":22.94603,\"lng\":113.93016},{\"lat\":22.94604,\"lng\":113.93011},{\"lat\":22.94604,\"lng\":113.93006},{\"lat\":22.94605,\"lng\":113.93001},{\"lat\":22.94605,\"lng\":113.92996},{\"lat\":22.94605,\"lng\":113.92991},{\"lat\":22.94605,\"lng\":113.92987},{\"lat\":22.94605,\"lng\":113.92982},{\"lat\":22.94605,\"lng\":113.92979},{\"lat\":22.94835,\"lng\":113.92693},{\"lat\":22.9484,\"lng\":113.9269},{\"lat\":22.94858,\"lng\":113.92677},{\"lat\":22.9486,\"lng\":113.92677},{\"lat\":22.94861,\"lng\":113.92677},{\"lat\":22.94863,\"lng\":113.92677},{\"lat\":22.94864,\"lng\":113.92677},{\"lat\":22.94864,\"lng\":113.92677},{\"lat\":22.94865,\"lng\":113.92676},{\"lat\":22.94867,\"lng\":113.92674},{\"lat\":22.94868,\"lng\":113.92673},{\"lat\":22.94868,\"lng\":113.92672},{\"lat\":22.94862,\"lng\":113.92671},{\"lat\":22.94861,\"lng\":113.92672},{\"lat\":22.94865,\"lng\":113.92671},{\"lat\":22.94866,\"lng\":113.9267},{\"lat\":22.94863,\"lng\":113.92671},{\"lat\":22.94867,\"lng\":113.9267},{\"lat\":22.94871,\"lng\":113.92668},{\"lat\":22.94871,\"lng\":113.92668},{\"lat\":22.94872,\"lng\":113.92668},{\"lat\":22.94868,\"lng\":113.92669},{\"lat\":22.94868,\"lng\":113.92669},{\"lat\":22.94869,\"lng\":113.92669},{\"lat\":22.94872,\"lng\":113.92667},{\"lat\":22.94873,\"lng\":113.92668},{\"lat\":22.94873,\"lng\":113.92667},{\"lat\":22.94872,\"lng\":113.92663},{\"lat\":22.94872,\"lng\":113.92661},{\"lat\":22.94872,\"lng\":113.9266},{\"lat\":22.94872,\"lng\":113.92668},{\"lat\":22.94872,\"lng\":113.92658},{\"lat\":22.94872,\"lng\":113.92667},{\"lat\":22.94873,\"lng\":113.92666},{\"lat\":22.94873,\"lng\":113.92665},{\"lat\":22.94873,\"lng\":113.92664},{\"lat\":22.94873,\"lng\":113.92661},{\"lat\":22.94873,\"lng\":113.92656},{\"lat\":22.94887,\"lng\":113.9264},{\"lat\":22.94896,\"lng\":113.92634},{\"lat\":22.94906,\"lng\":113.92627},{\"lat\":22.94911,\"lng\":113.92623},{\"lat\":22.94923,\"lng\":113.92614},{\"lat\":22.94874,\"lng\":113.92654},{\"lat\":22.94874,\"lng\":113.92652},{\"lat\":22.94936,\"lng\":113.92602},{\"lat\":22.9518,\"lng\":113.92404},{\"lat\":22.95182,\"lng\":113.92403},{\"lat\":22.95184,\"lng\":113.92402},{\"lat\":22.95186,\"lng\":113.92401},{\"lat\":22.95188,\"lng\":113.92402},{\"lat\":22.95189,\"lng\":113.92402},{\"lat\":22.9519,\"lng\":113.92403},{\"lat\":22.95191,\"lng\":113.92403},{\"lat\":22.952,\"lng\":113.92374},{\"lat\":22.95201,\"lng\":113.92373},{\"lat\":22.95202,\"lng\":113.92373},{\"lat\":22.95204,\"lng\":113.92373},{\"lat\":22.95207,\"lng\":113.92373},{\"lat\":22.95209,\"lng\":113.92372},{\"lat\":22.95212,\"lng\":113.9237},{\"lat\":22.95216,\"lng\":113.9237},{\"lat\":22.9522,\"lng\":113.9237},{\"lat\":22.9529,\"lng\":113.92413},{\"lat\":22.95223,\"lng\":113.92369},{\"lat\":22.95305,\"lng\":113.92422},{\"lat\":22.95228,\"lng\":113.9237},{\"lat\":22.9532,\"lng\":113.92432},{\"lat\":22.95231,\"lng\":113.92374},{\"lat\":22.95235,\"lng\":113.92376},{\"lat\":22.95328,\"lng\":113.92437},{\"lat\":22.95239,\"lng\":113.92379},{\"lat\":22.95344,\"lng\":113.92447},{\"lat\":22.9536,\"lng\":113.92457},{\"lat\":22.95244,\"lng\":113.92383},{\"lat\":22.95249,\"lng\":113.92387},{\"lat\":22.95256,\"lng\":113.92391},{\"lat\":22.95262,\"lng\":113.92394},{\"lat\":22.95269,\"lng\":113.92399},{\"lat\":22.95276,\"lng\":113.92405},{\"lat\":22.95283,\"lng\":113.92409},{\"lat\":22.95298,\"lng\":113.92417},{\"lat\":22.95312,\"lng\":113.92427},{\"lat\":22.95336,\"lng\":113.92442},{\"lat\":22.95352,\"lng\":113.92452},{\"lat\":22.95369,\"lng\":113.92462},{\"lat\":22.95377,\"lng\":113.92466},{\"lat\":22.95386,\"lng\":113.92471},{\"lat\":22.95395,\"lng\":113.92476},{\"lat\":22.95404,\"lng\":113.92482},{\"lat\":22.95414,\"lng\":113.92487},{\"lat\":22.96141,\"lng\":113.93242},{\"lat\":22.96135,\"lng\":113.93254},{\"lat\":22.96128,\"lng\":113.93267},{\"lat\":22.96121,\"lng\":113.9328},{\"lat\":22.96114,\"lng\":113.93292},{\"lat\":22.96108,\"lng\":113.93304},{\"lat\":22.96101,\"lng\":113.93317},{\"lat\":22.96095,\"lng\":113.93329},{\"lat\":22.96089,\"lng\":113.93342},{\"lat\":22.96083,\"lng\":113.93354},{\"lat\":22.96078,\"lng\":113.93366},{\"lat\":22.96072,\"lng\":113.93378},{\"lat\":22.96066,\"lng\":113.9339},{\"lat\":22.9606,\"lng\":113.93401},{\"lat\":22.96054,\"lng\":113.93411},{\"lat\":22.96049,\"lng\":113.93422},{\"lat\":22.96043,\"lng\":113.93432},{\"lat\":22.96038,\"lng\":113.93442},{\"lat\":22.96033,\"lng\":113.93452},{\"lat\":22.96028,\"lng\":113.93462},{\"lat\":22.96023,\"lng\":113.93471},{\"lat\":22.96018,\"lng\":113.9348},{\"lat\":22.96012,\"lng\":113.9349},{\"lat\":22.96007,\"lng\":113.93499},{\"lat\":22.96002,\"lng\":113.93508},{\"lat\":22.95997,\"lng\":113.93517},{\"lat\":22.95992,\"lng\":113.93527},{\"lat\":22.95986,\"lng\":113.93537},{\"lat\":22.95981,\"lng\":113.93548},{\"lat\":22.95975,\"lng\":113.93558},{\"lat\":22.9597,\"lng\":113.93569},{\"lat\":22.95902,\"lng\":113.93694},{\"lat\":22.95896,\"lng\":113.93705},{\"lat\":22.9589,\"lng\":113.93717},{\"lat\":22.95884,\"lng\":113.93728},{\"lat\":22.95877,\"lng\":113.93739},{\"lat\":22.95964,\"lng\":113.9358},{\"lat\":22.95958,\"lng\":113.93591},{\"lat\":22.95952,\"lng\":113.93603},{\"lat\":22.95946,\"lng\":113.93614},{\"lat\":22.9594,\"lng\":113.93626},{\"lat\":22.95933,\"lng\":113.93637},{\"lat\":22.95927,\"lng\":113.93649},{\"lat\":22.95921,\"lng\":113.9366},{\"lat\":22.95915,\"lng\":113.93671},{\"lat\":22.95908,\"lng\":113.93682},{\"lat\":22.95871,\"lng\":113.93751},{\"lat\":22.95974,\"lng\":113.94257},{\"lat\":22.95948,\"lng\":113.94255},{\"lat\":22.95998,\"lng\":113.94258},{\"lat\":22.95986,\"lng\":113.94257},{\"lat\":22.95961,\"lng\":113.94256},{\"lat\":22.95898,\"lng\":113.94251},{\"lat\":22.95935,\"lng\":113.94254},{\"lat\":22.95872,\"lng\":113.94248},{\"lat\":22.95923,\"lng\":113.94253},{\"lat\":22.9591,\"lng\":113.94252},{\"lat\":22.95848,\"lng\":113.94244},{\"lat\":22.95885,\"lng\":113.94249},{\"lat\":22.95824,\"lng\":113.9424},{\"lat\":22.9586,\"lng\":113.94246},{\"lat\":22.95836,\"lng\":113.94242},{\"lat\":22.95812,\"lng\":113.94238},{\"lat\":22.958,\"lng\":113.94236},{\"lat\":22.95789,\"lng\":113.94234},{\"lat\":22.95778,\"lng\":113.94233},{\"lat\":22.95767,\"lng\":113.94231},{\"lat\":22.95757,\"lng\":113.9423},{\"lat\":22.95748,\"lng\":113.94228},{\"lat\":22.9574,\"lng\":113.94227},{\"lat\":22.95732,\"lng\":113.94226},{\"lat\":22.95725,\"lng\":113.94224},{\"lat\":22.95696,\"lng\":113.94216},{\"lat\":22.95693,\"lng\":113.94215},{\"lat\":22.95689,\"lng\":113.94214},{\"lat\":22.95687,\"lng\":113.94214},{\"lat\":22.95684,\"lng\":113.94213},{\"lat\":22.95719,\"lng\":113.94222},{\"lat\":22.95714,\"lng\":113.94221},{\"lat\":22.95483,\"lng\":113.93833},{\"lat\":22.95492,\"lng\":113.93899},{\"lat\":22.95479,\"lng\":113.93817},{\"lat\":22.95492,\"lng\":113.93893},{\"lat\":22.95492,\"lng\":113.93886},{\"lat\":22.95483,\"lng\":113.93841},{\"lat\":22.95481,\"lng\":113.93825},{\"lat\":22.95477,\"lng\":113.93809},{\"lat\":22.95476,\"lng\":113.93801},{\"lat\":22.95474,\"lng\":113.93793},{\"lat\":22.95474,\"lng\":113.93785},{\"lat\":22.9547,\"lng\":113.93779},{\"lat\":22.95465,\"lng\":113.93767},{\"lat\":22.95467,\"lng\":113.93773},{\"lat\":22.95459,\"lng\":113.93757},{\"lat\":22.95461,\"lng\":113.93762},{\"lat\":22.95457,\"lng\":113.93753},{\"lat\":22.95456,\"lng\":113.93749},{\"lat\":22.95455,\"lng\":113.93746},{\"lat\":22.95454,\"lng\":113.93743},{\"lat\":22.95452,\"lng\":113.93737},{\"lat\":22.95453,\"lng\":113.9374},{\"lat\":22.95451,\"lng\":113.93735},{\"lat\":22.9545,\"lng\":113.93728},{\"lat\":22.95447,\"lng\":113.93718},{\"lat\":22.95445,\"lng\":113.93713},{\"lat\":22.95443,\"lng\":113.93707},{\"lat\":22.95441,\"lng\":113.93702},{\"lat\":22.95438,\"lng\":113.93697},{\"lat\":22.95436,\"lng\":113.93692},{\"lat\":22.95426,\"lng\":113.93669},{\"lat\":22.95425,\"lng\":113.93662},{\"lat\":22.95451,\"lng\":113.93731},{\"lat\":22.95423,\"lng\":113.93655},{\"lat\":22.95449,\"lng\":113.93723},{\"lat\":22.95432,\"lng\":113.93688},{\"lat\":22.95429,\"lng\":113.93685},{\"lat\":22.95427,\"lng\":113.93681},{\"lat\":22.9542,\"lng\":113.93637},{\"lat\":22.95426,\"lng\":113.93677},{\"lat\":22.95426,\"lng\":113.93673},{\"lat\":22.95425,\"lng\":113.93666},{\"lat\":22.95424,\"lng\":113.93659},{\"lat\":22.95422,\"lng\":113.93651},{\"lat\":22.95421,\"lng\":113.93646},{\"lat\":22.9542,\"lng\":113.93641},{\"lat\":22.95419,\"lng\":113.93631},{\"lat\":22.94801,\"lng\":113.93607},{\"lat\":22.94799,\"lng\":113.93606},{\"lat\":22.94797,\"lng\":113.93605},{\"lat\":22.94795,\"lng\":113.93603},{\"lat\":22.94794,\"lng\":113.93602},{\"lat\":22.94792,\"lng\":113.93601},{\"lat\":22.9479,\"lng\":113.936},{\"lat\":22.94788,\"lng\":113.93599},{\"lat\":22.94786,\"lng\":113.93598},{\"lat\":22.94784,\"lng\":113.93597},{\"lat\":22.94759,\"lng\":113.93585},{\"lat\":22.94759,\"lng\":113.93586},{\"lat\":22.94758,\"lng\":113.93587},{\"lat\":22.94757,\"lng\":113.93588},{\"lat\":22.94755,\"lng\":113.9359}]";
        System.out.println(s.getBytes().length );

        System.out.println(10>>1);

        // -javaagent:"/Users/xuhe/IdeaProject/Test/src/main/java/top/lovelily/base/InstrumentationAgent.jar"

        // jar cmf MANIFEST.MF InstrumentationAgent.jar top/lovelily/base/InstrumentationAgent.class


        //System.out.println(InstrumentationAgent.getObjectSize(new Object())); // 16 字节
        //User user = new User(1, "xuhe123xuhe", 28, 173, "Shanghai, China");
        //System.out.println(InstrumentationAgent.getObjectSize(user));  // 32 bytes


        byte[] company = new byte[16];

        byte[] discovery = "discovery".getBytes(); // [100, 105, 115, 99, 111, 118, 101, 114, 121]  java是大端模式
        System.arraycopy(discovery, 0, company, 0, discovery.length);

        ByteBuffer byteBuffer = ByteBuffer.wrap(discovery).order(ByteOrder.LITTLE_ENDIAN); // 大小端不影响， 并没有排序
        discovery = byteBuffer.array();
        byteBuffer.put("discovery".getBytes());
        System.out.println(Arrays.toString(discovery)); // 大端


        System.out.println("=============================");
        System.out.println("discovery".toCharArray().length);  // 9 个 char
        CharBuffer.wrap(new char[9]).order();
        byteBuffer = ByteBuffer.wrap(new byte[discovery.length*2]).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.asCharBuffer().put("discovery");   // Character use utf-16 2B
        System.out.println(Arrays.toString(byteBuffer.array()));

        char ch = 'c';
        System.out.println();

        byte[] str_LE = string2Bytes_LE("discovery");
        System.out.println(Arrays.toString(str_LE));

//        String hex = ByteUtil.bytes2Hex("Wiki".getBytes());
//        System.out.println(hex); // 0x
//        int wiki =  Integer.parseInt(hex, 16);
//        ByteBuffer bb =  ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN); // Wiki,   LD:  ikiW
//        bb.putInt(wiki);
//        System.out.println(new String(bb.array()));

        String hex = ByteUtil.bytes2Hex("discovery".getBytes());
        ByteBuffer bb =  ByteBuffer.allocate(18).order(ByteOrder.LITTLE_ENDIAN); // Wiki,   LD:  ikiW
        //bb.put(hex.getBytes());
        BigInteger bigInteger = new BigInteger(hex, 16);
        System.out.println(bigInteger);



        byte ub = (byte) 0x87;
        System.out.println(ub);

       int cb  =  ub & 0xff;
        System.out.println(cb);

        char A = 'A';
        byte A1 = (byte) A;
        System.out.println(A1);
        Character character = new Character('c');
        System.out.println(ClassLayout.parseInstance(character).toPrintable());

        System.out.println("xxxxxxxxxxxxxxxxxxxxxx");
        System.out.println(8028 & 0xFF); // 取一个字节，忽略其他字节 0x1F5C & 0xFF  0x5C(92)

    }

    private static byte[] chars2Bytes_LE(char[] chars){
        if(chars == null)
            return null;

        int iCharCount = chars.length;
        byte[] rst = new byte[iCharCount*2]; // utf16
        int i = 0;
        for( i = 0; i < iCharCount; i++){
            rst[i*2] = (byte)(chars[i] & 0xFF);
            rst[i*2 + 1] = (byte)(( chars[i] & 0xFF00 ) >> 8);
        }

        return rst;
    }

    private static byte[] string2Bytes_LE(String str) {
        if(str == null){
            return null;
        }
        char[] chars = str.toCharArray();

        byte[] rst = chars2Bytes_LE(chars);

        return rst;
    }
}

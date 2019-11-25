package top.lovelily.io.bio;

/**
 * Desc: TestHttpProxy
 * Author: xuhe
 * Date: 2019/10/24 6:00 下午
 * Version: 1.0
 *
 * firefox设置http代理， 指向127.0.0.1 9080进行测试
 * todo:
 *    http 代理通过 socket实现
 *    socks5 呢？
 *
 */
import java.lang.reflect.Constructor;
import java.net.*;
import java.io.*;
public class TestHttpProxy extends Thread {

    //操作实现代理服务器的类
    static public int RETRIES = 5;
    //在放弃之前尝试连接远程主机的次数
    static public int PAUSE = 5;
    //在两次连接尝试之间的暂停时间
    static public int TIMEOUT = 50;
    //等待Socket输入的等待时间
    static public int BUFSIZ = 1024;
    //输入的缓冲大小
    static public boolean logging = false;
    //是否要求代理服务器在日志中记录所有已传输的数据
    static public OutputStream log = null;
    //默认日志例程将向该OutputStream对象输出日志信息
    protected Socket socket;
    // 传入数据用的Socket
    static private String parent = null;
    //上级代理服务器
    static private int parentPort = -1;
    //  用来把一个代理服务器链接到另一个代理服务器（需要指定另一个服务器的名称和端口）。
    static public void setParentProxy(String name, int port) {

        parent = name;
        parentPort = port;

    }
    public TestHttpProxy(Socket s) {

        //创建一个代理线程
        socket = s;
        start();
        //启动线程

    }
    public void writeLog(int c, boolean browser) throws IOException {

        //写日志
        log.write(c);

    }
    public void writeLog(byte[] bytes, int offset, int len, boolean browser)
            throws IOException {

        //循环写日志
        for (int i = 0; i < len; i++)
            writeLog((int) bytes[offset + i], browser);

    }
    // 默认情况下，日志信息输出到控制台或文件
    public String printLog(String url, String host, int port, Socket sock) {

        java.text.DateFormat cal = java.text.DateFormat.getDateTimeInstance();
        System.out.println(cal.format(new java.util.Date()) + " - " + url + " "
                + sock.getInetAddress() + "\n");
        return host;

    }
    public void run() {

        // 执行操作的线程
        String line;
        String host;
        int port = 80;
        //默认端口为80
        Socket outbound = null;
        //每次请求都会创建一个新的线程
        try {

            socket.setSoTimeout(TIMEOUT);
            //设置超时时间
            InputStream is = socket.getInputStream();
            //创建输入流
            OutputStream os = null;
            try {

                line = "";
                // 获取请求行的内容
                host = "";
                int state = 0;
                boolean space;
                while (true) {

                    //无限循环
                    int c = is.read();
                    //读取输入流的信息
                    if (c == -1)//没有读取信息
                        break;
                    if (logging)
                        writeLog(c, true);
                    //将信息写入日志
                    space = Character.isWhitespace((char) c);
                    //判断是否为空白字符
                    switch (state) {

                        //判断状态
                        case 0:
                            if (space)
                                continue;
                            //跳过本次循环
                            state = 1;
                            //更改状态
                        case 1:
                            if (space) {

                                state = 2;
                                continue;
                                //跳过本次循环

                            }
                            line = line + (char) c;
                            //添加读取的信息
                            break;
                        case 2:
                            if (space)
                                continue;
                            // 跳过空白字符
                            state = 3;
                            //更改状态
                        case 3:
                            if (space) {

                                //如果是空白字符
                                state = 4;
                                //更改状态
                                String host0 = host;
                                //取出网址
                                int n;
                                n = host.indexOf("//");
                                //获取网址（不包括协议）
                                if (n != -1)
                                    //没有找到
                                    host = host.substring(n + 2);
                                n = host.indexOf('/');
                                if (n != -1)
                                    //没有找到/
                                    host = host.substring(0, n);
                                n = host.indexOf(":");
                                // 分析可能存在的端口号
                                if (n != -1) {

                                    //没有找到:
                                    port = Integer.parseInt(host.substring(n + 1));
                                    host = host.substring(0, n);

                                }
                                host = printLog(host0, host, port, socket);
                                //获得网站域名

                                if (parent != null) {

                                    host = parent;
                                    port = parentPort;

                                }
                                int retry = RETRIES;
                                while (retry-- != 0) {

                                    try {

                                        outbound = new Socket(host, port);
                                        //创建连接对象，通向目标服务器
                                        break;

                                    } catch (Exception e) {

                                        System.out.println("无法创建连接："+e.getMessage());

                                    }
                                    Thread.sleep(PAUSE);
                                    //设置线程等待

                                }
                                if (outbound == null)
                                    break;
                                outbound.setSoTimeout(TIMEOUT);
                                //设置超时时间，防止read方法导致的组赛
                                os = outbound.getOutputStream();
                                //获得输出流对象
                                os.write(line.getBytes());
                                //将信息写入流
                                os.write(' ');
                                os.write(host0.getBytes());
                                //将信息写入流
                                os.write(' ');
                                writeInfo(is, outbound.getInputStream(), os, socket
                                        .getOutputStream());
                                //调用方法将信息写入日志，套接字数据的交换
                                break;

                            }
                            host = host + (char) c;
                            break;

                    }

                }

            } catch (IOException e) {

            }

        } catch (Exception e) {

        } finally {

            try {

                socket.close();
                //释放资源

            } catch (Exception e1) {

            }
            try {

                outbound.close();

            } catch (Exception e2) {

            }
        }
    }
    void writeInfo(InputStream is0, InputStream is1, OutputStream os0,
                   OutputStream os1) throws IOException {

        //读取流中信息写入日志
        try {

            int ir;
            byte bytes[] = new byte[BUFSIZ];
            //创建字节数组，大小：1024
            //也是定影socket缓冲区的大小
            while (true) {

                try {

                    if ((ir = is0.read(bytes)) > 0) {

                        //判断读取输入流的信息
                        os0.write(bytes, 0, ir);
                        //将读取的数据写入输出流对象中

                        if (logging)
                            writeLog(bytes, 0, ir, true);
                        //写入日志

                    } else if (ir < 0)
                        //读取完毕
                        break;
                } catch (InterruptedIOException e) {

                    //捕获中断IO流异常

                }
                try {

                    if ((ir = is1.read(bytes)) > 0) {

                        //判断读取输入流的信息
                        os1.write(bytes, 0, ir);
                        //将读取的数据写入输出流对象中
                        if (logging)
                            writeLog(bytes, 0, ir, false);
                        //写入日志

                    } else if (ir < 0)
                        //读取完毕
                        break;

                } catch (InterruptedIOException e) {

                    //捕获中断IO流异常

                }
            }
        } catch (Exception e0) {

            //捕获异常
        }
    }


    static public void proxyStart(int port, Class<TestHttpProxy> clobj) {

        ServerSocket serverSocket;
        try {

            serverSocket = new ServerSocket(port);
            //根据端口创建服务器端Socket对象
            while (true) {

                Class[] objClass = new Class[1];
                //创建类数组，大小为1
                Object[] obj = new Object[1];
                //创建对象数组，大小为1
                objClass[0] = Socket.class;
                //添加Socket类
                try {

                    Constructor cons = clobj.getDeclaredConstructor(objClass);
                    //创建代理服务器实例
                    obj[0] = serverSocket.accept();
                    //挂起等待客户的请求
                    cons.newInstance(obj);
                    // 创建TextHttpProxy或其派生类的实例  创建传入类

                } catch (Exception e) {

                    Socket socket = (Socket) obj[0];
                    //对象强制转换
                    try {

                        socket.close();
                        //释放资源

                    } catch (Exception ec) {

                    }

                }

            }

        } catch (IOException e) {

        }
    }
    static public void main(String args[]) {

        System.out.println("HTTP代理服务器已经成功启动！");
        TestHttpProxy.log = System.out;
        //日志信息输出到控制台
        TestHttpProxy.logging = false;
        TestHttpProxy.proxyStart(9080, TestHttpProxy.class);

        //调用方法
    }

}
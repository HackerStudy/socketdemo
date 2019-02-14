import com.sun.corba.se.spi.orbutil.fsm.Input;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @class: Client
 * @description: Socket的客户端
 * @author: yangpeng03614
 * @date: 2019-02-14 13:20
 */
public class Client {
    private static final int port = 8800; //服务端的端口号

    private static final String host = "localhost"; //服务端的host

    /**
     * 客户端的main函数
     * @param args
     */
    public static void main(String[] args) {
        Socket socket  = null;
        try{
            socket = new Socket(host,port);
            System.out.println("连接服务端发送数据。。");
            //发送数据
            OutputStream out = socket.getOutputStream();
            System.out.println("用户开始发言：");
            String outData = new BufferedReader(new InputStreamReader(System.in)).readLine();
            byte[] outAarry = outData.getBytes();
            out.write(outAarry);
//            out.flush();
//            out.close();
            socket.shutdownOutput();
            //接收数据
            InputStream in =socket.getInputStream();
            int recvMsgSize = 0;
            byte[] receivBuf = new byte[32]; //接收字节的缓冲数组
            System.out.println("接收到来自服务端的回复：");
            while((recvMsgSize = in.read(receivBuf))!= -1){
                String inData = new String(receivBuf);
//                inData=String.copyValueOf(inData.toCharArray(), 0, receivBuf.length);
                System.out.println(inData);
            }
//            out.close();
//            in.close();
            socket.shutdownInput();
        }catch (UnknownHostException e){
            e.printStackTrace();
            System.out.println("客户端UnknownHostException："+e.getMessage());
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("客户端IOException："+e.getMessage());
        }
        finally {
            //释放Socket资源
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    System.out.println("客户端 finally 异常:" + e.getMessage());
                }
            }
        }
    }
}

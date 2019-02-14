import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @class: ServerThread
 * @description: 服务端socket与客户端的socket建立连接，并分配线程处理
 * @author: yangpeng03614
 * @date: 2019-02-14 10:28
 */
public class ServerThread implements Runnable{
    private Socket socket;
    private SocketAddress address;
    private int recvMsgSize = 0;
    //接收字节的缓冲数组
    private byte[] receivBuf = new byte[32];

    public ServerThread(Socket client, SocketAddress address){
        this.socket = client;
        this.address = address;
    }

    @Override
    public void run() {
        try {
            //接收数据
            InputStream in = socket.getInputStream();
            System.out.println("ip为"+address+"的用户开始发言：");
            while((recvMsgSize = in.read(receivBuf))!=-1){
                String receivedData = new String(receivBuf);
//                receivedData=String.copyValueOf(receivedData.toCharArray(), 0, receivBuf.length);
                System.out.println(receivedData);
            }
//            in.close();
            socket.shutdownInput();
            //发送数据
            System.out.println("服务端回复客户端：");
            OutputStream out = socket.getOutputStream();
            String outContext =  new BufferedReader(new InputStreamReader(System.in)).readLine();
            byte[] outArray = outContext.getBytes();
            out.write(outArray);
//            out.close();
            socket.shutdownOutput();
            System.out.println();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("服务器 run 异常: " + e.getMessage());
        }finally {
            //释放Socket资源
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    socket = null;
                    System.out.println("服务端 finally 异常:" + e.getMessage());
                }
            }
        }
    }
}

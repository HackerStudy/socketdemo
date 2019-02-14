package com.hackertstudy.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @class: com.hackertstudy.socket.Server
 * @description: socket的服务端
 * @author: yangpeng03614
 * @date: 2019-02-14 09:52
 */
public class Server {
    private static final int port = 8800; //服务端的端口号
    private static int connetNum = 0; //与服务器建立连接的连接数
    private static Socket clientSocket;
    private static ServerSocket serverSocket;

    /**
     * 服务端的main函数
     * @param args
     */
    public static void main(String[] args) {
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("服务端已启动，等待客户端连接");
            while(true){
                // 一旦有堵塞, 则表示服务器与客户端获得了连接
                clientSocket = serverSocket.accept();//监听客户端的请求，并获取客户端的socket
                SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
                System.out.println("这是第"+(++connetNum)+"位用户");
                System.out.println("客户端的ip为："+clientAddress);
                //开启线程，处理连接
                ServerThread serverThread = new ServerThread(clientSocket,clientAddress);
                Thread thread = new Thread(serverThread);
                thread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("服务端异常: " + e.getMessage());
        }
    }


}

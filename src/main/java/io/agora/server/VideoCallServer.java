package io.agora.server;

import io.agora.media.RtcTokenBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class VideoCallServer {
    static String appId = "23d4cf4473a24ee78a3810a8c61067b7";
    static String appCertificate = "f494c5663dec4e9b9da369a7fc867135";
    static String channelName = "first";
    static String userAccount = "1";
    static int uid = 220411;
    static int expirationTimeInSeconds = 3600;
    static String userToken;
    static ServerSocket serverSocket;
    private Socket clientSocket;

    public VideoCallServer(){
        try{
            serverSocket = new ServerSocket(8922);
            clientSocket = null;
        }catch (Exception e){
            System.out.println("ServerSocket initialize wrong!!!");
        }
    }

    public static void main(String args[]) throws IOException {
        //VideoCallServer videoCallServer = new VideoCallServer();
        ServerSocket serverSocket;
        Socket clientSocket;
        try{
            serverSocket = new ServerSocket(8922);
            clientSocket = null;
            int clientsCount = 0;
            while(true){
                clientSocket = serverSocket.accept();
                clientsCount++;
                System.out.println("Client count: " + clientsCount);
                InetAddress clientAddress = clientSocket.getInetAddress();
                System.out.println("Client's IP: " + clientAddress.getHostAddress());
                ServerThread serverThread = new ServerThread(clientSocket);
                serverThread.start();
            }
        }catch (Exception e){
            System.out.println("ServerSocket initialize wrong!!!");
        }

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    videoCallServer.tcpConnection();
                }catch (Exception e){
                    System.out.println("connection Wrong!");
                }
            }
        }).start();*/


    }

    /*String buildToken(int userID){
        RtcTokenBuilder token = new RtcTokenBuilder();
        int timestamp = (int)(System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
        //String result = token.buildTokenWithUserAccount(appId, appCertificate,
                //channelName, userAccount, RtcTokenBuilder.Role.Role_Publisher, timestamp);
        //System.out.println(result);

        userToken = token.buildTokenWithUid(appId, appCertificate,
                channelName, userID, RtcTokenBuilder.Role.Role_Publisher, timestamp);
        System.out.println(userToken);
        return userToken;
    }*/

    private void tcpConnection() throws Exception{
        //使用Socket对象中的方法accept，获取到客户端对象Socket
        clientSocket = serverSocket.accept();
        System.out.println("客户端已连接！");
        //获取网络字节输入流getInputStream对象中的方法read,读取客户端发送的数据
        InputStream clientInputStream = clientSocket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = clientInputStream.read(bytes);
        String userID = new String(bytes,0,len);
        System.out.println("读取客户端发送的数据成功！userID: "+userID);

        //读取资源
        /*byte[] bytes = new byte[1024];
        int len = clientInputStream.read(bytes);
        System.out.println(new String(bytes,0,len));//输出客户端的信息*/

        //String clientToekn = buildToken(Integer.parseInt(userID));

        //System.out.println("token is: "+clientToekn);
        //5 使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
        //OutputStream outputStream = clientSocket.getOutputStream();
        //outputStream.write(clientToekn.getBytes());
        //outputStream.write("客户端：我看到你的消息了".getBytes());
        //6 释放资源
        //clientSocket.close();
        //serverSocket.close();
    }
}

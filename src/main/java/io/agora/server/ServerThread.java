package io.agora.server;

import io.agora.media.RtcTokenBuilder;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
    static String appId = "23d4cf4473a24ee78a3810a8c61067b7";
    static String appCertificate = "f494c5663dec4e9b9da369a7fc867135";
    static String channelName = "first";
    static String userAccount = "1";
    static int uid = 220411;
    static int expirationTimeInSeconds = 3600;
    static String userToken;
    Socket clientSocket = null;


    public ServerThread(Socket socket){
        this.clientSocket = socket;
    }

    public void run(){
        InputStream clientInputStream = null;
        OutputStream clientOutputStream = null;
        try{
            clientInputStream = clientSocket.getInputStream();
            byte[] bytes = new byte[1024];
            int len = clientInputStream.read(bytes);
            String userID = new String(bytes,0,len);
            System.out.println("读取客户端发送的数据成功！userID: "+userID);
            String clientToekn = buildToken(Integer.parseInt(userID));
            System.out.println("token is: "+clientToekn);
            //5 使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
            clientOutputStream = clientSocket.getOutputStream();
            clientOutputStream.write(clientToekn.getBytes());
            //outputStream.write("客户端：我看到你的消息了".getBytes());
            //6 释放资源
            if(clientInputStream != null)
                clientInputStream.close();
            if(clientOutputStream != null)
                clientOutputStream.close();
            if(clientSocket != null)
                clientSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    String buildToken(int userID){
        RtcTokenBuilder token = new RtcTokenBuilder();
        int timestamp = (int)(System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
        /*String result = token.buildTokenWithUserAccount(appId, appCertificate,
                channelName, userAccount, RtcTokenBuilder.Role.Role_Publisher, timestamp);
        System.out.println(result);*/

        userToken = token.buildTokenWithUid(appId, appCertificate,
                channelName, userID, RtcTokenBuilder.Role.Role_Publisher, timestamp);
        //System.out.println(userToken);
        return userToken;
    }
}


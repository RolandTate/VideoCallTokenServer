package io.agora.server;

import io.agora.media.RtcTokenBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    static Lock TokenLock = new ReentrantLock();

    public static void main(String args[]) {
        try{
            ServerSocket serverSocket = new ServerSocket(8922);
            //ExecutorService executorService = Executors.newCachedThreadPool();
            int clientsCount = 0;
            while(true){
                Socket clientSocket = serverSocket.accept();
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

    }

}

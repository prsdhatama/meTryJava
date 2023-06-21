package com.prsdhatama.Port.openPort;

//////////////////////////////////

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class MyServer {
    public static void main(String[] args) throws IOException {
        // setting the inetAddress
        InetAddress inetAddress = InetAddress.getByName("0.0.0.0");
        //setting the port
        int port = 8080;
        try {
            //calling the constructor of the ServerSocket class, if this parameter on serverSocket filled by port number. the address will try to bind 0.0.0.0
            ServerSocket serverSocket = new ServerSocket();
            //Binding the SocketAddress with inetAddress and port
            SocketAddress endPoint = new InetSocketAddress(inetAddress, 8080);
            //bind() method  the ServerSocket to the specified socket address
            serverSocket.bind(endPoint);
            //getLocalSocketAddress() returns the local socket address
            System.out.println("Local Socket Address: " + serverSocket.getLocalSocketAddress());
            //establishes connection
            Socket socket = serverSocket.accept();
            //Creating object for listening the input data
            DataInputStream diStream = new DataInputStream(socket.getInputStream());
            //Define the incoming data type, java receive the data in bytes
            String streamedData = diStream.readUTF();
            //Display the data in terminal
            System.out.println("message= " + streamedData);
            //close socket
            serverSocket.close();
//
//            /////////////////////////
//            ServerSocket serverSocket = new ServerSocket(9092);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


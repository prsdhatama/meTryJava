package com.prsdhatama.Port.openPort;
////
/////////////////////////////////

import java.io.DataOutputStream;
import java.net.Socket;

public class MyClient {
    public static void main(String[] args) {
        try {
            //Creating an instance of socket at given address
            Socket socket = new Socket("localhost", 8080);
            //create an instance to write an object at given parameter
            DataOutputStream doutStream = new DataOutputStream(socket.getOutputStream());
            //Writing doutStream data
            doutStream.writeUTF("Hello Servers");
            //Flush
            doutStream.flush();
            //Clouse doutStream
            doutStream.close();
            //Close socket
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
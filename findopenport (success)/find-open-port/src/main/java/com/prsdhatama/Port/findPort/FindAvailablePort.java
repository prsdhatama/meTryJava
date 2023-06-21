package com.prsdhatama.Port.findPort;

import java.io.IOException;
import java.net.ServerSocket;

public class FindAvailablePort {
    public static void main(String[] args) {
        //Starting port number
        int startPort = 8080;
        //Ending port number (can be adjusted as needed)
        int endPort = 8085;
        //creating an instance of findAvailablePort as (port number)
        int availablePort = findAvailablePort(startPort, endPort);
        //checking the port number created from findAvailablePort instance based on condition below
        if (availablePort != -1) {
            System.out.println("Available port found: " + availablePort);
            // Proceed with opening the socket on the availablePort
        } else {
            System.out.println("No available port found within the range.");
            // Handle the situation where no available port is found
        }
    }

    private static int findAvailablePort(int startPort, int endPort) {
        for (int port = startPort;
             port <= endPort;
             port++) {
            try (ServerSocket ignorePort = new ServerSocket(port)) {
                return port; // Port is available
            }
            catch (IOException ignorePort) {
                // Port is already in use, continue to the next port
                System.out.println("No port available in the range given");
            }
        }
        return -1; // No available port found within the range
    }
}

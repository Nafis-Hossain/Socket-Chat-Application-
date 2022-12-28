package com.example.socketchat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class MyServer {
    public static void main(String[] args) throws IOException {

        try {
            System.out.println("Server is waiting for the client");
            ServerSocket serverSocket = new ServerSocket(6600);

            while (true) {
                Socket ssc = serverSocket.accept();
                Client client = new Client(ssc);
                Thread t = new Thread(client);
                t.start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



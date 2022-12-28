package com.example.socketchat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Client implements Runnable {
    String CLientName;

    BufferedWriter writer;
    BufferedReader reader;
    final static ArrayList<Client> clients = new ArrayList<Client>();

    Client(Socket sc) {

        try {
//            this.CLientName = clientName;

            OutputStreamWriter osw = new OutputStreamWriter(sc.getOutputStream());
            writer = new BufferedWriter(osw);

            InputStreamReader isr = new InputStreamReader(sc.getInputStream());
            reader = new BufferedReader(isr);

            CLientName = reader.readLine();
            clients.add(this);

            System.out.println("Client "+CLientName+" is Conneceted");

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                String data = reader.readLine() + "\n";
                data = CLientName +" :"+data +"";

                synchronized (clients) {
                    for (Client client : clients) {
                        client.writer.write(data);
                        client.writer.flush();
                    }
                }
            }catch (SocketException e){
                break;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

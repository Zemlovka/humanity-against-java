package com.zemlovka.haj.client.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private static final Logger log = LoggerFactory.getLogger(Client.class);
    private String message;

    public Client() {
    }

    @Override
    public void run() {
        log.info("Client started.");
        try (
            Socket clientSocket = new Socket("127.0.0.1", 8082);
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true))
        {
            Thread listenerThread = new Thread(new Listener(clientSocket.getInputStream()));
            listenerThread.start();

            boolean keepAlive = true;
            while (keepAlive) {
                if (message != null) {
                    pw.println(message);
                    message = null;
                }
                Thread.sleep(50);
            }
        } catch (IOException e) {
            log.error("Doslo k vyjimce behem komunikace se serverem.", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    class ClientServerOutputReader extends Thread {
        Socket serverSocket;
        public ClientServerOutputReader(Socket serverSocket){
            this.serverSocket = serverSocket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(serverSocket.getInputStream()));

                String outputFromServer="";
                while((outputFromServer=in.readLine())!= null){
                    //This part is printing the output to console
                    //Instead it should be appending the output to some file
                    //or some swing element. Because this output may overlap
                    //the user input from console
                    System.out.println(outputFromServer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    class ClientUserInputReader extends Thread {
        Socket serverSocket;
        public ClientUserInputReader(Socket serverSocket){
            this.serverSocket = serverSocket;
        }
        public void run(){
            BufferedReader stdIn = new BufferedReader(
                    new InputStreamReader(System.in));
            PrintWriter out;
            try {
                out = new PrintWriter(serverSocket.getOutputStream(), true);
                String userInput;

                while ((userInput = stdIn.readLine()) != null) {
                    out.println(userInput);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
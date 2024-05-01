package com.zemlovka.haj.client.ws;

import com.zemlovka.haj.client.ws.commands.CommandCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class LobbyClient extends Thread {
    private static final Logger log = LoggerFactory.getLogger(LobbyClient.class);
    private Socket clientSocket;
    private PrintWriter pw;
    private HashMap<UUID, CommandCallback<?>> commandCallbackMap;

    public LobbyClient() {
//        commandCallbackList = new ArrayList<>();
    }

    @Override
    public void run() {
        log.info("Client started.");
        try (
            Socket clientSocket = new Socket("127.0.0.1", 8082);
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true))
        {
            this.clientSocket = clientSocket;
            this.pw = pw;
            Thread listenerThread = new ClientServerOutputReader(clientSocket);
            listenerThread.start();
        } catch (IOException e) {
            log.error("Communication error.", e);
        }
    }

    public void sendRequest(String request) {
        //todo keepalive?
        pw.println(request);
    }

    class ClientServerOutputReader extends Thread {
        Socket clientSocket;
        public ClientServerOutputReader(Socket clientSocket){
            this.clientSocket = clientSocket;
        }

        public void run() {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()))) {

                while (true) {
//                    responseQueue.add(br.lines().collect(Collectors.joining()));
                }
            } catch (IOException e) {
                log.error("Exception occurred while listening for incoming communication.", e);
            }
        }
    }
}
package com.zemlovka.haj.client.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemlovka.haj.utils.CommunicationObject;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.ResourceObjectMapperFactory;
import com.zemlovka.haj.utils.dto.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;


public class LobbyClient extends Thread {
    private static final Logger log = LoggerFactory.getLogger(LobbyClient.class);
    private final ObjectMapper objectMapper;
    private Socket clientSocket;
    private ConcurrentHashMap<UUID, JavaFxAsyncFutureWrapper<Resource>> futureConcurrentHashMap;
    private UUID clientId;
    private PrintWriter pw;
    public LobbyClient() {
        this.objectMapper = ResourceObjectMapperFactory.getObjectMapper();
        futureConcurrentHashMap = new ConcurrentHashMap<>();
        clientId = UUID.randomUUID();
    }

    @Override
    public void run() {
        log.info("Client started.");
        try
        {
            this.clientSocket = new Socket("127.0.0.1", 8082);
            this.pw = new PrintWriter(clientSocket.getOutputStream(), true);
            Thread listenerThread = new ClientServerOutputReader(clientSocket);
            listenerThread.start();
        } catch (IOException e) {
            log.error("Communication error.", e);
        }
    }

    public JavaFxAsyncFutureWrapper<Resource> sendRequest(Resource request, String commandName) {
        final UUID uuid = UUID.randomUUID();
        final ConnectionHeader header = new ConnectionHeader(clientId, uuid, request.getClass().getSimpleName(), commandName);
        final CommunicationObject communicationObject = new CommunicationObject(header, request);
        JavaFxAsyncFutureWrapper<Resource> completableFuture = new JavaFxAsyncFutureWrapper<>();
        futureConcurrentHashMap.put(uuid, completableFuture);
        CompletableFuture.runAsync(() -> {
            try {
                pw.println(objectMapper.writeValueAsString(communicationObject));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return completableFuture;
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
                    String content = br.readLine();
                    CommunicationObject communicationObject = objectMapper.readValue(content, CommunicationObject.class);
                    futureConcurrentHashMap.forEach((uuid, commandCallback) -> {
                                if (uuid.equals(communicationObject.header().communicationUuid())) {
                                    commandCallback.complete(communicationObject.body());
                                    futureConcurrentHashMap.remove(communicationObject.header().communicationUuid());
                                }
                            });
                }
            } catch (IOException e) {
                log.error("Exception occurred while listening for incoming communication.", e);
            }
        }
    }

    public UUID getClientId() {
        return clientId;
    }
}
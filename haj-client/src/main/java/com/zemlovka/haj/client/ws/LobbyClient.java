package com.zemlovka.haj.client.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemlovka.haj.utils.CommunicationObject;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.Resource;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


public class LobbyClient extends Thread {
    private static final Logger log = LoggerFactory.getLogger(LobbyClient.class);
    private final ObjectMapper objectMapper;
    private Socket clientSocket;
    private PrintWriter pw;
    private ConcurrentHashMap<UUID, CompletableFuture<Resource>> futureConcurrentHashMap;

    public LobbyClient() {

        this.objectMapper = new ObjectMapper();
        Set<Class<?>> subtypes = new HashSet<>(new Reflections().getSubTypesOf(Resource.class));
        objectMapper.registerSubtypes(subtypes);
        futureConcurrentHashMap = new ConcurrentHashMap<>();
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

public <T> Future<Resource> sendRequest(Resource request, Class<T> returnObjectType) {
        final UUID uuid = UUID.randomUUID();
        final ConnectionHeader header = new ConnectionHeader(uuid, returnObjectType.getSimpleName());
        final CommunicationObject communicationObject = new CommunicationObject(header, request);
        CompletableFuture<Resource> completableFuture = new CompletableFuture<>();
        futureConcurrentHashMap.put(uuid, completableFuture);
        try {
            pw.println(objectMapper.writeValueAsString(communicationObject));
            return completableFuture;
        } catch (JsonProcessingException e) {
            //todo
            throw new RuntimeException(e);
        }
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
                    String content = br.lines().collect(Collectors.joining());
                    CommunicationObject communicationObject = objectMapper.readValue(content, CommunicationObject.class);
                    futureConcurrentHashMap.forEach((uuid, commandCallback) -> {
                                if (uuid.equals(communicationObject.header().uuid())) {
                                    commandCallback.complete(communicationObject.body());
                                }
                            });
                }
            } catch (IOException e) {
                log.error("Exception occurred while listening for incoming communication.", e);
            }
        }
    }

}
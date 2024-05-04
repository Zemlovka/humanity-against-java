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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


public class LobbyClient extends Thread {
    private static final Logger log = LoggerFactory.getLogger(LobbyClient.class);
    private final ObjectMapper objectMapper;
    private Socket clientSocket;
    private ConcurrentHashMap<UUID, CompletableFuture<Resource>> futureConcurrentHashMap;
    private UUID clientId;
    private PrintWriter pw;
    private CompletableFuture<Future<Resource>> senderThreadFuture;
    private Resource senderThreadResource;
    private Class<?> senderThreadReturnObjectType;
    private String senderThreadCommandName;
    private Thread senderThread;

    public LobbyClient() {

        this.objectMapper = new ObjectMapper();
        Set<Class<?>> subtypes = new HashSet<>(new Reflections().getSubTypesOf(Resource.class));
        objectMapper.registerSubtypes(subtypes);
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
//            senderThread = new ClientSocketSender();
            listenerThread.start();
//            senderThread.start();
        } catch (IOException e) {
            log.error("Communication error.", e);
        }
    }

    public <T> Future<Resource> sendRequest(Resource request, Class<T> returnObjectType , String commandName) {
        final UUID uuid = UUID.randomUUID();
        final ConnectionHeader header = new ConnectionHeader(clientId, uuid, returnObjectType.getSimpleName(), commandName);
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
//        senderThreadResource = request;
//        senderThreadReturnObjectType = returnObjectType;
//        senderThreadCommandName = commandName;
//        senderThreadFuture = new CompletableFuture<>();
//        senderThread.notify();
//        try {
//            return senderThreadFuture.get();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        }
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
                                if (uuid.equals(communicationObject.header().communicationUuid())) {
                                    commandCallback.complete(communicationObject.body());
                                }
                            });
                }
            } catch (IOException e) {
                log.error("Exception occurred while listening for incoming communication.", e);
            }
        }
    }

    class ClientSocketSender extends Thread {
        private PrintWriter pw;

        public void run() {
            try {
                PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
                while (true) {
//                    pw.println(new Scanner(System.in).nextLine());
                    synchronized(this) {
                    wait();
                    }
                    senderThreadFuture.complete(sendRequest(senderThreadResource, senderThreadReturnObjectType, senderThreadCommandName));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void interrupt() {
            super.interrupt();
        }

        public Future<Resource> sendRequest(Resource request, Class<?> returnObjectType , String commandName) {
            final UUID uuid = UUID.randomUUID();
            final ConnectionHeader header = new ConnectionHeader(clientId, uuid, returnObjectType.getSimpleName(), commandName);
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
    }

}
package com.zemlovka.haj.client.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemlovka.haj.client.fx.ToastNotification;
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

import static com.zemlovka.haj.utils.GlobalUtils.compileUUID;


public class Client extends Thread {
    private static final Logger log = LoggerFactory.getLogger(Client.class);
    private final ObjectMapper objectMapper;
    private Socket clientSocket;
    private ConcurrentHashMap<UUID, CommandCallback<Resource>> futureConcurrentHashMap;
    private UUID clientId;
    private PrintWriter pw;
    private static final int RECONNECT_INTERVAL_MS = 5000;
    public Client() {
        this.objectMapper = ResourceObjectMapperFactory.getObjectMapper();
        futureConcurrentHashMap = new ConcurrentHashMap<>();
        clientId = UUID.randomUUID();
    }

    @Override
    public void run() {
        log.info("Client started.");
        while (true) {
            try {
                this.clientSocket = new Socket("127.0.0.1", 8082);
                this.pw = new PrintWriter(clientSocket.getOutputStream(), true);
                Thread listenerThread = new ClientServerOutputReader(clientSocket);
                listenerThread.start();
                log.info("Connection with the server is established.");
                break;
            } catch (IOException e) {
                log.error("Communication error.", e);
                try {
                    Thread.sleep(RECONNECT_INTERVAL_MS);
                } catch (InterruptedException ex) {
                    log.error("Reconnection thread interrupted.", ex);
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public CommandCallback<Resource> sendRequest(Resource request, String commandName) {
        final UUID uuid = UUID.randomUUID();
        final ConnectionHeader header = new ConnectionHeader(clientId, uuid, request.getClass().getSimpleName(), commandName);
        final CommunicationObject communicationObject = new CommunicationObject(header, request);
        CommandCallback<Resource> completableFuture = new CommandCallback<>(commandName, uuid);
        log.info("Posting a callback for command {} with uuid {}", commandName, compileUUID(uuid));
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
                                log.info("Completing a callback for command {} with uuid {}", commandCallback.getCommandName(), compileUUID(uuid));
                                commandCallback.complete(communicationObject.body(), communicationObject.body().isPolling());
                                if (!communicationObject.body().isPolling())
                                    futureConcurrentHashMap.remove(communicationObject.header().communicationUuid());
                                log.info("Current unresolved callbacks ({}): {}", futureConcurrentHashMap.size(), futureConcurrentHashMap.values());
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
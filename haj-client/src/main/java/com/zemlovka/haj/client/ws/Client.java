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
import java.util.concurrent.*;
import java.util.function.Consumer;

import static com.zemlovka.haj.utils.GlobalUtils.compileUUID;

/**
 * Client class that handles communication with the server.
 * <p>
 * @version 1.0
 * @author Nikita Korotov, Mykhailo Bubnov
 */
public class Client extends Thread {
    private static final Logger log = LoggerFactory.getLogger(Client.class);
    private final ObjectMapper objectMapper;
    private Socket clientSocket;
    private ConcurrentHashMap<UUID, CommandCallback<Resource>> futureConcurrentHashMap;
    private Queue<Consumer<PrintWriter>> commandQueue;
    private UUID clientId;
    private PrintWriter pw;
    private static final int RECONNECT_INTERVAL_MS = 5000;
    private ConnectionStatusListener connectionStatusListener;
    private volatile boolean connected = false;
    private volatile boolean running = true;
    private Thread listenerThread;
    private int port;
    private String host;

    public Client(String host, int port) {
        this.objectMapper = ResourceObjectMapperFactory.getObjectMapper();
        futureConcurrentHashMap = new ConcurrentHashMap<>();
        commandQueue = new ConcurrentLinkedQueue<>();
        clientId = UUID.randomUUID();
        this.host = host;
        this.port = port;
    }

    public void setConnectionStatusListener(ConnectionStatusListener listener) {
        this.connectionStatusListener = listener;
    }

    /**
     * Main method of the client. Connects to the server and listens for incoming communication.
     */
    @Override
    public void run() {
        log.info("Client started.");
        while (running) {
            try {
                this.clientSocket = new Socket(host, port);
                this.pw = new PrintWriter(clientSocket.getOutputStream(), true);
                connected = true;
                if (connectionStatusListener != null) {
                    connectionStatusListener.onConnectionEstablished();
                }
                log.info("Connection with the server is established.");

                listenerThread = new ClientServerOutputReader(clientSocket);
                listenerThread.start();
                processCommandQueue();

                // Wait for the listener thread to finish
                listenerThread.join();

            } catch (IOException | InterruptedException e) {
                log.error("Communication error.", e);
                connected = false;
                if (connectionStatusListener != null) {
                    connectionStatusListener.onConnectionLost();
                }
                try {
                    Thread.sleep(RECONNECT_INTERVAL_MS);
                } catch (InterruptedException ex) {
                    log.error("Reconnection thread interrupted.", ex);
                    Thread.currentThread().interrupt();
                }
            } finally {
                if (listenerThread != null && listenerThread.isAlive()) {
                    listenerThread.interrupt();
                }
            }
        }
    }

    public void stopClient() {
        running = false;
        if (listenerThread != null) {
            listenerThread.interrupt();
        }
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            log.error("Error closing client socket", e);
        }
    }

    /**
     * Sends a request to the server and returns a CompletableFuture that will be completed when the response is received.
     * @param request the request to send
     * @param commandName the name of the command
     * @return {@link CompletableFuture} that will be completed when the response is received
     */
    public CommandCallback<Resource> sendRequest(Resource request, String commandName) {
        final UUID uuid = UUID.randomUUID();
        final ConnectionHeader header = new ConnectionHeader(clientId, uuid, request.getClass().getSimpleName(), commandName);
        final CommunicationObject<?> communicationObject = new CommunicationObject<>(header, request);
        CommandCallback<Resource> completableFuture = new CommandCallback<>(commandName, uuid, futureConcurrentHashMap);
        log.info("Posting a callback for command {} with uuid {}", commandName, compileUUID(uuid));
        futureConcurrentHashMap.put(uuid, completableFuture);

        Runnable command = () -> {
            try {
                pw.println(objectMapper.writeValueAsString(communicationObject));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };

        if (connected) {
            CompletableFuture.runAsync(command);
        } else {
            commandQueue.add(pw -> CompletableFuture.runAsync(command));
        }

        return completableFuture;
    }

    /**
     * Processes the command queue. This method is called when the client is connected to the server.
     */
    private void processCommandQueue() {
        Consumer<PrintWriter> command;
        while ((command = commandQueue.poll()) != null) {
            command.accept(pw);
        }
    }

    class ClientServerOutputReader extends Thread {
        Socket clientSocket;

        public ClientServerOutputReader(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()))) {

                while (!interrupted()) {
                    String content = br.readLine();
                    if (content == null) {
                        throw new IOException("Disconnected");
                    }
                    CommunicationObject<Resource> communicationObject = objectMapper.readValue(content, CommunicationObject.class);
                    futureConcurrentHashMap.forEach((uuid, commandCallback) -> {
                        if (uuid.equals(communicationObject.header().communicationUuid())) {
                            log.info("Completing a callback for command {} with uuid {}", commandCallback.getCommandName(), compileUUID(uuid));
                            commandCallback.complete(communicationObject);
                            log.info("Current unresolved callbacks ({}): {}", futureConcurrentHashMap.size(), futureConcurrentHashMap.values());
                        }
                    });
                }
            } catch (IOException e) {
                log.error("Exception occurred while listening for incoming communication.", e);
                connected = false;
                if (connectionStatusListener != null) {
                    connectionStatusListener.onConnectionLost();
                }
            }
        }
    }

    public UUID getClientId() {
        return clientId;
    }
}

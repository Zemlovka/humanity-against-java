package com.zemlovka.haj.server.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.utils.CommunicationObject;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;


public abstract class AbstractServerCommand<V extends Resource, R extends Resource> implements ServerCommand<V> {
    private static final Logger log = LoggerFactory.getLogger(AbstractServerCommand.class);
    protected final ServerWsActions wsActions;
    
    public AbstractServerCommand(ServerWsActions wsActions) {
        this.wsActions = wsActions;
    }

    @Override
    public boolean resolve(CommunicationObject resource) {
        return resource.header().commandName().equals(getName());
    }

    void send(R body, ConnectionHeader clientHeader) {
        log.info("Sending response of type {} to command {}, to client with UUID{}", body.getClass().getSimpleName(), getName(), clientHeader.clientID());
        wsActions.sendMessage(body, clientHeader);
    }

}

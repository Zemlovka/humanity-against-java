package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.utils.CommunicationObject;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Abstract server command for implementing server commands.
 *
 * @param <V> - request resource
 * @param <R> - return resource
 */
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

    /**
     * Used inside the command to send the response
     * @param body - resource to send
     * @param clientHeader - client headers from the request
     */
    void send(R body, ConnectionHeader clientHeader) {
        wsActions.sendMessage(body, clientHeader);
    }

}

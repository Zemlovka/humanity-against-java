package com.zemlovka.haj.server.command;

import com.zemlovka.haj.utils.Command;
import com.zemlovka.haj.utils.CommunicationObject;


public interface ResolvableCommand<V, R> extends Command<V, R> {

    /**
     * Resolves if this resource can be accepted by that command
     */
    default boolean resolve(CommunicationObject resource) {
        return resource.header().commandName().equals(getName());
    }
}

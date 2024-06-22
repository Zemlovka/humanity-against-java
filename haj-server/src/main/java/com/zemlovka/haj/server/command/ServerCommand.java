package com.zemlovka.haj.server.command;

import com.zemlovka.haj.utils.CommunicationObject;
import com.zemlovka.haj.utils.ConnectionHeader;


public interface ServerCommand<V> {

    /**
     * Resolves if this resource can be accepted by that command
     */
    boolean resolve(CommunicationObject resource);

    /**
     * Executes command with these params
     */
    void execute(V argument, ConnectionHeader connectionHeader);

    /**
     * Must return the name of this command
     */
    String getName();
}

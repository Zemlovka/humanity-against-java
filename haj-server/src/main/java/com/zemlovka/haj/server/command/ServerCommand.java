package com.zemlovka.haj.server.command;

import com.zemlovka.haj.utils.CommunicationObject;
import com.zemlovka.haj.utils.ConnectionHeader;


public interface ServerCommand<V> {

    /**
     * Resolves if this resource can be accepted by that command
     */
    boolean resolve(CommunicationObject resource);
    void execute(V argument, ConnectionHeader connectionHeader);

    String getName();
}

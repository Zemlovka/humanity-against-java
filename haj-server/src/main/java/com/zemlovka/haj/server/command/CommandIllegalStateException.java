package com.zemlovka.haj.server.command;



public class CommandIllegalStateException extends RuntimeException {
    public CommandIllegalStateException(String message) {
        super(message);
    }
}

package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.user.User;
import com.zemlovka.haj.utils.dto.client.InitDTO;
import com.zemlovka.haj.utils.dto.server.UserConnectionResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InitCommand implements ResolvableCommand<InitDTO, UserConnectionResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(InitCommand.class);
    private static final String NAME = "Init";
    private final User userData;

    public InitCommand(User userData) {
        this.userData = userData;
    }

    @Override
    public UserConnectionResponseDTO run(InitDTO argument) {
        if (userData.getUsername() != null) {
            throw new CommandIllegalStateException("User " + argument.username() + " is already initiated");
        }
        userData.setUsername(argument.username());
        userData.setUuid(argument.clientUuid());
        logger.info("User initiated with username " + userData.getUsername());
        return new UserConnectionResponseDTO(true, "User with username " + userData.getUsername() + "initiated succesfully");
    }

    @Override
    public String getName() {
        return NAME;
    }
}

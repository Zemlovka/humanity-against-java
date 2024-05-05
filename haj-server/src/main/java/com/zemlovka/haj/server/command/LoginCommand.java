package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.user.User;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.LoginDTO;
import com.zemlovka.haj.utils.dto.server.LoginResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginCommand implements ResolvableCommand<LoginDTO, LoginResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(LoginCommand.class);
    private static final String NAME = CommandNameEnum.LOGIN.name();
    private final User userData;

    public LoginCommand(User userData) {
        this.userData = userData;
    }

    @Override
    public LoginResponseDTO run(LoginDTO argument) {
        if (userData.getUsername() != null) {
            throw new CommandIllegalStateException("User " + argument.username() + " is already initiated");
        }
        userData.setUsername(argument.username());
        userData.setUuid(argument.clientUuid());
        logger.info("User initiated with username {}", userData.getUsername());
        return new LoginResponseDTO(true, "User with username " + userData.getUsername() + "initiated succesfully");
    }

    @Override
    public String getName() {
        return NAME;
    }
}

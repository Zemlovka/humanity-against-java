package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.LogoutDTO;
import com.zemlovka.haj.utils.dto.server.LogoutResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogoutCommand implements ResolvableCommand<LogoutDTO, LogoutResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(LoginCommand.class);
    private static final String NAME = CommandNameEnum.LOGOUT.name();
    private final User userData;

    public LogoutCommand(User userData) {
        this.userData = userData;
    }
    @Override
    public LogoutResponseDTO run(LogoutDTO argument) {
        logger.info("User with username {} has logged out", userData.getUsername());
        userData.setUsername(null);
        return new LogoutResponseDTO(true);
    }

    @Override
    public String getName() {
        return null;
    }
}

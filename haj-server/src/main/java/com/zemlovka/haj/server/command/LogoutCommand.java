package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.LogoutDTO;
import com.zemlovka.haj.utils.dto.server.LogoutResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A login command to uninitialize the user
 */
public class LogoutCommand extends AbstractServerCommand<LogoutDTO, LogoutResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(LogoutCommand.class);
    private static final String NAME = CommandNameEnum.LOGOUT.name();
    private final User userData;

    public LogoutCommand(ServerWsActions wsActions, User userData) {
        super(wsActions);
        this.userData = userData;
    }
    @Override
    public void execute(LogoutDTO argument, ConnectionHeader clientHeader) {
        logger.info("User with username {} has logged out", userData.getUsername());
        userData.setUsername(null);
        send(new LogoutResponseDTO(true), clientHeader) ;
    }

    @Override
    public String getName() {
        return null;
    }
}

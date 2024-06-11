package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.LoginDTO;
import com.zemlovka.haj.utils.dto.server.LoginResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class LoginCommand extends AbstractServerCommand<LoginDTO, LoginResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(LoginCommand.class);
    private static final String NAME = CommandNameEnum.LOGIN.name();
    private final User userData;
    private final Map<String, User> userMap;

    public LoginCommand(ServerWsActions wsActions, User userData, Map<String, User> userMap) {
        super(wsActions);
        this.userData = userData;
        this.userMap = userMap;
    }

    @Override
    public void execute(LoginDTO argument, ConnectionHeader clientHeader) {
        final LoginResponseDTO loginResponseDTO;
        if (userMap.get(argument.username()) != null) {
            loginResponseDTO = new LoginResponseDTO(false, "User " + argument.username() + " is already initiated");
        } else {
            userData.setUsername(argument.username());
            userData.setUuid(argument.clientUuid());
            userMap.put(argument.username(), userData);
            logger.info("User initiated with username {}", userData.getUsername());
            loginResponseDTO = new LoginResponseDTO(true, "User with username " + userData.getUsername() + "initiated succesfully");
        }
        send(loginResponseDTO,clientHeader);
    }

    @Override
    public String getName() {
        return NAME;
    }
}

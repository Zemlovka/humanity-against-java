package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.DtoMapper;
import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.FetchPlayersDTO;
import com.zemlovka.haj.utils.dto.server.FetchPlayersResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FetchPlayersCommand extends AbstractServerCommand<FetchPlayersDTO, FetchPlayersResponseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(FetchPlayersCommand.class);
    private static final String NAME = CommandNameEnum.JOIN_LOBBY.name();
    private final Lobby lobby;

    public FetchPlayersCommand(ServerWsActions wsActions, User userData) {
        super(wsActions);
        this.lobby = userData.getCurrentLobby();
    }
    @Override
    public void execute(FetchPlayersDTO argument, ConnectionHeader clientHeader) {
        lobby.getFlags().getNewPlayerFlag().thenApply(f -> {
            final boolean awaitNewPlayers = lobby.getUsers().size() < lobby.getCapacity();
            send(new FetchPlayersResponseDTO(DtoMapper.mapPlayers(lobby.getUsers().values()), awaitNewPlayers), clientHeader);
            lobby.getFlags().clearNewPlayerFlag();
            return null;
        });
    }

    @Override
    public String getName() {
        return NAME;
    }
}

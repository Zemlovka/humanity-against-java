package com.zemlovka.haj.server.command;

import com.zemlovka.haj.server.DtoMapper;
import com.zemlovka.haj.server.ServerWsActions;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.dto.CommandNameEnum;
import com.zemlovka.haj.utils.dto.client.FetchLobbyListDTO;
import com.zemlovka.haj.utils.dto.server.LobbyListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class FetchLobbysCommand extends AbstractServerCommand<FetchLobbyListDTO, LobbyListDTO> {
    private static final Logger logger = LoggerFactory.getLogger(FetchLobbysCommand.class);
    private static final String NAME = CommandNameEnum.FETCH_LOBBY_LIST.name();
    private final ConcurrentHashMap<String, Lobby> lobbies;
    private final User userData;

    public FetchLobbysCommand(ServerWsActions wsActions, ConcurrentHashMap<String, Lobby> lobbies, User userData) {
        super(wsActions);
        this.lobbies = lobbies;
        this.userData = userData;
    }

    @Override
    public void execute(FetchLobbyListDTO argument, ConnectionHeader clientHeader) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        userData.setLobbyPollingExecutor(executor);
// then, when you want to schedule a task
        Runnable task = () ->{
            LobbyListDTO response = new LobbyListDTO(
                    lobbies.values().stream().map(DtoMapper::mapLobby).collect(Collectors.toSet())
            );
            send(response, clientHeader);
        };
        executor.scheduleWithFixedDelay(task, 0,5, TimeUnit.SECONDS);
    }


    @Override
    public String getName() {
        return NAME;
    }
}
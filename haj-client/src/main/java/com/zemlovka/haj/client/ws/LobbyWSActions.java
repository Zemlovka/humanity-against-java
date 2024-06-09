package com.zemlovka.haj.client.ws;

import com.zemlovka.haj.client.ws.commands.*;
import com.zemlovka.haj.utils.dto.client.*;
import com.zemlovka.haj.utils.dto.server.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.zemlovka.haj.utils.dto.CommandNameEnum.*;


public class LobbyWSActions {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    private final Commands commands;
    private final LobbyClient client;

    public LobbyWSActions() {
        LobbyClient lobbyClient = new LobbyClient();
        lobbyClient.start();
        this.client = lobbyClient;
        commands = new Commands(lobbyClient);
    }


    public JavaFxAsyncFutureWrapper<LoginResponseDTO> login(String username) {
        return commands.login.run(new LoginDTO(username, client.getClientId()));
    }

    public JavaFxAsyncFutureWrapper<LogoutResponseDTO> logout() {
        return commands.logout.run(new LogoutDTO());
    }

    public JavaFxAsyncFutureWrapper<LeaveLobbyResponseDTO> leaveLobby() {
        return commands.leaveLobby.run(new LeaveLobbyDTO());
    }

    public JavaFxAsyncFutureWrapper<CreateLobbyResponseDTO> createLobby(Lobby lobby) {
        return commands.createLobby.run(
                new CreateLobbyDTO(lobby.getName(), lobby.getPassword(), lobby.getSize())
        );
    }

    public JavaFxAsyncFutureWrapper<LobbyListDTO> fetchLobbyList() {
        return commands.fetchLobbyList.run(new FetchLobbyListDTO());
    }

    public JavaFxAsyncFutureWrapper<JoinLobbyResponseDTO> joinLobby(Lobby lobby, String password) {
        return commands.joinLobby.run(new JoinLobbyDTO(lobby.getName(), password));
    }
    public JavaFxAsyncFutureWrapper<StartGameResponseDTO> startGame() {
        return commands.startGame.run(new StartGameDTO());
    }
    public JavaFxAsyncFutureWrapper<FetchPlayersResponseDTO> fetchPlayers() {
        return commands.fetchPlayers.run(new FetchPlayersDTO());
    }
    public JavaFxAsyncFutureWrapper<VoteCardResponseDTO> voteCard(AnswerCard votedCard) {
        return commands.voteCard.run(new VoteCardDTO(votedCard.getId()));
    }
    public JavaFxAsyncFutureWrapper<ChooseCardResponseDTO> chooseCard(AnswerCard answerCard) {
        return commands.chooseCard.run(new ChooseCardDTO(answerCard.getId()));
    }
    public JavaFxAsyncFutureWrapper<GetChosenCardsResponseDTO> getChosenCards() {
        return commands.getChosenCards.run(new GetChosenCardsDTO());
    }

    static class Commands {

        final ClientCommandImpl<LoginDTO, LoginResponseDTO> login;
        final ClientCommandImpl<LogoutDTO, LogoutResponseDTO> logout;
        final ClientCommandImpl<LeaveLobbyDTO, LeaveLobbyResponseDTO> leaveLobby;
        final ClientCommandImpl<CreateLobbyDTO, CreateLobbyResponseDTO> createLobby;
        final ClientCommandImpl<FetchLobbyListDTO, LobbyListDTO> fetchLobbyList;
        final ClientCommandImpl<JoinLobbyDTO, JoinLobbyResponseDTO> joinLobby;
        final ClientCommandImpl<StartGameDTO, StartGameResponseDTO> startGame;
        final ClientCommandImpl<FetchPlayersDTO, FetchPlayersResponseDTO> fetchPlayers;
        final ClientCommandImpl<VoteCardDTO, VoteCardResponseDTO> voteCard;
        final ClientCommandImpl<ChooseCardDTO, ChooseCardResponseDTO> chooseCard;
        final ClientCommandImpl<GetChosenCardsDTO, GetChosenCardsResponseDTO> getChosenCards;
        public Commands(LobbyClient client) {
            login = new ClientCommandImpl<>(client, LOGIN);
            logout = new ClientCommandImpl<>(client, LOGOUT);
            leaveLobby = new ClientCommandImpl<>(client, LEAVE_LOBBY);
            createLobby = new ClientCommandImpl<>(client, CREATE_LOBBY);
            fetchLobbyList = new ClientCommandImpl<>(client, FETCH_LOBBY_LIST);
            joinLobby = new ClientCommandImpl<>(client, JOIN_LOBBY);
            startGame = new ClientCommandImpl<>(client, START_GAME);
            fetchPlayers = new ClientCommandImpl<>(client, FETCH_PLAYERS);
            voteCard = new ClientCommandImpl<>(client, VOTE_CARD);
            chooseCard = new ClientCommandImpl<>(client, CHOOSE_CARD);
            getChosenCards = new ClientCommandImpl<>(client, GET_CHOSEN_CARDS);
        }
    }
}

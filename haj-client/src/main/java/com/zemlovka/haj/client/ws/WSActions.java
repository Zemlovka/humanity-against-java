package com.zemlovka.haj.client.ws;

import com.zemlovka.haj.client.fx.ConnectionLostNotifier;
import com.zemlovka.haj.client.ws.commands.*;
import com.zemlovka.haj.utils.dto.client.*;
import com.zemlovka.haj.utils.dto.server.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.zemlovka.haj.utils.dto.CommandNameEnum.*;


public class WSActions {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    private final Commands commands;
    private final Client client;
    private final ConnectionLostNotifier connectionStatusNotifier;

    public WSActions(ConnectionLostNotifier connectionStatusNotifier) {
        Client client = new Client();
        client.start();
        this.client = client;
        this.connectionStatusNotifier = connectionStatusNotifier;
        client.setConnectionStatusListener(connectionStatusNotifier);
        commands = new Commands(client);
    }


    public CommandCallback<LoginResponseDTO> login(String username) {
        return commands.login.run(new LoginDTO(username, client.getClientId()));
    }

    public CommandCallback<LogoutResponseDTO> logout() {
        return commands.logout.run(new LogoutDTO());
    }

    public CommandCallback<LeaveLobbyResponseDTO> leaveLobby() {
        return commands.leaveLobby.run(new LeaveLobbyDTO());
    }

    public CommandCallback<CreateLobbyResponseDTO> createLobby(Lobby lobby) {
        return commands.createLobby.run(
                new CreateLobbyDTO(lobby.getName(), lobby.getPassword(), lobby.getCapacity(), lobby.getRoundNumber())
        );
    }

    public CommandCallback<LobbyListDTO> fetchLobbyList() {
        return commands.fetchLobbyList.run(new FetchLobbyListDTO());
    }

    public CommandCallback<JoinLobbyResponseDTO> joinLobby(Lobby lobby, String password) {
        return commands.joinLobby.run(new JoinLobbyDTO(lobby.getName(), password));
    }
    public CommandCallback<StartGameResponseDTO> startGame() {
        return commands.startGame.run(new StartGameDTO());
    }
    public CommandCallback<FetchPlayersResponseDTO> fetchPlayers() {
        return commands.fetchPlayers.run(new FetchPlayersDTO());
    }
    public CommandCallback<VoteCardResponseDTO> voteCard(AnswerCard votedCard) {
        return commands.voteCard.run(new VoteCardDTO(votedCard.getId()));
    }
    public CommandCallback<GetWinnerCardResponseDTO> getWinnerCard() {
        return commands.getWinnerCard.run(new GetWinnerCardDTO());
    }
    public CommandCallback<ChooseCardResponseDTO> chooseCard(AnswerCard answerCard) {
        return commands.chooseCard.run(new ChooseCardDTO(answerCard.getId()));
    }
    public CommandCallback<GetChosenCardsResponseDTO> getChosenCards() {
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
        final ClientCommandImpl<GetWinnerCardDTO, GetWinnerCardResponseDTO> getWinnerCard;
        final ClientCommandImpl<ChooseCardDTO, ChooseCardResponseDTO> chooseCard;
        final ClientCommandImpl<GetChosenCardsDTO, GetChosenCardsResponseDTO> getChosenCards;
        public Commands(Client client) {
            login = new ClientCommandImpl<>(client, LOGIN);
            logout = new ClientCommandImpl<>(client, LOGOUT);
            leaveLobby = new ClientCommandImpl<>(client, LEAVE_LOBBY);
            createLobby = new ClientCommandImpl<>(client, CREATE_LOBBY);
            fetchLobbyList = new ClientCommandImpl<>(client, FETCH_LOBBY_LIST);
            joinLobby = new ClientCommandImpl<>(client, JOIN_LOBBY);
            startGame = new ClientCommandImpl<>(client, START_GAME);
            fetchPlayers = new ClientCommandImpl<>(client, FETCH_PLAYERS);
            voteCard = new ClientCommandImpl<>(client, VOTE_CARD);
            getWinnerCard = new ClientCommandImpl<>(client, GET_WINNER_CARD);
            chooseCard = new ClientCommandImpl<>(client, CHOOSE_CARD);
            getChosenCards = new ClientCommandImpl<>(client, GET_CHOSEN_CARDS);
        }
    }
}

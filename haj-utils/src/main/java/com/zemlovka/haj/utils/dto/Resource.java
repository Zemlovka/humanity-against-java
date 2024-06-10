package com.zemlovka.haj.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.zemlovka.haj.utils.dto.client.*;
import com.zemlovka.haj.utils.dto.server.*;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        //client
        @JsonSubTypes.Type(value = ChooseCardDTO.class),
        @JsonSubTypes.Type(value = CreateLobbyDTO.class),
        @JsonSubTypes.Type(value = FetchLobbyListDTO.class),
        @JsonSubTypes.Type(value = FetchPlayersDTO.class),
        @JsonSubTypes.Type(value = GetChosenCardsDTO.class),
        @JsonSubTypes.Type(value = GetWinnerCardDTO.class),
        @JsonSubTypes.Type(value = JoinLobbyDTO.class),
        @JsonSubTypes.Type(value = LeaveLobbyDTO.class),
        @JsonSubTypes.Type(value = LoginDTO.class),
        @JsonSubTypes.Type(value = LogoutDTO.class),
        @JsonSubTypes.Type(value = ReconnectDTO.class),
        @JsonSubTypes.Type(value = SelectAnswerDTO.class),
        @JsonSubTypes.Type(value = StartGameDTO.class),
        @JsonSubTypes.Type(value = VoteCardDTO.class),
        //server
        @JsonSubTypes.Type(value = ChooseCardResponseDTO.class),
        @JsonSubTypes.Type(value = CreateLobbyResponseDTO.class),
        @JsonSubTypes.Type(value = FetchPlayersResponseDTO.class),
        @JsonSubTypes.Type(value = GameEndDTO.class),
        @JsonSubTypes.Type(value = GameStartDTO.class),
        @JsonSubTypes.Type(value = GetChosenCardsResponseDTO.class),
        @JsonSubTypes.Type(value = GetWinnerCardResponseDTO.class),
        @JsonSubTypes.Type(value = JoinLobbyResponseDTO.class),
        @JsonSubTypes.Type(value = LobbyCreatedDTO.class),
        @JsonSubTypes.Type(value = LobbyListDTO.class),
        @JsonSubTypes.Type(value = LeaveLobbyResponseDTO.class),
        @JsonSubTypes.Type(value = LoginResponseDTO.class),
        @JsonSubTypes.Type(value = LogoutResponseDTO.class),
        @JsonSubTypes.Type(value = ReconnectAnswerDTO.class),
        @JsonSubTypes.Type(value = RoundEndDTO.class),
        @JsonSubTypes.Type(value = RoundVoteDTO.class),
        @JsonSubTypes.Type(value = StartGameResponseDTO.class),
        @JsonSubTypes.Type(value = StartRoundDTO.class),
        @JsonSubTypes.Type(value = VoteCardResponseDTO.class),
        @JsonSubTypes.Type(value = WhiteCardsDTO.class),
        //shared
        @JsonSubTypes.Type(value = AcceptDTO.class),
        @JsonSubTypes.Type(value = DenyDTO.class),
        @JsonSubTypes.Type(value = PingDTO.class),
})
public interface Resource {
    @JsonIgnore
    default boolean isPolling() {
        return false;
    }
    @JsonIgnore
    default boolean hasPollingKillConditionBeenMet() {
        return true;
    }

}

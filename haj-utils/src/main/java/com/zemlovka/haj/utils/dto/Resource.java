package com.zemlovka.haj.utils.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.zemlovka.haj.utils.dto.client.BeginDTO;
import com.zemlovka.haj.utils.dto.client.CreateLobbyDTO;
import com.zemlovka.haj.utils.dto.client.FetchLobbysDTO;
import com.zemlovka.haj.utils.dto.client.InitDTO;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BeginDTO.class),
        @JsonSubTypes.Type(value = CreateLobbyDTO.class),
        @JsonSubTypes.Type(value = FetchLobbysDTO.class),
        @JsonSubTypes.Type(value = InitDTO.class),
})
public interface Resource {

}

package com.zemlovka.haj.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.zemlovka.haj.utils.dto.Resource;
import com.zemlovka.haj.utils.dto.client.CreateLobbyDTO;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateLobbyDTO createLobbyDTO = new CreateLobbyDTO("name", "", 4);
        CommunicationObject communicationObject = new CommunicationObject(new ConnectionHeader(UUID.randomUUID(), CreateLobbyDTO.class.getSimpleName()), createLobbyDTO);
        String json = objectMapper.writeValueAsString(communicationObject);
        CommunicationObject co = objectMapper.readValue(json, CommunicationObject.class);
        Set<Class<?>> subtypes = new HashSet<>(new Reflections().getSubTypesOf(Resource.class));
        objectMapper.registerSubtypes(subtypes);
        System.out.println(objectMapper.readValue(json, CommunicationObject.class));
    }
}

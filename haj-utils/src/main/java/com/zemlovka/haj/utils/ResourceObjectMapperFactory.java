package com.zemlovka.haj.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemlovka.haj.utils.dto.Resource;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;


public class ResourceObjectMapperFactory {
    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        Set<Class<?>> subtypes = new HashSet<>(new Reflections().getSubTypesOf(Resource.class));
        objectMapper.registerSubtypes(subtypes);
        return objectMapper;
    }
}

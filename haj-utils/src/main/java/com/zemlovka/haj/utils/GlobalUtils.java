package com.zemlovka.haj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


public class GlobalUtils {
    private static final Logger logger = LoggerFactory.getLogger(GlobalUtils.class);

    /**
     * Shortens the uuid for a more comfortable preview
     */
    public static String compileUUID(UUID uuid) {
        String shortenedUUID = uuid.toString().substring(0,6).toUpperCase();
        logger.debug("Shortened uuid {} to {}", uuid, shortenedUUID);
        return shortenedUUID;
    }
}

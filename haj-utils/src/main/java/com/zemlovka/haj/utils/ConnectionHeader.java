package com.zemlovka.haj.utils;

import java.util.UUID;


public record ConnectionHeader(UUID clientID, UUID communicationUuid, String objectType, String commandName) {
}

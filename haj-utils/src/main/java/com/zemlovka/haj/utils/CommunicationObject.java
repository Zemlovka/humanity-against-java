package com.zemlovka.haj.utils;

import com.zemlovka.haj.utils.dto.Resource;


public record CommunicationObject(ConnectionHeader header, Resource body) {
}

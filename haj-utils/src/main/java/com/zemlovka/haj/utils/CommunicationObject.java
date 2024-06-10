package com.zemlovka.haj.utils;

import com.zemlovka.haj.utils.dto.Resource;


public record CommunicationObject<T extends Resource>(ConnectionHeader header, T body) {
}

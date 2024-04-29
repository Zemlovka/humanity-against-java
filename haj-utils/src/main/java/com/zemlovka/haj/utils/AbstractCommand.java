package com.zemlovka.haj.utils;

import com.zemlovka.haj.utils.dto.Resource;


public abstract class AbstractCommand<V extends Resource, R extends Resource> implements Command<V, R> {
    @Override
    public abstract R run(V argument);
}

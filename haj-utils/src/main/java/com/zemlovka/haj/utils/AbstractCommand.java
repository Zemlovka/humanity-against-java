package com.zemlovka.haj.utils;

import com.zemlovka.haj.utils.dto.Resource;


public abstract class AbstractCommand implements Command<Resource, Resource> {
    @Override
    public abstract Resource run(Resource argument);
}

package com.zemlovka.jah.utils;

import com.zemlovka.jah.utils.dto.Resource;


public abstract class AbstractCommand implements Command<Resource, Resource> {
    @Override
    public abstract Resource run(Resource argument);
}

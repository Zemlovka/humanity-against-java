package com.zemlovka.jah.utils;

public interface Command<V, R> {
    R run(V argument);
}

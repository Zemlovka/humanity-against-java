package com.zemlovka.haj.utils;

public interface Command<V, R> {
    R run(V argument);
}

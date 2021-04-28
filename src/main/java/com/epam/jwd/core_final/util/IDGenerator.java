package com.epam.jwd.core_final.util;

public enum IDGenerator {
    INSTANCE;

    long id = 1;

    public long getId() {
        return id++;
    }
}

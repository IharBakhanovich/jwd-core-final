package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.Map;

public enum SpaceShipFactory implements EntityFactory<Spaceship> {
    INSTANCE;

    @Override
    public Spaceship create(Object... args) {
        return new Spaceship((Long) args[0], (String) args[1], (Map) args[2], (Long) args[3]);
    }
}

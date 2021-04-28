package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import com.epam.jwd.core_final.factory.EntityFactory;

public enum PlanetFactory implements EntityFactory<Planet> {
    INSTANCE;

    @Override
    public Planet create(Object... args) {
        return new Planet((Long) args[0], (String) args[1], (Point) args[2]);
    }
}

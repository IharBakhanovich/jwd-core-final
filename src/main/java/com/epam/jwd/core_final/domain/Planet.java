package com.epam.jwd.core_final.domain;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;

/**
 * Expected fields:
 * <p>
 * location could be a simple class Point with 2 coordinates
 */
public class Planet extends AbstractBaseEntity {
    private final Point location;

    public Planet(long id, String name, Point location) {
        super(id, name);
        this.location = location;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String planetToString = "";
        try {
            planetToString = objectMapper.writeValueAsString(this);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return planetToString;
    }
}

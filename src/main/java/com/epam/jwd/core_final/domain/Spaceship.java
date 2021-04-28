package com.epam.jwd.core_final.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * crew {@link java.util.Map<Role, Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class Spaceship extends AbstractBaseEntity {
    private final Map<Role, Short> crew;
    private final Long flightDistance;
    private boolean isReadyForNextMission;

    public Spaceship(Long id,
                     String name,
                     Map<Role, Short> crew,
                     Long flightDistance) {
        super(id, name);
        this.crew = crew;
        this.flightDistance = flightDistance;
        this.isReadyForNextMission = true;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public boolean isReadyForNextMission() {
        return isReadyForNextMission;
    }

    public void setReadyForNextMission(boolean readyForNextMission) {
        if (isReadyForNextMission && !readyForNextMission) {
            isReadyForNextMission = false;
        }
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String spaceShipToString = "";
        try {
            spaceShipToString = objectMapper.writeValueAsString(this);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return spaceShipToString;
    }
}

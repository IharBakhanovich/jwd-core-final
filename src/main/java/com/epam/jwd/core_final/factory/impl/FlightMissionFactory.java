package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.time.LocalDate;

public enum FlightMissionFactory implements EntityFactory<FlightMission> {
    INSTANCE;

    @Override
    public FlightMission create(Object... args) {
        return new FlightMission((Long) args[0], (String) args[1], (LocalDate) args[2], (LocalDate) args[3],
                (Long) args[4], (MissionResult) args[5], (Planet) args[6], (Planet) args[7]);
    }
}

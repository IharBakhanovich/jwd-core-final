package com.epam.jwd.core_final.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 * from {@link Planet}
 * to {@link Planet}
 */
public class FlightMission extends AbstractBaseEntity {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Long distance;
    private Spaceship assignedSpaceShip;
    private List<CrewMember> assignedCrew;
    private final MissionResult missionResult;
    private final Planet from;
    private final Planet to;

    public FlightMission(Long id, String name,
                         LocalDate startDate,
                         LocalDate endDate,
                         Long distance,
                         MissionResult missionResult,
                         Planet from,
                         Planet to) {
        super(id, name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.missionResult = missionResult;
        this.from = from;
        this.to = to;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getDistance() {
        return distance;
    }

    public Spaceship getAssignedSpaceShip() {
        return assignedSpaceShip;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public Planet getFrom() {
        return from;
    }

    public Planet getTo() {
        return to;
    }

    public void setAssignedSpaceShip(Spaceship assignedSpaceShip) {
        this.assignedSpaceShip = assignedSpaceShip;
    }

    public void setAssignedCrew(List<CrewMember> assignedCrew) {
        this.assignedCrew = assignedCrew;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String flightMissionToString = "";
        try {
            flightMissionToString = objectMapper.writeValueAsString(this);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return flightMissionToString;
    }
}

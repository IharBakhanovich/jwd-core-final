package com.epam.jwd.core_final.domain;

import com.fasterxml.jackson.databind.ObjectMapper; // ObjectMapper class

import java.io.IOException;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class CrewMember extends AbstractBaseEntity {
    private final Role role;
    private final Rank rank;
    private boolean isReadyForNextMission;

    public CrewMember(Long id, String name, Role role, Rank rank) {
        super(id, name);
        this.role = role;
        this.rank = rank;
        isReadyForNextMission = true;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
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
        String crewMemberToString = "";
        try {
            crewMemberToString = objectMapper.writeValueAsString(this);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return crewMemberToString;
    }
}

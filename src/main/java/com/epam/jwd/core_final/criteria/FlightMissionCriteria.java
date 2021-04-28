package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.FlightMission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    private final Collection<Predicate<FlightMission>> predicates = new ArrayList<>();

    private void addPredicate(Predicate<FlightMission> newPredicate) {
        predicates.add(newPredicate);
    }

    public Collection<Predicate<FlightMission>> getPredicates() {
        return predicates;
    }

    public static class Builder {
        private final FlightMissionCriteria flightMissionCriteria;

        public Builder() {
            flightMissionCriteria = new FlightMissionCriteria();
        }

        public FlightMissionCriteria.Builder whereIdEquals(long id) {
            flightMissionCriteria.addPredicate(flightMission -> flightMission.getId() == id);
            return this;
        }

        public FlightMissionCriteria.Builder whereFlightDistanceLessThan(long distance) {
            flightMissionCriteria.addPredicate(flightMission -> flightMission.getDistance() < distance);
            return this;
        }

        public FlightMissionCriteria.Builder whereFlightDistanceMoreThan(Long flightDistance) {
            flightMissionCriteria.addPredicate(flightMission -> flightMission.getDistance() > flightDistance);
            return this;
        }

        public FlightMissionCriteria.Builder whereAssignedSpaceShipNameStartsWith(String beginOfSpaceShipName) {
            flightMissionCriteria.addPredicate(flightMission -> flightMission.getName().startsWith(beginOfSpaceShipName));
            return this;
        }

        public FlightMissionCriteria.Builder wherePlanetFrom(String planetName) {
            flightMissionCriteria.addPredicate(spaceship -> spaceship.getFrom().getName().equals(planetName));
            return this;
        }

        public FlightMissionCriteria.Builder wherePlanetTo(String planetName) {
            flightMissionCriteria.addPredicate(spaceship -> spaceship.getTo().getName().equals(planetName));
            return this;
        }

        public FlightMissionCriteria build() {
            return flightMissionCriteria;
        }
    }
}

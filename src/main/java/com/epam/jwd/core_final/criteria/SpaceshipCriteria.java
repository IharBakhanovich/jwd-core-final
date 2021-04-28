package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Spaceship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {
    private final Collection<Predicate<Spaceship>> predicates = new ArrayList<>();

    private void addPredicate(Predicate<Spaceship> newPredicate) {
        predicates.add(newPredicate);
    }

    public Collection<Predicate<Spaceship>> getPredicates() {
        return predicates;
    }

    public static class Builder {
        private final SpaceshipCriteria spaceshipCriteria;

        public Builder() {
            spaceshipCriteria = new SpaceshipCriteria();
        }

        public SpaceshipCriteria.Builder whereIdEquals(long id) {
            spaceshipCriteria.addPredicate(spaceship -> spaceship.getId() == id);
            return this;
        }

        public SpaceshipCriteria.Builder whereCrewMoreThan(long amountOfCrew) {
            spaceshipCriteria.addPredicate(spaceship -> (long) spaceship.getCrew().values().size() > amountOfCrew);
            return this;
        }

        public SpaceshipCriteria.Builder whereFlightDistanceMoreThan(Long flightDistance) {
            spaceshipCriteria.addPredicate(spaceship -> spaceship.getFlightDistance() > flightDistance);
            return this;
        }

        public SpaceshipCriteria.Builder whereNameStartsWith(String beginOfName) {
            spaceshipCriteria.addPredicate(spaceship -> spaceship.getName().startsWith(beginOfName));
            return this;
        }

        public SpaceshipCriteria.Builder whereNameEndsWith(String endOfName) {
            spaceshipCriteria.addPredicate(spaceship -> spaceship.getName().endsWith(endOfName));
            return this;
        }

        public SpaceshipCriteria.Builder whereSpaceshipIsReadyForNextMission() {
            spaceshipCriteria.addPredicate(Spaceship::isReadyForNextMission);
            return this;
        }

        public SpaceshipCriteria build() {
            return spaceshipCriteria;
        }
    }


}

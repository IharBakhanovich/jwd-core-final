package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Planet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class PlanetCriteria extends Criteria<Planet> {

    private final Collection<Predicate<Planet>> predicates = new ArrayList<>();

    private void addPredicate(Predicate<Planet> newPredicate) {
        predicates.add(newPredicate);
    }

    public Collection<Predicate<Planet>> getPredicates() {
        return predicates;
    }

    public static class Builder {
        private final PlanetCriteria planetCriteria;

        public Builder() {
            planetCriteria = new PlanetCriteria();
        }

        public PlanetCriteria.Builder whereIdEquals(long id) {
            planetCriteria.addPredicate(planet -> planet.getId() == id);
            return this;
        }

        public PlanetCriteria build() {
            return planetCriteria;
        }
    }
}

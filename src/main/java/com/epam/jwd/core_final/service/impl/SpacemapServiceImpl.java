package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.service.SpacemapService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// the Singleton
public class SpacemapServiceImpl implements SpacemapService {

    private static SpacemapServiceImpl spacemapService;
    ApplicationContext applicationContext;

    private SpacemapServiceImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static SpacemapServiceImpl getSpacemapServiceImpl(ApplicationContext applicationContext) throws IOException {
        if (spacemapService == null) {
            spacemapService = new SpacemapServiceImpl(applicationContext);
        }
        return spacemapService;
    }

    @Override
    public Planet getRandomPlanet() {
        ArrayList<Planet> planets = (ArrayList<Planet>) applicationContext.retrieveBaseEntityList(Planet.class);
        int i = (int) (Math.random() * planets.size());
        return planets.get(i);
    }

    @Override
    public int getDistanceBetweenPlanets(Planet first, Planet second) {
        return (int) first.getLocation().getDistanceBetwiinPoints(second.getLocation());
    }

    public List<Planet> findAllPlanets() {
        return (List<Planet>) applicationContext.retrieveBaseEntityList(Planet.class);
    }

    public List<Planet> findAllPlanetsByCriteria(Criteria<? extends Planet> planetCriteria) {

        Collection<? extends Predicate<? extends Planet>> predicates = planetCriteria.getPredicates();
        Stream<Planet> stream = applicationContext.retrieveBaseEntityList(Planet.class).stream();
        for (Predicate<? extends Planet> predicate : predicates) {
            stream = stream.filter((Predicate<? super Planet>) predicate);
        }
        return stream.collect(Collectors.toList());
    }
}

package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.InputReaderUtil.Impl.InputCrewReader;
import com.epam.jwd.core_final.InputReaderUtil.Impl.InputPlanetReader;
import com.epam.jwd.core_final.InputReaderUtil.Impl.InputSpaceshipReader;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

// todo
public class NassaContext implements ApplicationContext {

    private static final Logger logger = LoggerFactory.getLogger(InputCrewReader.class);

    private Collection<FlightMission> flightMissions = new ArrayList<>();
    // no getters/setters for them
    private final Collection<CrewMember> crewMembers = new ArrayList<>();
    private final Collection<Spaceship> spaceships = new ArrayList<>();
    private final Collection<Planet> planetMap = new ArrayList<>();

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass)
            throws IllegalArgumentException {
        switch (tClass.getName()) {
            case "com.epam.jwd.core_final.domain.CrewMember":
                return (Collection<T>) crewMembers;
            case "com.epam.jwd.core_final.domain.Spaceship":
                return (Collection<T>) spaceships;
            case "com.epam.jwd.core_final.domain.Planet":
                return (Collection<T>) planetMap;
            case "com.epam.jwd.core_final.domain.FlightMission":
                return (Collection<T>) flightMissions;
        }
        throw new IllegalArgumentException("There exist no such a collection of tClass in NasaContext class");
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {

        try {
            populateCollections();
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new InvalidStateException(exception.getMessage());
        }
    }

    private void populateCollections() throws IOException {
        logger.info("NassaContext.init() has started");

        ApplicationProperties applicationProperties = ApplicationProperties.getApplicationProperties();
        String inputRootDir = "src/main/resources/" + applicationProperties.getInputRootDir() + "/";
        System.out.println("Input root is: " + inputRootDir); ///////////////////////////////////////////

        String pathToCrewFile = inputRootDir + applicationProperties.getCrewFileName();
        InputCrewReader inputCrewReader = new InputCrewReader(pathToCrewFile, ";", ",");

        String pathToSpaceMapFile = inputRootDir + applicationProperties.getSpaceMapFileName();
        InputPlanetReader inputPlanetReader = new InputPlanetReader(pathToSpaceMapFile, ",");

        String pathToSpaceshipsFile = inputRootDir + applicationProperties.getSpaceshipsFileName();
        InputSpaceshipReader inputSpaceshipReader
                = new InputSpaceshipReader(pathToSpaceshipsFile, ";", ",", ":");
        try {
            changeField("crewMembers", inputCrewReader.fetchMembers());
            changeField("spaceships", inputSpaceshipReader.fetchMembers());
            changeField("planetMap", inputPlanetReader.fetchMembers());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

//             NassaContext nassaContext = new NassaContext(inputCrewReader.fetchMembers(),
//                inputSpaceshipReader.fetchMembers(), inputPlanetReader.fetchMembers());

        System.out.println("COLLECTIONS ARE: ");/////////////////////////////////////////////////////////
        System.out.println("CREWMEMBERS");
        for (CrewMember crewMember : crewMembers
        ) {
            System.out.println(crewMember);
        }
        System.out.println("SPACESHIPS");
        for (Spaceship spaceship : spaceships
        ) {
            System.out.println(spaceship);
        }
        System.out.println("PLANETS");
        for (Planet planet : planetMap
        ) {
            System.out.println(planet);
        }
    }

    private void changeField(String name, Object value) throws IllegalAccessException, NoSuchFieldException {
        Field field = NassaContext.class.getDeclaredField(name);
        field.setAccessible(true);
        field.set(this, value);
    }

    public Collection<FlightMission> getFlightMissions() {
        return flightMissions;
    }

    public void setFlightMissions(Collection<FlightMission> flightMissions) {
        this.flightMissions = flightMissions;
    }
}

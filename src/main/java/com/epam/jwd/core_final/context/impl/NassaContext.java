package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.InputReaderUtil.Impl.InputCrewReader;
import com.epam.jwd.core_final.InputReaderUtil.Impl.InputPlanetReader;
import com.epam.jwd.core_final.InputReaderUtil.Impl.InputSpaceshipReader;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;
import com.epam.jwd.core_final.util.IDGenerator;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

// todo
public class NassaContext implements ApplicationContext {

    private static final Logger logger = LoggerFactory.getLogger(InputCrewReader.class);

    private final Collection<FlightMission> flightMissions = new ArrayList<>();
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

        // generates FlightMissions
        int random = (int) (Math.random() * 50);
        for (int counter = 1; counter < random; counter++) {
            FlightMission mission = createMission();
            if (mission != null) {
                flightMissions.add(mission);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String outputDir = "output";
        try {
            outputDir = PropertyReaderUtil.getPropertyReaderUtil().getOutputRootDir();
        } catch (IOException exception) {
            exception.printStackTrace();
            logger.error("I/O error during the loading properties");
        }
        String path = "src/main/resources/" + outputDir + "/missions.json";
        File file = new File(path);
        file.getParentFile().mkdirs();
        try {
            objectMapper.writeValue(new File(path), flightMissions);
        } catch (IOException exception) {
            exception.printStackTrace();
            logger.error("Error occured during recording the 'missions.json' file");
        }
    }

    private void populateCollections() throws IOException {

        ApplicationProperties applicationProperties = ApplicationProperties.getApplicationProperties();
        String inputRootDir = "src/main/resources/" + applicationProperties.getInputRootDir() + "/";

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
    }

    private void changeField(String name, Object value) throws IllegalAccessException, NoSuchFieldException {
        Field field = NassaContext.class.getDeclaredField(name);
        field.setAccessible(true);
        field.set(this, value);
    }

    private FlightMission createMission() {
        final int DAYSINYEAR = 365;
        int dayOfTheYearOne = (int) (Math.random() * DAYSINYEAR);
        int dayOfTheYearTwo = (int) (Math.random() * DAYSINYEAR);
        LocalDate startDate = LocalDate.ofYearDay(2022, dayOfTheYearOne);
        LocalDate endDate = LocalDate.ofYearDay(2023, dayOfTheYearTwo);
        Planet planetFrom = null;
        Planet planetTo = null;
        Long distance = 0L;
        try {
            planetFrom = SpacemapServiceImpl.getSpacemapServiceImpl(this).getRandomPlanet();
            planetTo = SpacemapServiceImpl.getSpacemapServiceImpl(this).getRandomPlanet();
            distance = Long.valueOf(SpacemapServiceImpl
                    .getSpacemapServiceImpl(this).getDistanceBetweenPlanets(planetFrom, planetTo));
        } catch (IOException exception) {
            exception.printStackTrace();
            logger.warn("The I/O error occurred during the mission creating");
        }
        MissionResult missionResult = MissionResult.IN_PROGRESS;

        String name = "From_" + planetFrom.getName() + "_To_" + planetTo.getName();
        if (!planetFrom.getId().equals(planetTo.getId())) {
            return FlightMissionFactory.INSTANCE.create(IDGenerator.INSTANCE.getId(),
                    name, startDate, endDate, distance, missionResult, planetFrom, planetTo);
        }
        return null;
    }
}

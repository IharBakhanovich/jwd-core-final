package com.epam.jwd.core_final.InputReaderUtil.Impl;

import com.epam.jwd.core_final.InputReaderUtil.InputReader;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import com.epam.jwd.core_final.exception.EmptyInputFileException;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.util.IDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InputPlanetReader implements InputReader<Planet> {

    private static final Logger logger = LoggerFactory.getLogger(InputPlanetReader.class);

    String path;
    String splitter;

    public InputPlanetReader(String path, String splitter) {
        this.path = path;
        this.splitter = splitter;
    }

    @Override
    public Collection<Planet> fetchMembers() {
        BufferedReader bufferReader = null;
        String line = "";
        Collection<Planet> planets = new ArrayList<>();
        List<String[]> rawPlanets = new ArrayList<>();

        try {
            bufferReader = new BufferedReader(new FileReader(path));
            while ((line = bufferReader.readLine()) != null) {
                rawPlanets.add(line.split(splitter));
            }
            try {
                planets = makeArrayOfPlanet(rawPlanets);
            } catch (EmptyInputFileException exception) {
                exception.printStackTrace();
                logger.error("Error occurred during planets collection creation");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (bufferReader != null) {
                try {
                    bufferReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("Error occurred during closing bufferReader.");
                }
            }
        }
        return planets;
    }

    private Collection<Planet> makeArrayOfPlanet(List<String[]> rawPlanets) throws EmptyInputFileException {
        Collection<Planet> planets = new ArrayList<>();
        if (rawPlanets == null) {
            throw new EmptyInputFileException("The map is empty - there are no planets");
        }
        //(x,y) is the point of the planet
        for (int y = 0; y < rawPlanets.size(); y++) {
            for (int x = 0; x < rawPlanets.get(y).length; x++) {
                if (!rawPlanets.get(y)[x].equals("null")) {
                    String name = rawPlanets.get(y)[x];
                    Point point = new Point(x, y);
                    Planet planet = PlanetFactory.INSTANCE.create(IDGenerator.INSTANCE.getId(), name, point);
                    planets.add(planet);
                }
            }
        }
        return planets;
    }
}

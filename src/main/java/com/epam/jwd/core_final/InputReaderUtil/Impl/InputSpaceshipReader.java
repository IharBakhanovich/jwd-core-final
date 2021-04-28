package com.epam.jwd.core_final.InputReaderUtil.Impl;

import com.epam.jwd.core_final.InputReaderUtil.InputReader;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidAmountOfParametersException;
import com.epam.jwd.core_final.factory.impl.SpaceShipFactory;
import com.epam.jwd.core_final.util.IDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InputSpaceshipReader implements InputReader<Spaceship> {

    private static final Logger logger = LoggerFactory.getLogger(InputCrewReader.class);

    String path;
    String splitter1;
    String splitter2;
    String splitter3;

    public InputSpaceshipReader(String path, String splitter1, String splitter2, String splitter3) {
        this.path = path;
        this.splitter1 = splitter1;
        this.splitter2 = splitter2;
        this.splitter3 = splitter3;
    }

    @Override
    public Collection<Spaceship> fetchMembers() {
        BufferedReader bufferReader = null;
        String line = "";
        Collection<Spaceship> spaceships = new ArrayList<>();
        List<String[]> rawSpaceships = new ArrayList<>();

        try {
            bufferReader = new BufferedReader(new FileReader(path));
            while ((line = bufferReader.readLine()) != null) {
                rawSpaceships.add(line.split(splitter1));
            }
            try {
                spaceships = makeArrayOfSpaceships(rawSpaceships);
            } catch (InvalidAmountOfParametersException exception) {
                logger.error(exception.getMessage(), new InvalidAmountOfParametersException(exception.getMessage()));
                System.out.println("The program due to unknown format of inputs can not start");
                exception.printStackTrace();
            }
        } catch (IOException exception) {
            logger.error(exception.getMessage(), new IOException(exception.getMessage()));
            System.out.println("The program due to input/output error can not start");
            exception.printStackTrace();
        } finally {
            if (bufferReader != null) {
                try {
                    bufferReader.close();
                } catch (IOException exception) {
                    logger.error(exception.getMessage(), new IOException(exception.getMessage()));
                    System.out.println("The program due to input/output error can not start");
                    exception.printStackTrace();
                }
            }
        }
        return spaceships;
    }

    private Collection<Spaceship> makeArrayOfSpaceships(List<String[]> rawSpaceships)
            throws InvalidAmountOfParametersException {
        Collection<Spaceship> spaceships = new ArrayList<>();

        if (rawSpaceships.get(0).length != 3) {
            throw new InvalidAmountOfParametersException("The amount of parameters are wrong");
        }

        for (String[] rawSpaceship : rawSpaceships
        ) {
            if (!(rawSpaceship[0].charAt(0) == '#')) {
                String name = rawSpaceship[0];
                Long distance = Long.valueOf(rawSpaceship[1]);
                Map<Role, Short> crew = parseCrew(rawSpaceship[2].substring(1,rawSpaceship[2].length()-1));
                Spaceship spaceship
                        = SpaceShipFactory.INSTANCE.create(IDGenerator.INSTANCE.getId(), name, crew, distance);
                spaceships.add(spaceship);

            }
        }
        return spaceships;
    }

    private Map<Role, Short> parseCrew(String crew) {
        Map<Role, Short> map = new HashMap<>();
        String[] members = crew.split(splitter2);
        for (String member:members
             ) {
            String[] pair = member.split(splitter3);
            Role role = Role.resolveRoleById(Long.parseLong(pair[0]));
            Short amount = Short.parseShort(pair[1]);
            map.put(role, amount);
        }
        return map;
    }
}

package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.NotAbleToBeAssignedException;
import com.epam.jwd.core_final.exception.NotAbleToBeCreatedException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.SpaceShipFactory;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.util.IDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//the singleton
public class SpaceshipServiceImpl implements SpaceshipService {

    private static final Logger logger = LoggerFactory.getLogger(SpaceshipServiceImpl.class);

    private static SpaceshipServiceImpl spaceshipService;
    ApplicationContext applicationContext;

    private SpaceshipServiceImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static SpaceshipServiceImpl getSpaceshipServiceImpl(ApplicationContext applicationContext) throws IOException {
        if (spaceshipService == null) {
            spaceshipService = new SpaceshipServiceImpl(applicationContext);
        }
        return spaceshipService;
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return (List<Spaceship>) applicationContext.retrieveBaseEntityList(Spaceship.class);
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> spaceShipCriteria) {

        Collection<? extends Predicate<? extends Spaceship>> predicates = spaceShipCriteria.getPredicates();
        Stream<Spaceship> stream = applicationContext.retrieveBaseEntityList(Spaceship.class).stream();
        for (Predicate<? extends Spaceship> predicate : predicates) {
            stream = stream.filter((Predicate<? super Spaceship>) predicate);
        }
        return stream.collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {

        Stream<Spaceship> stream = applicationContext.retrieveBaseEntityList(Spaceship.class).stream();
        for (Predicate<? extends Spaceship> predicate : criteria.getPredicates()) {
            stream.filter((Predicate<? super Spaceship>) predicate);
        }
        if (stream.count() == 0) {
            return Optional.empty();
        } else {
            return stream.findAny();
        }
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        SpaceshipCriteria criteria = new SpaceshipCriteria.Builder().whereIdEquals(spaceship.getId()).build();
        Spaceship updatedSpaceShip = null;
        // deletes spaceShip from the context
        applicationContext.retrieveBaseEntityList(Spaceship.class)
                .removeIf(spaceship1 -> spaceship1.getId().equals(spaceship.getId()));
        updatedSpaceShip = SpaceShipFactory.INSTANCE.create(spaceship.getId(), spaceship.getName(),
                spaceship.getCrew(), spaceship.getFlightDistance());
        // wrights updatedSpaceShip to the context
        applicationContext.retrieveBaseEntityList(Spaceship.class).add(updatedSpaceShip);
        return updatedSpaceShip;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship) throws NotAbleToBeAssignedException {
        Collection<FlightMission> flightMissions = applicationContext.retrieveBaseEntityList(FlightMission.class);
        if (isExist(spaceship)) {
            logger.info(String.format("The Spiceship %s does not exist",
                    spaceship.getName()));
            throw new UnknownEntityException("There is no such a spaceShip in system.");
        }
        for (FlightMission flightMission : flightMissions
        ) {
            if (spaceship.getFlightDistance() < flightMission.getDistance()) {
                logger.info(String.format("The Spiceship %s is not able to be assigned on the mission %s",
                        spaceship.getName(), flightMission.getName()));
                throw new NotAbleToBeAssignedException(
                        String.format("This crewMember is not able to be assigned on %s mission", flightMission.getName()));

            } else
                flightMission.setAssignedSpaceShip(spaceship);
            // spaceShip is assigned
            return;
        }
    }

    private boolean isExist(Spaceship spaceship) {
        SpaceshipCriteria criteria = new SpaceshipCriteria.Builder().whereIdEquals(spaceship.getId()).build();
        return findSpaceshipByCriteria(criteria).isPresent();
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws RuntimeException {
        //CrewMemberCriteria criteria = new CrewMemberCriteria.Builder().whereIdEquals(crewMember.getId()).build();
        if (isExist(spaceship)) {
            logger.info("Attempt to create spaceShip with existed in context id");
            throw new NotAbleToBeCreatedException("This crewMember can not be created");
        }

        Spaceship newSpaceShip = SpaceShipFactory.INSTANCE.create(IDGenerator.INSTANCE.getId(),
                spaceship.getName(), spaceship.getCrew(), spaceship.getFlightDistance());
        applicationContext.retrieveBaseEntityList(Spaceship.class).add(newSpaceShip);
        return newSpaceShip;
    }
}

package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.NotAbleToBeAssignedException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.util.IDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MissionServiceImpl implements MissionService {
    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(CrewServiceImpl.class);

    ApplicationContext applicationContext;

    @Override
    public List<FlightMission> findAllMissions() {
        return (List<FlightMission>) applicationContext.retrieveBaseEntityList(FlightMission.class);
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> flightMissionCriteria) {
        Collection<? extends Predicate<? extends FlightMission>> predicates = flightMissionCriteria.getPredicates();
        Stream<FlightMission> stream = applicationContext.retrieveBaseEntityList(FlightMission.class).stream();
        for (Predicate<? extends FlightMission> predicate : predicates) {
            stream = stream.filter((Predicate<? super FlightMission>) predicate);
        }
        return stream.collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        Stream<FlightMission> stream = applicationContext.retrieveBaseEntityList(FlightMission.class).stream();
        for (Predicate<? extends FlightMission> predicate : criteria.getPredicates()) {
            stream.filter((Predicate<? super FlightMission>) predicate);
        }
        if (stream.count() == 0) {
            return Optional.empty();
        } else {
            return stream.findAny();
        }
    }

    @Override
    public FlightMission updateSpaceshipDetails(FlightMission flightMission) {
        FlightMissionCriteria criteria = new FlightMissionCriteria.Builder().whereIdEquals(flightMission.getId()).build();
        FlightMission updatedFlightMission = null;
        if (findMissionByCriteria(criteria).isPresent()) {
            // deletes crewMember from the context
            applicationContext.retrieveBaseEntityList(CrewMember.class)
                    .removeIf((Predicate<? super CrewMember>) criteria.getPredicates());
            updatedFlightMission = FlightMissionFactory.INSTANCE.create(flightMission.getId(), flightMission.getName(),
                    flightMission.getStartDate(), flightMission.getEndDate(), flightMission.getDistance(),
                    flightMission.getMissionResult(), flightMission.getFrom(), flightMission.getTo());
            // wrights updatedCrewMember to the context
            applicationContext.retrieveBaseEntityList(FlightMission.class).add(updatedFlightMission);
        } else {
            logger.info("The attempt to update a not existed in context mission.");
            throw new UnknownEntityException("there is no such a mission in system");
        }
        return updatedFlightMission;
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) {

        FlightMissionCriteria criteria = new FlightMissionCriteria.Builder().whereIdEquals(flightMission.getId()).build();
        if (findMissionByCriteria(criteria).isPresent()) {
            logger.info("Attempt to create duplicate of the existed in context mission");
            throw new NotAbleToBeAssignedException("This crewMember can not be created");
        }

        FlightMission newFlightMission = FlightMissionFactory.INSTANCE.create(IDGenerator.INSTANCE.getId(), flightMission.getName(),
                flightMission.getStartDate(), flightMission.getEndDate(), flightMission.getDistance(),
                flightMission.getMissionResult(), flightMission.getFrom(), flightMission.getTo());
        applicationContext.retrieveBaseEntityList(FlightMission.class).add(newFlightMission);
        return newFlightMission;
    }
}

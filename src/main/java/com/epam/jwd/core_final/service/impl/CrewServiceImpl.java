package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.NotAbleToBeAssignedException;
import com.epam.jwd.core_final.exception.NotAbleToBeCreatedException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.CrewService;
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

// the Singleton
public class CrewServiceImpl implements CrewService {

    private static final Logger logger = LoggerFactory.getLogger(CrewServiceImpl.class);

    private static CrewServiceImpl crewService;

    ApplicationContext applicationContext;

    private CrewServiceImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static CrewServiceImpl getCrewServiceImpl(ApplicationContext applicationContext) throws IOException {
        if (crewService == null) {
            crewService = new CrewServiceImpl(applicationContext);
        }
        return crewService;
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return (List<CrewMember>) applicationContext.retrieveBaseEntityList(CrewMember.class);
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> crewMemberCriteria) {

        Collection<? extends Predicate<? extends CrewMember>> predicates = crewMemberCriteria.getPredicates();
        Stream<CrewMember> stream = applicationContext.retrieveBaseEntityList(CrewMember.class).stream();
        for (Predicate<? extends CrewMember> predicate : predicates) {
            stream = stream.filter((Predicate<? super CrewMember>) predicate);
        }
        return stream.collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {

        Stream<CrewMember> stream = applicationContext.retrieveBaseEntityList(CrewMember.class).stream();
        for (Predicate<? extends CrewMember> predicate : criteria.getPredicates()) {
            stream.filter((Predicate<? super CrewMember>) predicate);
        }
        if (stream.count() == 0) {
            return Optional.empty();
        } else {
            return stream.findAny();
        }
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        CrewMemberCriteria criteria = new CrewMemberCriteria.Builder().whereIdEquals(crewMember.getId()).build();
        CrewMember updatedCrewMember = null;
        if (findCrewMemberByCriteria(criteria).isPresent()) {
            // deletes crewMember from the context
            applicationContext.retrieveBaseEntityList(CrewMember.class)
                    .removeIf((Predicate<? super CrewMember>) criteria.getPredicates());
            updatedCrewMember = CrewMemberFactory.INSTANCE.create(crewMember.getId(), crewMember.getName(),
                    crewMember.getRole(), crewMember.getRank());
            // wrights updatedCrewMember to the context
            applicationContext.retrieveBaseEntityList(CrewMember.class).add(updatedCrewMember);
        } else {
            logger.error("The attempt to update a not existed in context member.");
            throw new UnknownEntityException("There is no such a crewMember in system.");
        }
        return updatedCrewMember;
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws NotAbleToBeAssignedException {
        Collection<FlightMission> flightMissions = applicationContext.retrieveBaseEntityList(FlightMission.class);
        for (FlightMission flightMission : flightMissions
        ) {
            Integer amountOfRoleInCrew
                    = checkAmountOfRoleInCrew(flightMission.getAssignedSpaceShip(), crewMember);
            Integer amountOfThisRoleOnSpaceShip
                    = checkAmountOfThisRoleOnSpaceShip(flightMission.getAssignedCrew(), crewMember);

            if (amountOfRoleInCrew == null) {
                logger.info(String.format("The CrewMember %s is not able to be assigned on the mission %s",
                        crewMember.getName(), flightMission.getName()));
                throw new NotAbleToBeAssignedException(
                        String.format("This crewMember is not able to be assigned on %s mission", flightMission.getName()));

            } else if (amountOfRoleInCrew > amountOfThisRoleOnSpaceShip) {
                flightMission.getAssignedCrew().add(crewMember);
                // crewMember is assigned
                return;
            }
        }
    }

    //checks if there is those Role on the assigned spaceship and how many
    private Integer checkAmountOfThisRoleOnSpaceShip(List<CrewMember> assignedCrew, CrewMember crewMember) {
        Integer amountOfThisRoleOnSpaceShip = 0;
        for (CrewMember assignedCrewMember : assignedCrew
        ) {
            if (assignedCrewMember.getRole() == crewMember.getRole()) {
                amountOfThisRoleOnSpaceShip++;
            }
        }
        return amountOfThisRoleOnSpaceShip;
    }

    //checks the amount of the role this crewMember on
    private Integer checkAmountOfRoleInCrew(Spaceship assignedSpaceShip, CrewMember crewMember) {
        return Integer.valueOf(assignedSpaceShip.getCrew().get(crewMember.getRole()));
    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws RuntimeException {
        CrewMemberCriteria criteria = new CrewMemberCriteria.Builder().whereIdEquals(crewMember.getId()).build();
        if (findCrewMemberByCriteria(criteria).isPresent()) {
            logger.info("Attempt to create crewMember with existed in context id");
            throw new NotAbleToBeCreatedException("This crewMember can not be created");
        }

        CrewMember newCrewMember = CrewMemberFactory.INSTANCE.create(IDGenerator.INSTANCE.getId(), crewMember.getName(),
                crewMember.getRole(), crewMember.getRank());
        applicationContext.retrieveBaseEntityList(CrewMember.class).add(newCrewMember);
        return newCrewMember;
    }
}

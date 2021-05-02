package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.PlanetCriteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

public enum ApplicationMenuImpl implements ApplicationMenu {
    INSTANCE;

    public static final String I_O_ERROR_BY_FETCHING_PLANETS_WITH_THE_METHOD_FIND_ALL_PLANETS
            = "I/O error by fetching planets with the method findAllPlanets()";
    public static final String I_O_ERROR_BY_FETCHING_CREW_MEMBERS_WITH_THE_METHOD_FIND_ALL_CREW_MEMBERS
            = "I/O error by fetching crewMembers with the method findAllCrewMembers()";
    public static final String I_O_ERROR_BY_FETCHING_SPACE_SHIPS_WITH_THE_METHOD_FIND_ALL_SPACESHIPS
            = "I/O error by fetching spaceShips with the method findAllSpaceships()";
    public static final String I_O_ERROR_BY_FETCHING_MISSIONS_WITH_THE_METHOD_FIND_ALL_MISSIONS
            = "I/O error by fetching missions with the method findAllMissions()";
    private final static String FIRST_LEVEL_MENU
            = "Input 'c', if you want to see all crewMembers and manage them." + "\n"
            + "Input 'p', if you want to see all planets." + "\n"
            + "Input 's', if you want to see all spaceships." + "\n"
            + "Input 'm', if you want to see all missions." + "\n"
            + "Input 'q' to quit from the system.";

    private final static String WRONG_COMMAND = "Error! You entered the wrong command";
    private final static String HI =
            "Hi. It is the program, that helps user to plan spaceMissions."
                    + " You can see the available crewMembers, planets, spaceShips or missions." + "\n \n"
                    + "WELCOM TO THE 'UNIVERSE' APP" + "\n";
    private final static String NOTE =
            "It is the program, that helps user to plan spaceMissions."
                    + " You can see the available crewMembers, planets, spaceShips or missions." + "\n"
                    + FIRST_LEVEL_MENU + "\n"
                    + "WELCOM TO THE 'UNIVERSE' APP";
    private final static String TRY_AGAIN = "Please try again";
    private final static String CREW_PRESENTATION = "There are following crewMembers in system: ";
    private final static String PLANET_PRESENTATION = "There are following planets in system: ";
    private final static String SPACESHIP_PRESENTATION = "There are following spaceShips in system: ";
    private final static String MISSIONS_PRESENTATION = "There are following missions in system: ";
    private final static String CREWMEMBER_MENU
            = "\n"
            + "Input 'g id', if you want to see an information about the crewMember with the ID id." + "\n"
            + "Input 'u id', if you want to update the crewMember with the ID id." + "\n"
            + "Input 'q' to go to main menu.";
    private final static String PlANETS_MENU
            = "\n"
            + "Input 'd id1 id2', if you want to see a distance between the Planet with id = id1"
            + " and the Planet with id = id2." + "\n"
            + "Input 'r', if you want to get random Planet." + "\n"
            + "Input 'q' to go to main menu.";
    private static final Logger logger = LoggerFactory.getLogger(ApplicationMenuImpl.class);
    private static final String WRONG_ID = "You entered a wrond ID. ";
    private static final String CREW_BY_ID_PRESENTATION = "The required crewMember is:";
    private static final String NO_SUCH_ID_IN_SYSTEM = "There is no CrewMember with such an ID in system";
    private static final String CREWMEMBER_UPDATE_MENU
            = "\n"
            + "Input 'ro id', if you want to change the Role of the crewMember. The avaliable ID of the Role are:" + "\n"
            + " 1 - MISSION_SPECIALIST" + "\n"
            + " 2 - FLIGHT_ENGINEER" + "\n"
            + " 3 - PILOT" + "\n"
            + " 4 - COMMANDER" + "\n"
            + "Input 'ra id', if you want to change the Rank of the crewMember. The avaliable ID of the Rank are:" + "\n"
            + " 1 - TRAINEE" + "\n"
            + " 2 - SECOND_OFFICER" + "\n"
            + " 3 - FIRST_OFFICER" + "\n"
            + " 4 - CAPTAIN" + "\n"
            + "Input 'i', if you want to make the CrewMember NOT Ready for next mission." + "\n"
            + "Input 'q' to go to CrewMember menu (one level up).";
    private static final String CORRECT_VALUES = "ID should be 1 , 2 , 3 or 4";
    public static final String GREAT_WHAT_NEXT = "Great. What next?";
    public static final String THE_CREW_MEMBER_TO_UPDATE_IS = "the crewMember to update is: ";
    public static final String I_O_EXCEPTION_DURING_FINDING_CREW_MEMBER_BY_CRITERIA
            = "I/O exception during finding crewMember by criteria";
    public static final String CREW_MEMBER_AFTER_UPDATE_IS = "crewMember after update is: ";
    public static final String I_O_ERROR_DURING_UPDATE_CREW_MEMBER_DETAILS_METHOD_OF_CREW_SERVICE_IMPL
            = "I/O error during updateCrewMemberDetails() method of CrewServiceImpl";
    public static final String I_O_ERROR_DURING_THE_GET_RANDOM_PLANET_OF_SPACEMAP_SERVICE
            = "I/O error during the getRandomPlanet of spacemapService.";
    public static final String THERE_ARE_NO_PLANETS_WITH_SUCH_IDS_IN_SYSTEM = "There are no planets with such IDs in System";
    public static final String I_O_ERROR_DURING_FETCHING_ALL_PLANETS_BY_SPACEMAP_SERVICE = "I/O error during fetching all planets by spacemapService";
    public static final String THERE_ARE_NO_SUCH_PLANETS_IN_SYSTEM = "There are no such planets in system";
    public static final String I_O_ERROR_DURING_GET_DISTANCE_METHOD_IN_SPACEMAP_SERVICE = "I/O error during getDistance method in SpacemapService";

    ApplicationContext applicationContext;

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public Object printAvailableOptions() {

        return ApplicationMenu.super.printAvailableOptions();
    }

    @Override
    public Object handleUserInput(ApplicationContext applicationContext) {
        String readScanner;
        String[] tokens; // the array for save inputs from user by words
        Scanner scanner = new Scanner(System.in).useDelimiter("\\s+");
        System.out.println("\n" + HI);
        System.out.println(FIRST_LEVEL_MENU);
        tokens = getUserInput(scanner);
        while (!tokens[0].equalsIgnoreCase("q")) {
            //catches the wrong name of the command
            while (!tokens[0].equalsIgnoreCase("c")
                    && !tokens[0].equalsIgnoreCase("p")
                    && !tokens[0].equalsIgnoreCase("s")
                    && !tokens[0].equalsIgnoreCase("m")) {
                error(WRONG_COMMAND, NOTE, TRY_AGAIN);
                tokens = getUserInput(scanner);
            }
            switch (tokens[0]) {
                case ("c"):
                    System.out.println(CREW_PRESENTATION);
                    try {
                        for (CrewMember crewMemder
                                : CrewServiceImpl
                                .getCrewServiceImpl(applicationContext)
                                .findAllCrewMembers()
                        ) {
                            System.out.println(crewMemder);
                        }
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        logger.error(I_O_ERROR_BY_FETCHING_CREW_MEMBERS_WITH_THE_METHOD_FIND_ALL_CREW_MEMBERS);
                    }
                    crewMemberMenu(scanner, tokens);
                    break;
                case ("p"):
                    System.out.println(PLANET_PRESENTATION);
                    try {
                        for (Planet planet
                                : SpacemapServiceImpl
                                .getSpacemapServiceImpl(applicationContext)
                                .findAllPlanets()
                        ) {
                            System.out.println(planet);
                        }
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        logger.error(I_O_ERROR_BY_FETCHING_PLANETS_WITH_THE_METHOD_FIND_ALL_PLANETS);
                    }
                    planetsMenu(scanner, tokens);
                    break;
                case ("s"):
                    System.out.println(SPACESHIP_PRESENTATION);
                    try {
                        for (Spaceship spaceship
                                : SpaceshipServiceImpl
                                .getSpaceshipServiceImpl(applicationContext)
                                .findAllSpaceships()
                        ) {
                            System.out.println(spaceship);
                        }
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        logger.error(I_O_ERROR_BY_FETCHING_SPACE_SHIPS_WITH_THE_METHOD_FIND_ALL_SPACESHIPS);
                    }
                    break;
                case ("m"):
                    System.out.println(MISSIONS_PRESENTATION);
                    try {
                        for (FlightMission flightMission
                                : MissionServiceImpl
                                .getMissionServiceImpl(applicationContext)
                                .findAllMissions()
                        ) {
                            System.out.println(flightMission);
                        }
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        logger.error(I_O_ERROR_BY_FETCHING_MISSIONS_WITH_THE_METHOD_FIND_ALL_MISSIONS);
                    }
                    break;
            }
            System.out.println(GREAT_WHAT_NEXT);
            System.out.println(FIRST_LEVEL_MENU);
            tokens = getUserInput(scanner);
        }
        return null;
    }

    private void planetsMenu(Scanner scanner, String[] tokens) {
        System.out.println(PlANETS_MENU);
        tokens = getUserInput(scanner);
        while (!tokens[0].equalsIgnoreCase("q")) {
            //catches the wrong name of the command
            while (!tokens[0].equalsIgnoreCase("d")
                    && !tokens[0].equalsIgnoreCase("r")
                    && !tokens[0].equalsIgnoreCase("q")) {
                error(WRONG_COMMAND, PlANETS_MENU, TRY_AGAIN);
                tokens = getUserInput(scanner);
            }
            switch (tokens[0]) {
                case ("d"):
                    if (isCheckedForIDs(tokens)) break;
                    // Collection<Planet> planets;
                    Collection<Planet> planetByIDOne = null;
                    Collection<Planet> planetByIDTwo = null;
                    PlanetCriteria planetCriteriaOne
                            = new PlanetCriteria.Builder()
                            .whereIdEquals(Long.parseLong(tokens[1]))
                            .build();
                    PlanetCriteria planetCriteriaTwo
                            = new PlanetCriteria.Builder()
                            .whereIdEquals(Long.parseLong(tokens[2]))
                            .build();
                    try {
                        planetByIDOne
                                = SpacemapServiceImpl
                                .getSpacemapServiceImpl(applicationContext)
                                .findAllPlanetsByCriteria(planetCriteriaOne);
                        planetByIDTwo
                                = SpacemapServiceImpl
                                .getSpacemapServiceImpl(applicationContext)
                                .findAllPlanetsByCriteria(planetCriteriaTwo);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        logger.error(I_O_EXCEPTION_DURING_FINDING_CREW_MEMBER_BY_CRITERIA);
                    }
                    int distance = 0;
                    if (planetByIDOne == null || planetByIDOne.size() < 1
                            || planetByIDTwo == null || planetByIDTwo.size() < 1) {
                        error(THERE_ARE_NO_SUCH_PLANETS_IN_SYSTEM, PlANETS_MENU, TRY_AGAIN);
                        break;
                    } else {
                        Planet planet1 = planetByIDOne.iterator().next();
                        Planet planet2 = planetByIDTwo.iterator().next();
//                        Planet[] planetsOne = (Planet[]) planetByIDOne.toArray();
//                        Planet[] planetsTwo = (Planet[]) planetByIDTwo.toArray();
                        try {
                            distance = SpacemapServiceImpl
                                    .getSpacemapServiceImpl(applicationContext)
                                    .getDistanceBetweenPlanets(planet1, planet2);
                            System.out.println("The distance between" + planet1
                                    + " and " + planet2 + "is: " + distance + " km.");
                        } catch (IOException exception) {
                            exception.printStackTrace();
                            logger.error(I_O_ERROR_DURING_GET_DISTANCE_METHOD_IN_SPACEMAP_SERVICE);
                        }
                    }
                    break;
                case ("r"):
                    Planet planet = null;
                    try {
                        planet = SpacemapServiceImpl.getSpacemapServiceImpl(applicationContext).getRandomPlanet();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        System.out.println(I_O_ERROR_DURING_THE_GET_RANDOM_PLANET_OF_SPACEMAP_SERVICE);
                    }
                    System.out.println("The random planet is: " + planet);
                    break;
                case ("q"):
                    break;
            }
            if (tokens[0].equalsIgnoreCase("q")) {
                return;
            }
            System.out.println(PlANETS_MENU);
            tokens = getUserInput(scanner);
        }
    }

    private void crewMemberMenu(Scanner scanner, String[] tokens) {
        System.out.println(CREWMEMBER_MENU);
        tokens = getUserInput(scanner);
        while (!tokens[0].equalsIgnoreCase("q")) {
            //catches the wrong name of the command
            while (!tokens[0].equalsIgnoreCase("g")
                    && !tokens[0].equalsIgnoreCase("u")
                    && !tokens[0].equalsIgnoreCase("q")) {
                error(WRONG_COMMAND, CREWMEMBER_MENU, TRY_AGAIN);
                tokens = getUserInput(scanner);
            }
            switch (tokens[0]) {
                case ("g"):
                    if (isCheckedForID(tokens)) break;
                    Collection<CrewMember> crewMembers = crewMembersInSystem(tokens[1]);

                    if (crewMembers.isEmpty()) {
                        System.out.println(NO_SUCH_ID_IN_SYSTEM);
                    } else {
                        System.out.println(CREW_BY_ID_PRESENTATION);
                        for (CrewMember crewMemder : crewMembers
                        ) {
                            System.out.println(crewMemder);
                        }
                    }
                    break;
                case ("u"):
                    if (isCheckedForID(tokens)) break;
                    Collection<CrewMember> crewMembersUpdate = crewMembersInSystem(tokens[1]);
                    if (crewMembersUpdate.isEmpty()) {
                        System.out.println(NO_SUCH_ID_IN_SYSTEM);
                    } else {
                        CrewMember crewMember = crewMembersUpdate.stream().findFirst().get();
                        System.out.println(THE_CREW_MEMBER_TO_UPDATE_IS + crewMember);
                        updateCrewMemberMenu(scanner,
                                parseValue(tokens[1]),
                                crewMember,
                                tokens);
                    }
                    break;
                case ("q"):
                    break;
            }
            if (tokens[0].equalsIgnoreCase("q")) {
                return;
            }
            System.out.println(CREWMEMBER_MENU);
            tokens = getUserInput(scanner);
        }
    }

    private boolean isCheckedForID(String[] tokens) {
        if (checkCommandOfTwo(tokens, "") != 0) {
            if (parseValue(tokens[1]) == 0) {
                System.out.println(NO_SUCH_ID_IN_SYSTEM);
                return false;
            }
            return false;
        }
        return true;
    }

    private boolean isCheckedForIDs(String[] tokens) {
        if (checkCommandOfThree(tokens, "") != 0) {
            if (parseValue(tokens[1]) == 0
                    || parseValue(tokens[2]) == 0) {
                System.out.println(THERE_ARE_NO_PLANETS_WITH_SUCH_IDS_IN_SYSTEM);
                return false;
            }
            return false;
        }
        return true;
    }

    private Collection<CrewMember> crewMembersInSystem(String token) {
        Collection<CrewMember> crewMemberByID = null;
        CrewMemberCriteria crewMemberCriteria
                = new CrewMemberCriteria.Builder()
                .whereIdEquals(Long.parseLong(token))
                .build();
        try {
            crewMemberByID
                    = CrewServiceImpl
                    .getCrewServiceImpl(applicationContext)
                    .findAllCrewMembersByCriteria(crewMemberCriteria);
        } catch (IOException exception) {
            exception.printStackTrace();
            logger.error(I_O_EXCEPTION_DURING_FINDING_CREW_MEMBER_BY_CRITERIA);
        }
        return crewMemberByID;
    }

    private void updateCrewMemberMenu(Scanner scanner, int id, CrewMember crewMember, String[] tokens) {
        System.out.println(CREWMEMBER_UPDATE_MENU);
        CrewMember crewMemberToWork = crewMember;
        tokens = getUserInput(scanner);
        String readScanner;
        while (!tokens[0].equalsIgnoreCase("q")) {
            //catches the wrong name of the command
            while (!tokens[0].equalsIgnoreCase("ro")
                    && !tokens[0].equalsIgnoreCase("ra")
                    && !tokens[0].equalsIgnoreCase("i")
                    && !tokens[0].equalsIgnoreCase("q")) {
                error(WRONG_COMMAND, CREWMEMBER_UPDATE_MENU, TRY_AGAIN);
                tokens = getUserInput(scanner);
            }
            switch (tokens[0]) {
                case ("ro"):
                    if (isCheckedForID(tokens)) break;
                    if (parseValue(tokens[1]) > 4) {
                        System.out.println(CORRECT_VALUES);
                    } else {
                        System.out.println(THE_CREW_MEMBER_TO_UPDATE_IS + crewMemberToWork);
                        CrewMember crewMemberAfterUpdate
                                = CrewMemberFactory.INSTANCE.create(
                                crewMemberToWork.getId(),
                                crewMemberToWork.getName(),
                                Role.resolveRoleById((long) parseValue(tokens[1])),
                                crewMemberToWork.getRank(),
                                crewMember.isReadyForNextMission());
                        crewMemberToWork = crewMemberAfterUpdate;
                        System.out.println(CREW_MEMBER_AFTER_UPDATE_IS + crewMemberAfterUpdate);
                        try {
                            CrewServiceImpl.
                                    getCrewServiceImpl(applicationContext).
                                    updateCrewMemberDetails(crewMemberAfterUpdate);

                        } catch (IOException exception) {
                            exception.printStackTrace();
                            logger.error(I_O_ERROR_DURING_UPDATE_CREW_MEMBER_DETAILS_METHOD_OF_CREW_SERVICE_IMPL);
                        }
                    }
                    break;
                case ("ra"):
                    if (isCheckedForID(tokens)) break;
                    if (parseValue(tokens[1]) > 4) {
                        System.out.println(CORRECT_VALUES);
                    } else {
                        System.out.println(THE_CREW_MEMBER_TO_UPDATE_IS + crewMemberToWork);
                        CrewMember crewMemberAfterUpdate
                                = CrewMemberFactory.INSTANCE.create(
                                crewMemberToWork.getId(),
                                crewMemberToWork.getName(),
                                crewMemberToWork.getRole(),
                                Rank.resolveRankById((long) parseValue(tokens[1])),
                                crewMember.isReadyForNextMission());
                        crewMemberToWork = crewMemberAfterUpdate;
                        System.out.println(CREW_MEMBER_AFTER_UPDATE_IS + crewMemberAfterUpdate);
                        try {
                            CrewServiceImpl.
                                    getCrewServiceImpl(applicationContext).
                                    updateCrewMemberDetails(crewMemberAfterUpdate);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                            logger.error(I_O_ERROR_DURING_UPDATE_CREW_MEMBER_DETAILS_METHOD_OF_CREW_SERVICE_IMPL);
                        }
                    }
                    break;
                case ("i"):
                    System.out.println(THE_CREW_MEMBER_TO_UPDATE_IS + crewMemberToWork);
                    crewMemberToWork.setReadyForNextMission(false);
                    CrewMember crewMemberAfterUpdate
                            = CrewMemberFactory.INSTANCE.create(
                            crewMemberToWork.getId(),
                            crewMemberToWork.getName(),
                            crewMemberToWork.getRole(),
                            crewMemberToWork.getRank(),
                            crewMemberToWork.isReadyForNextMission());
                    System.out.println(CREW_MEMBER_AFTER_UPDATE_IS + crewMemberToWork);
                    try {
                        CrewServiceImpl.
                                getCrewServiceImpl(applicationContext).
                                updateCrewMemberDetails(crewMemberAfterUpdate);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        logger.error(I_O_ERROR_DURING_UPDATE_CREW_MEMBER_DETAILS_METHOD_OF_CREW_SERVICE_IMPL);
                    }
                    break;
                case ("q"):
                    break;
            }
            if (tokens[0].equalsIgnoreCase("q")) {
                return;
            }
            System.out.println(CREWMEMBER_UPDATE_MENU);
            tokens = getUserInput(scanner);
        }
    }

    private int checkCommandOfTwo(String[] tokens, String note) {
        int id;
        if (tokens.length != 2) {
            error(WRONG_COMMAND, note, TRY_AGAIN);
            return 0;
        } else if (!isIdValid(tokens[1])) {
            error(WRONG_COMMAND, note, TRY_AGAIN);
            return 0;
        } else {
            if (parseValue(tokens[1]) == Integer.MIN_VALUE) {
                error(WRONG_ID, note, TRY_AGAIN);
                return 0;
            }
            id = parseValue(tokens[1]);
        }
        return id;
    }

    private int checkCommandOfThree(String[] tokens, String note) {
        int id;
        if (tokens.length != 3) {
            error(WRONG_COMMAND, note, TRY_AGAIN);
            return 0;
        } else if (!isIdValid(tokens[1])
                || !isIdValid(tokens[2])) {
            error(WRONG_COMMAND, note, TRY_AGAIN);
            return 0;
        } else {
            if (parseValue(tokens[1]) == Integer.MIN_VALUE
                    || parseValue(tokens[2]) == Integer.MIN_VALUE) {
                error(WRONG_ID, note, TRY_AGAIN);
                return 0;
            }
            id = parseValue(tokens[1]);
        }
        return id;
    }

    /**
     * Parses second parameter of user input from String to int.
     * Catches NumberFormatException and checks whether
     * the input number negative.
     *
     * @param token is the text entered by user.
     * @return the value if this value was entered by the user correctly,
     * or Integer.MIN_VALUE otherwise.
     */
    private int parseValue(String token) {
        int value;
        try {
            if (token.toCharArray()[0] == '-') {
                value = Integer.parseInt(token.substring(1));
                value = value * -1;
            } else {
                value = Integer.parseInt(token);
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return Integer.MIN_VALUE;
        }
        return value;
    }

    /**
     * Checks whether the value {@param token} a number.
     *
     * @param token is the value, that is the second parameter of the command.
     * @return true if the {@param value} a number and false otherwise
     */
    private boolean isIdValid(String token) {
        return token.matches("\\d+");
    }

    private String[] parseInput(String userInput) {
        String delimeter = "\\s+";
        //to save users inputs by words
        String[] userInputToArray = userInput.split(delimeter);

        // translates an expression to lowercase
        for (int i = 0; i < userInputToArray.length; i++) {
            userInputToArray[i] = userInputToArray[i].toLowerCase();
        }
        return userInputToArray;
    }

    private static void prompt() {
        System.out.print("> ");
    }

    private static void error(String message1, String message2,
                              String message3) {
        System.out.println(message1 + "\n" + "\n"
                + message2 + "\n" + "\n"
                + message3 + "\n");
        // prompt();
    }

    private String[] getUserInput(Scanner scanner) {
        prompt();
        String readScanner = scanner.nextLine();
        while (readScanner.trim().equalsIgnoreCase("")
                || readScanner.equalsIgnoreCase(" ")) {
            prompt();
            readScanner = scanner.nextLine();
        }
        return parseInput(readScanner);
    }
}
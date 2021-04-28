package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.Scanner;

public enum ApplicationMenuImpl implements ApplicationMenu {
    INSTANCE;

    private final static String FIRST_LEVEL_MENU = "Input 'c', if you want to see all crewMembers." + "\n"
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
                    + " You can see the available crewMembers, planets, spaceShips or missions."
                    + FIRST_LEVEL_MENU
                    + "WELCOM TO THE 'UNIVERSE' APP";
    private final static String TRY_AGAIN = "Please try again";
    private final static String CREW_PRESENTATION = "There are following crewMembers in system: ";
    private final static String PLANET_PRESENTATION = "There are following planets in system: ";
    private final static String SPACESHIP_PRESENTATION = "There are following spaceShips in system: ";
    private final static String MISSIONS_PRESENTATION = "There are following missions in system: ";

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
    public Object handleUserInput(ApplicationContext o) {
        String readScanner;
        String[] tokens; // the array for save inputs from user by words
        Scanner scanner = new Scanner(System.in).useDelimiter("\\s+");
        System.out.println("\n" + HI);
        tokens = getUserInput(scanner);
        while (!tokens[0].equals("q")) {
            //catches the wrong name of the command
            System.out.println("the token[0] is " + tokens[0]); //////////////////////////////
            while (!tokens[0].equalsIgnoreCase("c")
                    && !tokens[0].equalsIgnoreCase("p")
                    && !tokens[0].equalsIgnoreCase("s")
                    && !tokens[0].equalsIgnoreCase("m")) {
                error(WRONG_COMMAND, NOTE, TRY_AGAIN);
                readScanner = scanner.nextLine();
                while (readScanner.equalsIgnoreCase("")
                        || readScanner.equalsIgnoreCase(" ")) {
                    prompt();
                    readScanner = scanner.nextLine();
                }
                tokens = parseInput(readScanner);
            }
            switch (tokens[0]) {
                case ("c"):
                    System.out.println(CREW_PRESENTATION);
                    for (CrewMember crewMemder : o.retrieveBaseEntityList(CrewMember.class)
                    ) {
                        System.out.println(crewMemder);
                    }
                    break;
                case ("p"):
                    System.out.println(PLANET_PRESENTATION);
                    for (Planet planet : o.retrieveBaseEntityList(Planet.class)
                    ) {
                        System.out.println(planet);
                    }
                    break;
                case ("s"):
                    System.out.println(SPACESHIP_PRESENTATION);
                    for (Spaceship spaceship : o.retrieveBaseEntityList(Spaceship.class)
                    ) {
                        System.out.println(spaceship);
                    } break;
                case ("m"):
                    System.out.println(MISSIONS_PRESENTATION);
                    for (FlightMission flightMission : o.retrieveBaseEntityList(FlightMission.class)
                    ) {
                        System.out.println(flightMission);
                    }
                    break;
            }
            System.out.println("Great. What next?");
            tokens = getUserInput(scanner);
        }
        return null;
        //return ApplicationMenu.super.handleUserInput(o);
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
        prompt();
    }

    private String[] getUserInput(Scanner scanner){
        System.out.println(FIRST_LEVEL_MENU);
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

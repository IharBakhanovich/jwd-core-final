package com.epam.jwd.core_final.InputReaderUtil.Impl;

import com.epam.jwd.core_final.InputReaderUtil.InputReader;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InvalidAmountOfParametersException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.util.IDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.epam.jwd.core_final.domain.Rank.resolveRankById;
import static com.epam.jwd.core_final.domain.Role.resolveRoleById;

public class InputCrewReader implements InputReader<CrewMember> {

    private static final Logger logger = LoggerFactory.getLogger(InputCrewReader.class);

    String pathToFile;
    String splitter1;
    String splitter2;

    public InputCrewReader(String path, String splitter1, String splitter2) {
        this.pathToFile = path;
        this.splitter1 = splitter1;
        this.splitter2 = splitter2;
    }

    @Override
    public Collection<CrewMember> fetchMembers() {
        BufferedReader bufferReader = null;
        String line = "";
        List<CrewMember> crewMembers = new ArrayList<>();
        List<String[]> rawCrewMembers = new ArrayList<>();

        try {
            System.out.println("path to file is: " + pathToFile); /////////////////////////////////////
            bufferReader = new BufferedReader(new FileReader(pathToFile));
            while ((line = bufferReader.readLine()) != null) {
                rawCrewMembers.add(line.split(splitter1));
            }
            System.out.println("CrewMembers array raw (fetchMembers() method"); /////////////////////////
            for (String[] rawCrewMember : rawCrewMembers
            ) {
                System.out.println("Raw crew member is" + rawCrewMember);////////////////////////////////
                System.out.println("what can we see in Array of rawCrewMembers");

                for (String string : rawCrewMember
                ) {
                    System.out.println("the rawCrewMember is " + string);
                }
            }
            try {
                crewMembers = makeArrayOfCrewMembers(rawCrewMembers);
                System.out.println("CrewMembers array after (makeArrayOfCrewMembers() method)"); ///////////////////////
                for (CrewMember crewMember : crewMembers
                ) {
                    System.out.println(crewMember + "The class is: " + crewMember.getClass());
                }

            } catch (InvalidAmountOfParametersException exception) {
                logger.error(exception.getMessage(), new InvalidAmountOfParametersException(exception.getMessage()));
                System.out.println("The program due to unknown format of inputs can not start");
                exception.printStackTrace();
            }
        } catch (IOException exception) {
            System.out.println("The program due to input/output error can not start");
            exception.printStackTrace();
            logger.error(exception.getMessage(), new IOException(exception.getMessage()));
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
        return crewMembers;
    }

    private List<CrewMember> makeArrayOfCrewMembers(List<String[]> rawCrewMembers)
            throws InvalidAmountOfParametersException {

        System.out.println("On the entrance in makeArrayOfCrewMembers() method"); /////////////////////////
        for (String[] rawCrewMember : rawCrewMembers
        ) {
            System.out.println("what can we see in Array of rawCrewMembers");

            for (String string : rawCrewMember
            ) {
                System.out.println("the rawCrewMember is " + string);
            }
        }
        List<CrewMember> crewMembers = new ArrayList<>();
        String[] arrayOfFields = rawCrewMembers.get(0)[0].split(splitter2);

        if (arrayOfFields.length != 3) {
            throw new InvalidAmountOfParametersException("The amount of parameters are wrong");
        }
        // 'for' to delete the # at the beginning og the parameterName
        for (int i = 0; i < arrayOfFields.length; i++) {
            if (arrayOfFields[i].charAt(0)=='#') {
                arrayOfFields[i] = arrayOfFields[i].substring(1);
            }
        }

        for (int i = 0; i < arrayOfFields.length; i++ //////////////////////////
        ) {
            System.out.println("field" + i + " is: " + arrayOfFields[i]);
        }

        System.out.println("BEFORE PARCING RAWCREWMEMBERS.get(1) was"); //////////////////////////////////////

        for (String string : rawCrewMembers.get(1)
        ) {
            System.out.println("the rawCrewMember is " + string);
        }

        for (String rawCrewMember : rawCrewMembers.get(1)
        ) {
            String[] valuesOfParameters = rawCrewMember.split(splitter2);
            System.out.println("AFTER PARCING RAWCREWMEMBER with delimeter " + splitter2); //////////////////////////////////////

            for (String string : valuesOfParameters
            ) {
                System.out.println("the parameter is " + string);
            }

            String name = null;
            Rank rank = null;
            Role role = null;
            for (int j = 0; j < valuesOfParameters.length; j++) {
                switch (arrayOfFields[j]) {
                    case "name":
                        name = valuesOfParameters[j];
                        System.out.println("Name is: " + name);/////////////////////////
                        break;
                    case "role":
                        System.out.println("ID of role is: " + Integer.parseInt(valuesOfParameters[j]));////////////////////
                        role = resolveRoleById(Long.parseLong(valuesOfParameters[j]));
                        System.out.println("Role is: " + role.getName());//////////////////////////////////
                        break;
                    case "rank":
                        System.out.println("ID of rank is: " + Integer.parseInt(valuesOfParameters[j]));///////////////
                        rank = resolveRankById(Long.parseLong(valuesOfParameters[j]));
                        System.out.println("Rank is: " + rank.getName());////////////////////////////////
                        break;
                }
                System.out.println("after SWITCH/CASE block");//////////////////////////////////

            }
            CrewMember crewMember = CrewMemberFactory.INSTANCE.create(IDGenerator.INSTANCE.getId(), name, role, rank);
            System.out.println("CrewMember was created and is: " + "Name is:" + crewMember.getName() + " Role is: " + crewMember.getRole() + " Rank is: " + crewMember.getRank()); //////////////////////////////////
            System.out.println("CrewMember toString: " + crewMember);
            crewMembers.add(crewMember);
        }
        System.out.println("Created ARRAY OF CREWMEMBERS");
        for (CrewMember crewMember: crewMembers
             ) {
            System.out.println("CrewMember is: " + crewMember);
        }
        return crewMembers;
    }
}

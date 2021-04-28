package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */
public final class ApplicationProperties {
    private static ApplicationProperties applicationProperties;
    private final String inputRootDir;
    private final String outputRootDir;
    private final String crewFileName;
    private final String missionsFileName;
    private final String spaceshipsFileName;
    private final Integer fileRefreshRate;
    private final String dateTimeFormat;
    private final String spaceMapFileName;

    private ApplicationProperties() throws FileNotFoundException, IOException {

        PropertyReaderUtil propertyReadUtil = PropertyReaderUtil.getPropertyReaderUtil();
        inputRootDir = propertyReadUtil.getInputRootDir();
        outputRootDir = propertyReadUtil.getOutputRootDir();
        crewFileName = propertyReadUtil.getCrewFileName();
        missionsFileName = propertyReadUtil.getMissionsFileName();
        spaceshipsFileName = propertyReadUtil.getSpaceshipsFileName();
        fileRefreshRate = propertyReadUtil.getfileRefreshRate();
        dateTimeFormat = propertyReadUtil.getDateTimeFormat();
        spaceMapFileName = propertyReadUtil.getSpacemapFileName();
    }

    public static ApplicationProperties getApplicationProperties() throws IOException {
        if (applicationProperties == null) {
            applicationProperties = new ApplicationProperties();
        }
        return applicationProperties;
    }

    public String getInputRootDir() {
        return inputRootDir;
    }

    public String getOutputRootDir() {
        return outputRootDir;
    }

    public String getCrewFileName() {
        return crewFileName;
    }

    public String getMissionsFileName() {
        return missionsFileName;
    }

    public String getSpaceshipsFileName() {
        return spaceshipsFileName;
    }

    public Integer getFileRefreshRate() {
        return fileRefreshRate;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public String getSpaceMapFileName() {
        return spaceMapFileName;
    }
}

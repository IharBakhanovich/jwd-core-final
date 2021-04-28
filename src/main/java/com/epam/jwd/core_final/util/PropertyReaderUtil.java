package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// the Singleton
public final class PropertyReaderUtil {
    private static PropertyReaderUtil propertyReaderUtil;
    private static final Logger logger = LoggerFactory.getLogger(PropertyReaderUtil.class);
    private static final Properties properties = new Properties();

    private PropertyReaderUtil() throws FileNotFoundException, IOException{
        loadProperties();
    }

    public static PropertyReaderUtil getPropertyReaderUtil() throws IOException {
        if (propertyReaderUtil == null) {
            propertyReaderUtil = new PropertyReaderUtil();
        }
        return propertyReaderUtil;
    }

    public static Properties getProperties() {
        return properties;
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public void loadProperties() throws IOException{
        final String propertiesFileName = "src/main/resources/application.properties";

        InputStream inputStream = null;

        try {
            // Loading properties file from the path (propertiesFileName)
            inputStream = new FileInputStream(propertiesFileName);
            properties.load(inputStream);
        } catch (FileNotFoundException exception) {
            logger.error("There is no file 'application.properties' in the 'resources/' location");
            exception.printStackTrace();
            throw new FileNotFoundException();
        } catch (IOException exception) {
            logger.error("An error occurred when reading from the input stream.");
            exception.printStackTrace();
            throw new IOException();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException exception) {
                logger.error("An I/O error occurs during the closing the inputStream.");
                exception.printStackTrace();
                throw new IOException();
            }
        }
    }

    /**
     * Returns the name of inputRootDir folder in resources.
     *
     * @return the name of directory where input files are located.
     */
    public String getInputRootDir() {
        return properties.getProperty("inputRootDir");
    }

    /**
     * Returns the name of outputRootDir folder in resources.
     *
     * @return the name of directory where output files should be written.
     */
    public String getOutputRootDir() {
        return properties.getProperty("outputRootDir");
    }

    /**
     * Returns the name of crewFileName file in resources.
     *
     * @return the name of crewFileName file.
     */
    public String getCrewFileName() {
        return properties.getProperty("crewFileName");
    }

    /**
     * Returns the name of spacemapFileName file in resources.
     *
     * @return the name of spacemapFileName file.
     */
    public String getSpacemapFileName() {
        return properties.getProperty("spacemapFileName");
    }

    /**
     * Returns the name of missionsFileName file in resources. This file is to write missions.
     *
     * @return the name of missionsFileName file.
     */
    public String getMissionsFileName() {
        return properties.getProperty("missionsFileName");
    }

    /**
     * Returns the name of spaceshipsFileName file in resources.
     *
     * @return the name of spaceshipsFileName file.
     */
    public String getSpaceshipsFileName() {
        return properties.getProperty("spaceshipsFileName");
    }

    /**
     * Returns the fileRefreshRate.
     *
     * @return the value of the fileRefreshRate.
     */
    public int getfileRefreshRate() {
        return Integer.parseInt(properties.getProperty("fileRefreshRate"));
    }

    /**
     * Returns the pattern of the dateTimeFormat.
     *
     * @return the pettern of the dateTimeFormat.
     */
    public String getDateTimeFormat() {
        return properties.getProperty("dateTimeFormat");
    }
}

package bootstrap;


import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.EnhancedPatternLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


public class Driver {
    static Properties applicationConfig = new Properties();
    static Logger logger = LoggerFactory.getLogger(Driver.class);

    public static void main(String[] args) {

        initializeProperties(args);
        configureLogging(extractProperty("log.file.path"),
                Boolean.parseBoolean(extractProperty("debug.log")));
        logger.info("Hello Hogwarts");

    }

    public static String configureLogging(String logDirectory, boolean debug) {
        DailyRollingFileAppender dailyRollingFileAppender = new DailyRollingFileAppender();

        String logFileName = "";
        if (!debug) {
            dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.INFO_INT));
            logFileName = logDirectory + "intro-git.log";

        } else {
            dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.DEBUG_INT));
            logFileName = logDirectory + "intro-git.log";
        }

        System.out.println("Log files written out at " + logFileName);
        dailyRollingFileAppender.setFile(logFileName);
        dailyRollingFileAppender.setLayout(new EnhancedPatternLayout("%-6d [%t] %-5p %c - %m%n"));

        dailyRollingFileAppender.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(dailyRollingFileAppender);
        return dailyRollingFileAppender.getFile();
    }

    public static void initializeProperties(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("--")) {
                String configParam = args[i].replaceAll("--", "");
                applicationConfig.put(configParam, args[i + 1]);
            }
        }
    }

    public static String extractProperty(String propertyName) {
        return (String) applicationConfig.get(propertyName);
    }

}

package bad.robot.radiate;

import org.apache.log4j.*;

import java.io.File;

public class Logging {

    public static void initialise() {
        Logger root = Logger.getRootLogger();
        root.addAppender(createFileAppender());
        root.addAppender(createConsoleAppender());
    }

    private static FileAppender createFileAppender() {
        RollingFileAppender file = new RollingFileAppender();
        file.setName("File Appender");
        file.setMaxBackupIndex(1);
        file.setMaxFileSize("2MB");
        file.setFile(System.getProperty("user.home") + File.separator + ".radiate" + File.separator + "radiate.log");
        file.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
        file.setThreshold(Level.INFO);
        file.setAppend(true);
        file.activateOptions();
        return file;
    }

    private static ConsoleAppender createConsoleAppender() {
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(new PatternLayout("%d [%p|%C{1}] %m%n"));
        console.setThreshold(Level.INFO);
        console.activateOptions();
        return console;
    }
}

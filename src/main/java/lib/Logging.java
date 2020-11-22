package lib;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Logging {

    private static Logging instance = null;

    private Logger logger;

    private FileHandler fh;

    private final SimpleDateFormat fileFormatter = new SimpleDateFormat("dd.MM.yyyy");

    private final SimpleDateFormat logFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public static synchronized Logging getInstance() {
        if (instance == null) {
            instance = new Logging();
        }
        return instance;
    }

    private Logging() {
    }

    public Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger(Logging.class.getName());
            logger.setLevel(Level.WARNING);
            addFileHandler();
        }
        return logger;
    }

    private void addFileHandler() {
        try {
            fh = new FileHandler("/prog/DigitalUnicorn/lessons/lessons_logs_" + getCurrnetDate() + ".log");
            fh.setEncoding("UTF-8");
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return "[" + record.getLevel() + "]: "
                            + logFormatter.format(new Date().getTime())
                            + " || "
                            + record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".") + 1)
                            + "."
                            + record.getSourceMethodName()
                            + "() : "
                            + record.getMessage() + "\n";
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(fh);
    }

    private String getCurrnetDate() {
        return fileFormatter.format(new Date());
    }
 }

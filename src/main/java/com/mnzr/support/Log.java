package com.mnzr.support;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Log {

    private static PrintWriter writer;
    private static final Object lock = new Object();
    private static final String LOG_DIR = "target/logs";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    static {
        try {
            File dir = new File(LOG_DIR);
            if (!dir.exists()) dir.mkdirs();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = LOG_DIR + "/TestRun_" + timestamp + ".log";
            writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)), true);
            info("===== LOGGING STARTED: " + timestamp + " =====");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Log() {}

    private static String timestamp() {
        return new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }

    private static String threadTag() {
        return "[Thread-" + Thread.currentThread().getId() + "]";
    }

    private static void log(String level, String message) {
        String formatted = String.format("%s %s [%s] %s", timestamp(), threadTag(), level, message);
        synchronized (lock) {
            System.out.println(formatted);
            if (writer != null) {
                writer.println(formatted);
                writer.flush();
            }
        }
    }

    public static void info(String message) { log("INFO", message); }
    public static void warn(String message) { log("WARN", message); }
    public static void error(String message) { log("ERROR", message); }
    public static void debug(String message) { if (Boolean.parseBoolean(System.getProperty("debug", "false"))) log("DEBUG", message); }

    public static void close() {
        synchronized (lock) {
            if (writer != null) {
                info("===== LOGGING ENDED =====");
                writer.close();
            }
        }
    }
}

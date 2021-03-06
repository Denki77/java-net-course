package ru.daniilazarnov.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Logger {
    private final String log_file_name = "log.txt";
    private String log_dir = "./";

    public Logger(String log_dir) {
        File dir = new File(log_dir);
        if (!dir.isDirectory()) {
            boolean created = dir.mkdir();
            if (!created) {
                return;
            }
        }
        this.log_dir = log_dir;
    }

    public void setLogMessage(String message) throws IOException {

        File file = getMeLogFile();
        FileOutputStream out = new FileOutputStream(file, true);

        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss - ");

        String logger_string = formatForDateNow.format(dateNow) + message + "\n";
        out.write(logger_string.getBytes(StandardCharsets.UTF_8));

    }

    private File getMeLogFile() throws FileNotFoundException {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");

        String full_path_to_file = log_dir + formatForDateNow.format(dateNow) + log_file_name;

        File file = new File(full_path_to_file);
        if (!file.exists()) {
            // If not exist log file - create it
            new FileOutputStream(full_path_to_file);
        }
        return file;
    }

    public void getMeLastMessages() throws IOException {

        File file = getMeLogFile();

        BufferedReader raf = new BufferedReader(new InputStreamReader(new FileInputStream(log_file_name)));
        String line;
        ArrayList<String> linesIntoFile = new ArrayList<>();
        int count_line = 10;

        while ((line = raf.readLine()) != null) {
            linesIntoFile.add(line);
        }

        if (linesIntoFile.size() > count_line) {
            linesIntoFile.subList(0, linesIntoFile.size() - count_line).clear();
        } else {
            count_line = linesIntoFile.size();
        }

        for (String outline :
                linesIntoFile) {
            System.out.println(count_line + ". " + outline);
            count_line--;
        }
    }


}

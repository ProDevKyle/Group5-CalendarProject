import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class BackEnd {
    private Path pathToFile;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Integer> months = new ArrayList<>();
    private ArrayList<Integer> days = new ArrayList<>();
    private String splitter = ",";

    public BackEnd(String path) {
        this.pathToFile = Paths.get(new File(path).getPath());
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            br.readLine();
            String line = br.readLine();
            while (line != null) {
                String[] values = line.split(splitter);
                names.add(values[0].trim());
                months.add(Integer.parseInt(values[1].trim()));
                days.add(Integer.parseInt(values[2].trim()));
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public int size() {
        return names.size();
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<Integer> getMonths() {
        return months;
    }

    public ArrayList<Integer> getDays() {
        return days;
    }

    public void addEvent(String name, int month, int day) {
        names.add(name);
        months.add(month);
        days.add(day);
        String line = "\n" + name + splitter + month + splitter + day;
        try {
            Files.write(pathToFile, line.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
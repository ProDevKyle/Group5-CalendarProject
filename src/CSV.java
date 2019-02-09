import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class CSV {
    private static Path pathToFile = Paths.get(new File("src/events.csv").getPath());
    private static String splitter = ",";

    CSV() {
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            br.readLine();
            String line = br.readLine();
            while (line != null) {
                String[] values = line.split(splitter);
                new Event(
                        new Date(
                                Integer.parseInt(values[1].trim()),
                                Integer.parseInt(values[2].trim())
                        ),
                        values[0].trim()
                );
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    static void saveCSV() {
        try {
            FileWriter fw = new FileWriter(new File(pathToFile.toString()));
            fw.append("name,month,day");
            fw.close();

            for (Event event : Event.getEvents()) {
                String line = "\n" + event.getName() + splitter + event.getDate().getMonth() + splitter + event.getDate().getDay();
                Files.write(pathToFile, line.getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
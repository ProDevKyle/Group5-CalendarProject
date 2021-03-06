import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class NotesCSV {
    private static Path pathToFile = Paths.get(new File("src/notes.csv").getPath());

    NotesCSV() {
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            br.readLine();
            String line = br.readLine();
            while (line != null) {
                new Note(line.trim());
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    static void saveNotesCSV() {
        try {
            FileWriter fw = new FileWriter(new File(pathToFile.toString()));
            fw.append("content");
            fw.close();
            for (Note note : Note.getNotes()) {
                String line = "\n" + note.getContent();
                Files.write(pathToFile, line.getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
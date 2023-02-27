package controller;

import model.Model;
import model.entity.NoteEnglish;
import view.StringConstatnts;
import view.ViewC;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Controller implements StringConstatnts {
    private final Model model;
    private final ViewC view;

    public Controller(Model model, ViewC view) {
        this.model = model;
        this.view = view;
    }

       public void processingSubtitlesWords() throws IOException {
           List<String> listEng = readData(pathEng);
           List<String> lineWithoutTiming = model.getLineWithoutTiming(listEng);
           writerFile(lineWithoutTiming, pathEngCorrect, "");
           List<String> lineWithNewWords =  model.getNewWord(lineWithoutTiming);
           writerFile(lineWithNewWords, pathENGWords, "\n");
           model.writeJDBC(lineWithNewWords);
    }

    private void writerFile(List<String> line, String path, String sign) {
        try (FileWriter writer = new FileWriter(path)) {
            line.stream().forEach(e -> {
                try {
                    writer.write(e + sign);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readData(String path) {
        List<String> lines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            lines = stream.toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}

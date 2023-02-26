import model.Model;
import model.entity.NoteEnglish;

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
        List<String> lineWithoutTiming = getLineWithoutTiming(listEng);

        writerFileTranslated(lineWithoutTiming, pathEngCorrect);

        List<String> listEngCorrect = readData(pathEngCorrect);

        List<String> listWordsCorrect = getWords(model.getListFromBase(),listEngCorrect);

        writerFile(listWordsCorrect, pathENGWords);

        model.writeJDBC(listWordsCorrect);

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

    private List<String> getLineWithoutTiming(List<String> list) {
        return list.stream()
                .filter(e -> !e.isEmpty())
                .filter(e -> !e.matches("[0-9:.->\\W]+"))
                .map(e -> e + " ")
                .toList();

    }

    private void writerFileTranslated(List<String> listLine, String path) {
        try (FileWriter writer = new FileWriter(path)) {
            listLine.stream().forEach(e -> {
                try {
                    writer.write(getNewLine(e));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getNewLine(String e) {
        StringBuilder sb = new StringBuilder(e);
        StringBuilder sb1 = new StringBuilder();
        if (e.contains(".")) {
            int index = e.indexOf(".");
            return sb1.append(sb.substring(0, index + 1)).append("\n").append(sb.substring(index + 1, e.length())).substring(0);
        } else return e;
    }

    private static String addSpace(String e) {
        return e + " ";
    }

    private List<String> getWords(List<NoteEnglish> listNote, List<String> listLine1) {
        Set<String> set = new TreeSet<>();
        for (String s : listLine1) {
            for (String s1 : s.split(" ")) {
                String line = getNewWord(listNote, (removeX(s1))).toLowerCase(Locale.ROOT);
                set.add(line);
            }
        }
        return set.stream().map(x -> x + "\n").toList();
    }

    private String getNewWord(List<NoteEnglish> listNote, String removeX) {
        String finalRemoveX = removeX;
        if (listNote.stream().anyMatch(x -> x.getWord().equalsIgnoreCase(finalRemoveX))) {
            removeX = "@";
        }
        return removeX;
    }

    private static String removeX(String x) {
        System.out.print(x);
        StringBuilder sb = new StringBuilder(x);
        if (sb.indexOf(",") > 0
                || sb.indexOf(".") >= 0
                || sb.indexOf("?") >= 0
                || sb.indexOf("!") >= 0
                || sb.indexOf(":") >= 0
                || sb.indexOf("\"") >= 0
                || sb.indexOf(";") >= 0
                || sb.indexOf(")") >= 0
                || sb.indexOf("(") >= 0) {
            x = getCorrectWord(x);
            System.out.println(" -->                     " + x);
        }
        return x;
    }

    private static String getCorrectWord(String x) {
        StringBuilder sb = new StringBuilder();
        while (x.matches("[\\w]+[\\W]{1,3}")) {
            x = sb.append(x).substring(0, sb.length() - 1);
            sb.delete(0, sb.length());
        }

        while (x.matches("[\\W]{1,3}[\\w]+")) {
            x = sb.append(x).substring(1, sb.length());
            sb.delete(0, sb.length());
        }

        return x;
    }

    static void writerFile(List<String> listLine, String path) {
        try (FileWriter writer = new FileWriter(path)) {
            listLine.stream().forEach(e -> {
                try {
                    writer.write(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

package model;

import model.DAO.DAOFactory;
import model.DAO.NoteEnglishDAO;
import model.entity.NoteEnglish;

import java.util.*;

public class Model {
    DAOFactory factory = DAOFactory.getInstance();
    NoteEnglishDAO dao = factory.createNoteEnglishDAO();

        public List<String> getLineWithoutTiming(List<String> list) {
        return list.stream()
                .filter(e -> !e.isEmpty())
                .filter(e -> !e.matches("[0-9:.->\\W]+"))
                .map(e -> e + " ").map(this::getNewLine)
                .toList();
    }

    private String getNewLine(String e) {
        StringBuilder sb = new StringBuilder(e);
        StringBuilder sb1 = new StringBuilder();
        if (e.contains(".")) {
            int index = e.indexOf(".");
            return sb1.append(sb.substring(0, index + 1)).append("\n").append(sb.substring(index + 1, e.length())).substring(0);
        } else return e;
    }

    public List<String> getNewWord(List<String> line) {
        Set<String> set = new TreeSet<>();
        for (String s : line) {
            for (String s1 : s.split(" ")) {
                String word = getNewWordWithCampare((removeX(s1))).toLowerCase(Locale.ROOT);
                set.add(word);
            }
        }


    return set.stream().toList();}

    private String getNewWordWithCampare(String removeX) {
        String finalRemoveX = removeX;

        if (getListFromBase().stream().anyMatch(x -> x.getWord().equalsIgnoreCase(finalRemoveX))) {
            removeX = "@";
        }
        return removeX;
    }

    private String removeX(String x) {
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

    private String getCorrectWord(String x) {
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

    public void writeJDBC(List<String> list) {
        System.out.println("we are connected!");
        for (String english : list) {
            if (!english.equals("@")) {
                dao.create(english);
            }
        }
    }

    public List<NoteEnglish> getListFromBase() {
        return dao.findAll();
    }
}

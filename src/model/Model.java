package model;

import model.DAO.DAOFactory;
import model.DAO.NoteEnglishDAO;
import model.entity.NoteEnglish;
import java.util.List;

public class Model {
    DAOFactory factory = DAOFactory.getInstance();
    NoteEnglishDAO dao = factory.createNoteEnglishDAO();

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

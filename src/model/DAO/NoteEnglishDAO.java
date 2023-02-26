package model.DAO;

import model.entity.NoteEnglish;

import java.util.List;

public interface NoteEnglishDAO {
    void create(String noteEnglish);
    NoteEnglish findByID(int id);
    NoteEnglish findByWord(String word);
    List<NoteEnglish>findAll();
    void update(NoteEnglish noteEnglish);
    void delete(int id);
}

package model.DAO.impl;

import model.DAO.NoteEnglishDAO;
import model.entity.NoteEnglish;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCNoteEnglishDAO implements NoteEnglishDAO {
    private final Connection connection;


    public JDBCNoteEnglishDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(String noteEnglish) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO my_db.noteenglish(word) VALUES(?)"
        )) {
            ps.setString(1, noteEnglish);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NoteEnglish findByID(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT  * FROM my_db.noteenglish WHERE id =(?);"
        )) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public NoteEnglish findByWord(String word) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM my_db.noteenglish WHERE word like '?';"
        )) {
            ps.setString(1, word);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private NoteEnglish extractFromResultSet(ResultSet rs) throws SQLException {
        NoteEnglish result = new NoteEnglish();
        //result.setId(rs.getInt("id"));
        result.setWord(rs.getString("word"));
        return result;
    }

    @Override
    public List<NoteEnglish> findAll() {
        List<NoteEnglish> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT DISTINCT word FROM my_db.noteenglish;"
        )) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NoteEnglish note = extractFromResultSet(rs);
                list.add(note);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void update(NoteEnglish noteEnglish) {

    }

    @Override
    public void delete(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM noteenglish WHERE id = ?;"
        )) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

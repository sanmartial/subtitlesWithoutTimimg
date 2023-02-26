package model.DAO.impl;

import model.DAO.DAOFactory;
import model.DAO.NoteEnglishDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDAOFactory extends DAOFactory {
    @Override
    public NoteEnglishDAO createNoteEnglishDAO() {


        return new JDBCNoteEnglishDAO(getConnections());
    }

    private Connection getConnections() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/my_db",
                    "bestuser",
                    "bestuser"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

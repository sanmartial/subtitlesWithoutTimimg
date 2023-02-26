package model.DAO;

import model.DAO.impl.JDBCDAOFactory;

public abstract class DAOFactory {
    private static DAOFactory daoFactory;
    public abstract NoteEnglishDAO createNoteEnglishDAO();
    public static DAOFactory getInstance(){
        if(daoFactory == null){
            synchronized (DAOFactory.class){
                if(daoFactory == null){
                    daoFactory = new JDBCDAOFactory();
                }
            }
        }
    return daoFactory;}
}

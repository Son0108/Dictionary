package main.Dictionary.Repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EnglishRepository {
    public void Update(int englishId, String word,int status, String transcribe){
        Connection conn;
        try {
            conn = DriverManager.getConnection(DatabaseRepository.dbURL,DatabaseRepository.user,DatabaseRepository.pass);
            Statement st1 = conn.createStatement();
            st1.executeUpdate("exec spUpdateEnglish " + englishId + ", N'" + word + "', '', " + status + ", N'"+ transcribe + "'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}

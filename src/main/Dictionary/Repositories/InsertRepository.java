package main.Dictionary.Repositories;
import java.sql.*;

public class InsertRepository {
    public void Insert(String englishWord, String vietnameseWord,String transcribe, int WordTypeId){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection( DatabaseRepository.dbURL, DatabaseRepository.user, DatabaseRepository.pass);
            if(conn != null){
                Statement st1 = conn.createStatement();
                ResultSet rs1 = st1.executeQuery("exec spInsertEnglish '" + englishWord + "', '', N'" + transcribe + "', 1");
                rs1.next();
                int englistId = rs1.getInt("Id");
                System.out.println(englistId);
                System.out.println(englistId);
                Statement st2 = conn.createStatement();
                ResultSet rs2 = st1.executeQuery("exec spInsertVietnamese '" + englistId + "', " + WordTypeId + ", N'" + vietnameseWord + "', '', 1");
                rs2.next();
                st1.close();
                st2.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }
}

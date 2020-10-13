package main.Dictionary.Repositories;
import main.Dictionary.Models.WordTypeModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WordTypeRepository {
    public List<WordTypeModel> Get()
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection( DatabaseRepository.dbURL, DatabaseRepository.user, DatabaseRepository.pass);
            if(conn != null){
                Statement st1 = conn.createStatement();
                ResultSet rs1 = st1.executeQuery("exec spGetWordTypes");
                List<WordTypeModel> list_wordType = new ArrayList<WordTypeModel>();
                while(rs1.next()){
                    WordTypeModel wordType = new WordTypeModel();
                    wordType.WordTypeId = rs1.getInt("WordTypeId");
                    wordType.Name = rs1.getString("Name");
                    wordType.ShortName = rs1.getString("ShortName");
                    list_wordType.add(wordType);
                }
                st1.close();
                return list_wordType;
            }
            return new ArrayList<WordTypeModel>();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new ArrayList<WordTypeModel>();
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

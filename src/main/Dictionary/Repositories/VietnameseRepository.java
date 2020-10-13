package main.Dictionary.Repositories;

import main.Dictionary.Models.VietnameseModel;
import main.Dictionary.Models.WordTypeModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VietnameseRepository {
    public List<VietnameseModel> Get(int englistId)
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection( DatabaseRepository.dbURL, DatabaseRepository.user, DatabaseRepository.pass);
            if(conn != null){
                Statement st1 = conn.createStatement();
                ResultSet rs1 = st1.executeQuery("exec spGetVietnamese " + englistId);
                List<VietnameseModel> list_vietnamese = new ArrayList<VietnameseModel>();
                while(rs1.next()){
                    VietnameseModel vietnamese = new VietnameseModel();
                    vietnamese.WordTypeId = rs1.getInt("WordTypeId");
                    vietnamese.Name = rs1.getString("Name");
                    vietnamese.ShortName = rs1.getString("ShortName");
                    vietnamese.Word = rs1.getNString("Word");
                    vietnamese.VietnameseId = rs1.getInt("VietnameseId");
                    list_vietnamese.add(vietnamese);
                }
                st1.close();
                return list_vietnamese;
            }
            return new ArrayList<VietnameseModel>();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new ArrayList<VietnameseModel>();
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

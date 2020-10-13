package main.Dictionary.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import main.Dictionary.Models.SearchModel;
import main.Dictionary.Models.VietnameseModel;
import main.Dictionary.Repositories.DatabaseRepository;
import main.Dictionary.Repositories.VietnameseRepository;
import org.controlsfx.control.textfield.TextFields;

import java.beans.EventHandler;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    private TextField tf_local_search;
    @FXML
    private TableColumn<SearchModel,String> WordType;
    @FXML
    private TableColumn<SearchModel,String> Transcribe;
    @FXML
    private TableColumn<SearchModel,Button> Speech;
    @FXML
    private TableColumn<SearchModel,String> VietnameseWord;
    @FXML
    private TableColumn<SearchModel, Button> Edit;
    @FXML
    private TableColumn<SearchModel,Button> Remove;

    @FXML
    private TableView<SearchModel> table_view;

    private Connection conn;
    private Statement statement;
    private List<String> posibleWords = new ArrayList<>();

    private ObservableList<SearchModel> searchModels = FXCollections.observableArrayList();

    @FXML
    public void pressKey(KeyEvent e){
        try {
            posibleWords.clear();
            ResultSet st1 = statement.executeQuery("exec spGetSearches " + tf_local_search.getText());
            int englishId = 0;
            String transcribe = "";
            while(st1.next()){
                posibleWords.add(st1.getString("Word"));
                englishId = st1.getInt("EnglishId");
                transcribe = st1.getNString("Transcribe");
            }
            if(e.getCode().getName() == "Enter"){

                VietnameseRepository vietnameseRepository = new VietnameseRepository();
                List<VietnameseModel> vietnameseModels = vietnameseRepository.Get(englishId);
                searchModels.clear();
                for(VietnameseModel v:vietnameseModels) {
                    searchModels.add(new SearchModel(v.Word,transcribe,v.Name));
                }
                System.out.println(searchModels.size());
                this.table_view.setItems(searchModels);

            }
            TextFields.bindAutoCompletion(tf_local_search,posibleWords);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WordType.setCellValueFactory(new PropertyValueFactory<>("WordType"));
        Transcribe.setCellValueFactory(new PropertyValueFactory<>("Transcribe"));
        Speech.setCellValueFactory(new PropertyValueFactory<>("Speech"));
        Edit.setCellValueFactory(new PropertyValueFactory<>("Edit"));
        Remove.setCellValueFactory(new PropertyValueFactory<>("Remove"));
        VietnameseWord.setCellValueFactory(new PropertyValueFactory<>("VietnameseWord"));
        table_view.setItems(searchModels);
        try {
            conn = DriverManager.getConnection(DatabaseRepository.dbURL,DatabaseRepository.user,DatabaseRepository.pass);
            statement = conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

package main.Dictionary.Controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import main.Dictionary.Models.SearchModel;
import main.Dictionary.Models.VietnameseModel;
import main.Dictionary.Repositories.DatabaseRepository;
import main.Dictionary.Repositories.EnglishRepository;
import main.Dictionary.Repositories.VietnameseRepository;
import main.Dictionary.Services.SpeechService;
import org.controlsfx.control.textfield.TextFields;


import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private AnchorPane root;
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

    @FXML
    private Button speechButton;
    @FXML
    private Button editButton;
    @FXML
    private Button removeButton;

    private int englishId;
    private String transcribe = "";
    private Connection conn;
    private Statement statement;
    private String currentEnglishWord = "";
    private int AlphabeticalGroupId;

    private List<String> posibleWords = new ArrayList<>();

    private ObservableList<SearchModel> searchModels = FXCollections.observableArrayList();

    @FXML
    public void pressKey(KeyEvent e){
        try {
            ResultSet st1 = statement.executeQuery("exec spGetSearches '" + tf_local_search.getText() + "'");
            posibleWords.clear();
            editButton.setDisable(true);
            removeButton.setDisable(true);

            while(st1.next()){
                currentEnglishWord = st1.getString("Word");
                posibleWords.add(currentEnglishWord);
                englishId = st1.getInt("EnglishId");
                transcribe = st1.getNString("Transcribe");
                AlphabeticalGroupId = st1.getInt("AlphabeticalGroupId");
            }

            if(e.getCode().getName() == "Enter"){
                VietnameseRepository vietnameseRepository = new VietnameseRepository();
                System.out.println(AlphabeticalGroupId);
                List<VietnameseModel> vietnameseModels = vietnameseRepository.Get(AlphabeticalGroupId);
                searchModels.clear();
                for (VietnameseModel v : vietnameseModels) {
                    englishId = v.EnglishId;
                    searchModels.add(new SearchModel(v.VietnameseId, v.Word, transcribe, v.Name, searchModels, searchModels.size(), v.WordTypeId));
                }

                this.table_view.setItems(searchModels);
                if(searchModels.size() == 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("This word is not found!!!");
                    alert.setTitle("Error alert");
                    alert.setHeaderText("");
                    alert.show();
                }
                else{
                    editButton.setDisable(false);
                    removeButton.setDisable(false);
                }
            }
            TextFields.bindAutoCompletion(tf_local_search,posibleWords);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table_view.setEditable(true);
        Image speechImage = new Image(getClass().getResourceAsStream("../Statics/speech.png"));
        ImageView speechImageView = new ImageView(speechImage);
        speechButton.setGraphic(speechImageView);
        Image editImage = new Image(getClass().getResourceAsStream("../Statics/edit.png"));
        ImageView editImageView = new ImageView(editImage);

        editButton.setGraphic(editImageView);
        editButton.setDisable(true);

        Image removeImage = new Image(getClass().getResourceAsStream("../Statics/delete.png"));
        ImageView removeImageView = new ImageView(removeImage);

        removeButton.setGraphic(removeImageView);
        removeButton.setDisable(true);

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
    public void speechText(){

        SpeechService.TalkResource(tf_local_search.getText());
    }

    public void removeEnglish(){
        EnglishRepository englishRepository = new EnglishRepository();
        englishRepository.Update(englishId,tf_local_search.getText(),0,"");
        searchModels.clear();

    }
    public void editEnglish(){
        Dialog<Pair<String,String>> dialog = new Dialog<>();
        dialog.setTitle("Edit English dialog");
        dialog.setHeaderText("");
        ButtonType editButtonType = new ButtonType("Save",ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(editButtonType,ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20,150,10,10));
        TextField newEnglishWord = new TextField();
        newEnglishWord.setText(tf_local_search.getText());
        TextField newTranscribe = new TextField();
        newTranscribe.setText(transcribe);
        gridPane.add(new Label("English Word"),0,0);
        gridPane.add(newEnglishWord,1,0);
        gridPane.add(new Label("Transcribe"),0,1);
        gridPane.add(newTranscribe,1,1);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter(dialogButton->{
            if(dialogButton == editButtonType){
                return new Pair<>(newEnglishWord.getText(),newTranscribe.getText());
            }
            return null;
        });
        Optional<Pair<String,String>> result = dialog.showAndWait();
        result.ifPresent(data->{
            for(int i=0;i<searchModels.size();i++){
                searchModels.get(i).setTranscribe(data.getValue());
                searchModels.set(i,searchModels.get(i));
            }
            transcribe = data.getValue();
            System.out.println(englishId);
            EnglishRepository englishRepository = new EnglishRepository();
            englishRepository.Update(englishId,data.getKey(),1,data.getValue());
        });

    }

}

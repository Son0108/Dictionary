package main.Dictionary.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.Dictionary.Models.WordTypeModel;
import main.Dictionary.Repositories.InsertRepository;
import main.Dictionary.Repositories.WordTypeRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InsertController implements Initializable {

    @FXML
    private TableView<InsertController> table;

    @FXML
    private TableColumn<InsertController, String> tbl_English;

    @FXML
    private TableColumn<InsertController, String> tbl_Vietnamese;

    @FXML
    private TableColumn<InsertController, String> tbl_Word;

    @FXML
    private TextField tf_insertE;

    @FXML
    private TextField tf_insertV;

    @FXML
    private ChoiceBox<String> txt_Word;

    @FXML
    private TextField tf_transcribe;

    private List<WordTypeModel> list_WordType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WordTypeRepository wordTypeRepository = new WordTypeRepository();
        this.list_WordType = wordTypeRepository.Get();
        for(WordTypeModel w : list_WordType){
            txt_Word.getItems().add(w.Name);
        }
    }

    public void Insert(javafx.event.ActionEvent actionEvent) {

        if (!tf_insertE.getText().trim().isEmpty() && !tf_insertV.getText().trim().isEmpty() && !txt_Word.getValue().trim().isEmpty() && !tf_transcribe.getText().trim().isEmpty()){
            int wordTypeId = 0;
            for(WordTypeModel w : list_WordType){
                if(w.Name == txt_Word.getValue()){
                    wordTypeId = w.WordTypeId;
                    break;
                }
            }
            InsertRepository insertRepository = new InsertRepository();
            insertRepository.Insert(tf_insertE.getText().trim(),tf_insertV.getText().trim(),tf_transcribe.getText(),wordTypeId);
        }
    }
}

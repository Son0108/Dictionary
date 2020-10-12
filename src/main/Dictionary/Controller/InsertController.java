package main.Dictionary.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txt_Word.getItems().addAll("danh từ", "ngoại động từ", "nội động từ", "tính từ", "phó từ", "trạng từ");
    }

    public void Insert(javafx.event.ActionEvent actionEvent) {

    }
}

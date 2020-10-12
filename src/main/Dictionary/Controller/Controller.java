package main.Dictionary.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    @FXML
    private BorderPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    private void loadButton(String nameButton) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(nameButton));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.setCenter(root);
    }

    public void handleButtonActionHome(MouseEvent mouseEvent) { loadButton("/main/Dictionary/Fxml/home.fxml"); }

    public void handleButtonActionInsert(MouseEvent mouseEvent) {
        loadButton("/main/Dictionary/Fxml/insert.fxml");
    }

    public void handleButtonActionSearch(MouseEvent mouseEvent) {
        loadButton("/main/Dictionary/Fxml/search.fxml");
    }

}

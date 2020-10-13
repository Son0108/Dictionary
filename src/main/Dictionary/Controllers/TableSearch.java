package main.Dictionary.Controllers;

import javafx.beans.property.SimpleStringProperty;

import java.awt.*;

public class TableSearch {
    private SimpleStringProperty Enghlish;
    private SimpleStringProperty Vietnamese;
    private SimpleStringProperty Word;
    private Button button;

    void TabableView(String english, String vietnamese, String word) {
        this.Enghlish = new SimpleStringProperty(english);
        this.Vietnamese = new SimpleStringProperty(vietnamese);
        this.Word = new SimpleStringProperty(word);
        this.button = new Button("Speech");
    }

    public void setEnghlish(String enghlish) {
        this.Enghlish.set(enghlish);
    }

    public void setVietnamese(String vietnamese) {
        this.Vietnamese.set(vietnamese);
    }

    public void setWord(String word) {
        this.Word.set(word);
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getEnghlish() {
        return Enghlish.get();
    }

    public String getVietnamese() {
        return Vietnamese.get();
    }

    public String getWord() {
        return Word.get();
    }

    public Button getButton() {
        return button;
    }
}

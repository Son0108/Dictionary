package main.Dictionary.Models;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class SearchModel {
    private String VietnameseWord;
    private String Transcribe;
    private String WordType;
    private Button Speech = new Button("Speech");
    private Button Edit = new Button("Edit");
    private Button Remove = new Button("Remove");
    public SearchModel(String word, String transcribe, String wordType){
        System.out.println(word);
        this.VietnameseWord = word;
        this.Transcribe = transcribe;
        this.WordType = wordType;
    }

    public Button getEdit() {
        return Edit;
    }

    public void setEdit(Button edit) {
        Edit = edit;
    }

    public Button getRemove() {
        return Remove;
    }

    public void setRemove(Button remove) {
        Remove = remove;
    }

    public Button getSpeech() {
        return Speech;
    }

    public void setSpeech(Button speech) {
        Speech = speech;
    }

    public String getTranscribe() {
        return Transcribe;
    }

    public void setTranscribe(String transcribe) {
        this.Transcribe = transcribe;
    }

    public String getVietnameseWord() {
        return this.VietnameseWord;
    }

    public void setVietnameseWord(String vietnameseWord) {
        this.VietnameseWord = vietnameseWord;
    }

    public String getWordType() {
        return this.WordType;
    }

    public void setWordType(String wordType) {
        this.WordType = wordType;
    }
}

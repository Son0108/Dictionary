package main.Dictionary.Models;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class SearchModel {
    private String VietnameseWord;
    private String Transcribe;
    private String WordType;
    private Button Speech = new Button();
    private Button Edit = new Button();
    private Button Remove = new Button();
    public SearchModel(String word, String transcribe, String wordType){
        Button speechButton = new Button();
        Button editButton = new Button();
        Button removeButton = new Button();

        Text iconspeech = MaterialDesignIconFactory.get().createIcon(MaterialDesignIcon.VOLUME_HIGH);
        Text iconEdit = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.EDIT);
        Text iconRemove = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.REMOVE);
        speechButton.setGraphic(iconspeech);
        editButton.setGraphic(iconEdit);
        removeButton.setGraphic(iconRemove);

        this.setRemove(removeButton);
        this.setEdit(editButton);
        this.setSpeech(speechButton);
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

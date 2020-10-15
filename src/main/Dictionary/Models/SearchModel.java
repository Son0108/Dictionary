package main.Dictionary.Models;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Pair;
import main.Dictionary.Repositories.VietnameseRepository;
import main.Dictionary.Repositories.WordTypeRepository;
import main.Dictionary.Services.SpeechService;
import org.controlsfx.control.textfield.TextFields;

import java.util.List;
import java.util.Optional;

public class SearchModel implements EventHandler<ActionEvent> {
    private int VietnameseId;
    private String VietnameseWord;
    private String Transcribe;
    private int WordTypeId;
    private String WordType;
    private Button Speech = new Button();
    private Button Edit = new Button();
    private Button Remove = new Button();
    public SearchModel(int vietnameseId,String word, String transcribe, String wordType, ObservableList<SearchModel> searchModels, int index,int wordTypeId){
        this.VietnameseId = vietnameseId;
        Button speechButton = new Button();
        Button editButton = new Button();
        Button removeButton = new Button();

        Image speechImage = new Image(getClass().getResourceAsStream("../Statics/speech.jpg"));
        ImageView speechImageView = new ImageView(speechImage);

        speechButton.setGraphic(speechImageView);
        speechButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SpeechService.TalkResource(VietnameseWord);
            }
        });

        Image editImage = new Image(getClass().getResourceAsStream("../Statics/edit.png"));
        ImageView editImageView = new ImageView(editImage);

        editButton.setGraphic(editImageView);

        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WordTypeRepository wordTypeRepository = new WordTypeRepository();
                List<WordTypeModel> list_WordType = wordTypeRepository.Get();
                ChoiceBox<String> choiceBox_workTypes = new ChoiceBox<>();
                for(WordTypeModel w : list_WordType){
                    choiceBox_workTypes.getItems().add(w.Name);
                }
                choiceBox_workTypes.setValue(WordType);
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Edit Dialog");
                dialog.setHeaderText("");
                ButtonType editButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

                GridPane gridPane = new GridPane();
                gridPane.setVgap(10);
                gridPane.setHgap(10);
                gridPane.setPadding(new Insets(20,150,10,10));
                TextField newVietnameseWord = new TextField(getVietnameseWord());
                newVietnameseWord.setPromptText("Vietnamese Word");

                gridPane.add(new Label("Vietnamese Word"),0,0);
                gridPane.add(newVietnameseWord,1,0);
                gridPane.add(new Label("Type"),0,2);
                gridPane.add(choiceBox_workTypes,1,2);
                dialog.getDialogPane().setContent(gridPane);
                dialog.setResultConverter(dialogButton -> {
                    if(dialogButton == editButtonType){
                        return new Pair<>(newVietnameseWord.getText().trim(),choiceBox_workTypes.getValue());
                    }
                    return null;
                });
                Optional<Pair<String, String>> result = dialog.showAndWait();
                result.ifPresent(data->{
                    int newWordTypeId = 0;
                    for(WordTypeModel w: list_WordType){
                        if(w.Name == data.getValue()){
                            newWordTypeId = w.WordTypeId;
                        }
                    }
                    VietnameseRepository vietnameseRepository = new VietnameseRepository();
                    vietnameseRepository.Update(VietnameseId,data.getKey().trim(),1,newWordTypeId);
                    searchModels.set(index,new SearchModel(VietnameseId,data.getKey().trim(),transcribe,data.getValue().trim(),searchModels,index,newWordTypeId));
                });
            }
        });

        Image removeImage = new Image(getClass().getResourceAsStream("../Statics/delete.png"));
        ImageView removeImageView = new ImageView(removeImage);

        removeButton.setGraphic(removeImageView);
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VietnameseRepository vietnameseRepository = new VietnameseRepository();
                vietnameseRepository.Update(vietnameseId,getVietnameseWord(),0,wordTypeId);
                searchModels.remove(index);
            }
        });

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

    @Override
    public void handle(ActionEvent event) {

    }
}

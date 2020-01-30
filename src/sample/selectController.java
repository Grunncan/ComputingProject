package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.VLineTo;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.util.*;

public class selectController {

    @FXML
    private ListView listview;

    @FXML
    private static VBox vBox;

    @FXML
    private ChoiceBox choiceBox;

    private String previousItem;

    private List<String> difficultyDESC;
    private List<String> difficultyASC;
    private List<String> dateDESC;
    private List<String> dateASC;

    @FXML
    private Label diffLabel;
    @FXML
    private Label dateLabel;

    @FXML
    private HBox hbox1;
    @FXML
    private HBox hbox2;
    @FXML
    private HBox hbox3;
    @FXML
    private HBox hbox4;
    @FXML
    private HBox hbox5;
    @FXML
    private HBox hbox6;
    @FXML
    private HBox hbox7;
    @FXML
    private HBox hbox8;
    @FXML
    private HBox hbox9;

    private ArrayList<Button> buttons = new ArrayList<>();


    private static HBox[] hBoxes;
   // private static SudokuList sudokuList;

    @FXML
    private Rectangle rectangleSelect;
    @FXML
    private Rectangle rectangleGenerate;


    public void initialize() throws IOException {

        hBoxes = new HBox[]{hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7, hbox8, hbox9};

        SudokuList slist = SudokuList.getInstance();


        listview.getItems().setAll(Arrays.asList(slist.getNames()));
        listview.getSelectionModel().selectFirst();
        listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listview.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            if(t1 !=  null){
                String val = (String) listview.getSelectionModel().getSelectedItem();
                List<String> names= slist.getNames();

                for(int i=0; i< slist.size(); i++){
                    if(val.equals(names.get(i))){
                        updateSudoku((Sudoku) slist.get(i));
                    }
                }
            }
        });



        difficultyASC = getNamesList(slist.sortDifficulty(true));
        difficultyDESC = getNamesList(slist.sortDifficulty());
        dateASC = getNamesList(slist.sortDate(true));
        dateDESC = getNamesList(slist.sortDate());


        choiceBox.getSelectionModel().selectFirst();
        previousItem = (String) choiceBox.getSelectionModel().getSelectedItem();

        setInitView(((Sudoku) slist.get(0)));


    }

    private List<String> getNamesList(Object[] obs){
        List<String> list = new ArrayList<>();
        for(int i=0; i< obs.length; i++){
            list.add(((Sudoku) obs[i]).getName());
        }
        return list;
    }


    private void setInitView(Sudoku s){
        int[][] board = s.getBoard();
        for(int j=0;j <9; j++){
            for(int i=0; i<9;i++){
                Button b = new Button(Integer.toString(board[j][i]));
                b.setOnAction(event -> {
                    Button button = (Button) event.getSource();
                    int value = Integer.parseInt(button.getText());
                    if(value == 9){
                        button.setText("0");
                    }else{
                        button.setText(Integer.toString(value +1));
                    }
                });
                b.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 0.5;");
                b.setId(Integer.toString(i+j));
                hBoxes[j].getChildren().add(b);

                buttons.add(b);
            }
        }
        diffLabel.setText("Difficulty: " + s.getDifficulty());
        dateLabel.setText("Date: " + s.getDate().toString());
    }



    @FXML
    public void onOptionButtonClick(ActionEvent event){

        generateController.changeScene(event);
    }


    @FXML
    public void onPrintButtonCLick(ActionEvent event){
        final Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(Main.getStage());
        stage.setResizable(false);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("This is a Dialog"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        stage.setScene(dialogScene);
        stage.show();

    }




    public void updateSudoku(Sudoku s){

        for(int i=0; i< buttons.size(); i++){
            buttons.get(i).setText(Integer.toString(s.getFlattenedBoard().get(i)));
        }
        diffLabel.setText("Difficulty: " + s.getDifficulty());
        dateLabel.setText("Date: " + s.getDate().toString());

    }


    @FXML
    public void onChoiceBoxUpdate(ActionEvent event){
        ChoiceBox cb = (ChoiceBox) event.getSource();
        String item = (String) cb.getSelectionModel().getSelectedItem();
        if(item.equals(previousItem)){
            return;
        }else if(item.equals("Date DESC")){
            listview.getItems().setAll(dateDESC);
        }else if(item.equals("Date ASC")){
            listview.getItems().setAll(dateASC);
        }else if(item.equals("Difficulty DESC")){
            listview.getItems().setAll(difficultyDESC);
        }else if(item.equals("Difficulty ASC")){
            listview.getItems().setAll(difficultyASC);
        }
        previousItem = item;

    }



}

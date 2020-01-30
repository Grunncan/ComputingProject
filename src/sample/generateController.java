package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class generateController {

    @FXML
    private static Rectangle rectangleSelect;
    @FXML
    private static Rectangle rectangleGenerate;

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


    public void initialize()  {
        hBoxes = new HBox[]{hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7, hbox8, hbox9};
        setInitView();
    }




    @FXML
    public void onOptionButtonClick(ActionEvent event){
        changeScene(event);

    }

    public static void changeScene(ActionEvent event){
        if(((Button) event.getSource()).getId().equals("buttonGenerate")){
            rectangleGenerate.setFill(Color.BLACK);
            rectangleSelect.setFill(Color.WHITE);
            Main.getStage().setScene(Main.getGenerateScene());
        }else{
            rectangleGenerate.setFill(Color.WHITE);
            rectangleSelect.setFill(Color.BLACK);
            Main.getStage().setScene(Main.getSelectScene());


        }
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


    private void setInitView(){

        for(int j=0;j <9; j++){
            for(int i=0; i<9;i++){
                Button b = new Button("0");
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

    }

}

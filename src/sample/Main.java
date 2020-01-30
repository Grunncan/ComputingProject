package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;



public class Main extends Application {
    //Inheriting from javafx.application.Application abstract class
    //Documentation https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html



    private static Scene selectScene;
    private static Scene generateScene;
    private static Stage stage;

    @Override
    public void start( Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent selectRoot = FXMLLoader.load(getClass().getResource("fxml/selectRoot.fxml"));
        selectScene = new Scene(selectRoot, 800, 500);

        Parent generateRoot = FXMLLoader.load(getClass().getResource("fxml/generateRoot.fxml"));
        generateScene = new Scene(generateRoot, 800, 500);


        stage.setTitle("Sudoku Generator");
        stage.getIcons().add(new Image("https://is4-ssl.mzstatic.com/image/thumb/Purple123/v4/4f/10/af/4f10afa2-7421-3fdd-e625-dbea1789b4f2/source/512x512bb.jpg"));
        stage.setScene(selectScene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() throws Exception{
        //Database.getInstance().addSudoku();
        super.stop();
    }

    public static Stage getStage(){
        return stage;
    }

    public static Scene getSelectScene(){
        return selectScene;
    }

    public static Scene getGenerateScene(){
        return generateScene;
    }



    public static void main(String[] args) throws IOException{


        int[][] board = {
                { 0, 0, 5, 0, 0, 2, 0, 0, 0 },
                { 0, 7, 6, 6, 0, 0, 0, 0, 0 },
                { 0, 9, 0, 7, 9, 0, 0, 0, 0 },
                { 0, 2, 0, 0, 0, 4, 0, 0, 5 },
                { 0, 0, 0, 3, 0, 0, 4, 0, 0 },
                { 3, 0, 0, 1, 0, 1, 0, 0, 4 },
                { 0, 0, 1, 0, 0, 0, 2, 6, 8 },
                { 0, 8, 8, 5, 0, 0, 3, 0, 9 },
                { 0, 2, 3, 0, 0, 0, 4, 0, 0 }};


        //Database db = Database.getInstance();


//        Sudoku sudoku = new Sudoku(6, board, new Date(), "TestSudoku3");
//        Database.getInstance().addSudoku(sudoku);
//        Sudoku sudoku = new Sudoku(1, board, new Date(), "");
//        list = sudoku.getFlattenedBoard();


        launch(args);


    }






}

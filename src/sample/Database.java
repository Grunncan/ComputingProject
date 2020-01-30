package sample;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Represents a database
 * final keyword showing that it will not be inherited by other classes
 *
 */

public final class Database {

    /**
     * Defining connection in Database scope
     */
    private Connection connection = null;

    /**
     * Defining statement in Database scope
     */
    private Statement statement;


    private static Database db = null;

    /**
     * Constructs the connection to the database loads data from config file
     *
     *
     */


    private Database(){

        try{
            Properties prop = new Properties();
            InputStream input = new FileInputStream("../config.properties");
            prop.load(input);


            connection = DriverManager.getConnection(prop.getProperty("db.url"), prop.getProperty("db.user"), prop.getProperty("db.password"));
            System.out.println("Connection successful");

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Returns the instance of the database if there is one
     * if not will create a new one and return
     *
     *
     * @return {@code sample.Database}
     */

    public static Database getInstance(){
        if(db ==null){

            db = new Database();
        }

        return db;
    }

    /**
     * Adds a sudoku to the database
     *
     * @param sudoku the sudoku object to be added to to the database
     * @return {@code true} if successfully added a sudoku to the database,
     * {@code false} if failed to add suokdu to database
     */
    public boolean addSudoku(Sudoku sudoku){
        if(connection ==null) return false;
        try{
            statement = connection.createStatement();
            String sql = String.format("INSERT INTO sudokutable (name, difficulty, date, row1, row2, row3, row4, row5, row6, row7, row8, row9)" +
                            " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    sudoku.getName(),
                    Integer.toString(sudoku.getDifficulty()),
                    sudoku.getDate().toString(),
                    Arrays.toString(sudoku.getBoard()[0]),
                    Arrays.toString(sudoku.getBoard()[1]),
                    Arrays.toString(sudoku.getBoard()[2]),
                    Arrays.toString(sudoku.getBoard()[3]),
                    Arrays.toString(sudoku.getBoard()[4]),
                    Arrays.toString(sudoku.getBoard()[5]),
                    Arrays.toString(sudoku.getBoard()[6]),
                    Arrays.toString(sudoku.getBoard()[7]),
                    Arrays.toString(sudoku.getBoard()[8]));


            statement.execute(sql);
            System.out.println("Executed sql");
            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }




    }

    /**
     * Returns a {@code Collection} of Sudoku instances from the database
     *
     * @return {@code Collection} of Sudoku instances
     *
     */

    public Collection<Sudoku> getSudokus(){
        if(connection ==null) return null;

        Collection<Sudoku> sList = new ArrayList<>();

        try{
            statement = connection.createStatement();
            String sql = "SELECT * FROM sudokutable;";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){

                int[][] board = new int[9][];

                for(int i=0; i<9; i++){
                    int[] row = new int[9];

                    String[] rsString = rs.getString("row" + (i+1)).replace("[", "").
                            replace("]", "").
                            replaceAll("\\s", "").
                            split(",");

                    for(int j=0; j<9; j++){
                        row[j] = Integer.parseInt(rsString[j]);
                    }
                    board[i] = row;

                }

                String name = rs.getString("name");
                int difficulty = Integer.parseInt(rs.getString("difficulty"));
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("date"));

                Sudoku s = new Sudoku(difficulty, board, date, name);
                sList.add(s);
            }



            return sList;


        }catch(SQLException | ParseException e){
            e.printStackTrace();
            return null;

        }
    }




}

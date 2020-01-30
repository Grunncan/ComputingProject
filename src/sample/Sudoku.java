package sample;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class Sudoku {

    private final int DIFFICULTY;
    private final int[][] BOARD;
    private final java.sql.Date DATE;
    private String name;
    private SudokuGenerator ss;

    public Sudoku(int difficulty, int[][] board, Date date, String name){
        this.DIFFICULTY = difficulty;
        this.BOARD = board;
        this.DATE = new java.sql.Date(date.getTime());
        this.name = name;

    }

    public Sudoku(){
        this.DIFFICULTY = ((int) (Math.random() *4)) +1;
        this.BOARD = new int[9][];
        this.DATE = new java.sql.Date(new Date().getTime());
        this.name = "";
    }

    public void solveBoard(){
        ss = new SudokuGenerator(BOARD);
        ss.solve();
    }

    public void generateBoard(){
        System.out.println("===========");
        ss = new SudokuGenerator();
        ss.genRandomBoard();
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int[][] getBoard(){
        return BOARD;
    }

    public List<Integer> getFlattenedBoard(){
        List<Integer> list = new ArrayList<>();
        for(int i=0; i< BOARD.length; i++){
            for(int j=0; j< BOARD[i].length; j++){
                list.add(BOARD[i][j]);
            }
        }
        return list;
    }

    public Date getDate(){
        return DATE;
    }

    public int getDifficulty(){
        return DIFFICULTY;

    }

    @Override
    public String toString(){
        String lineSeparator = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        for (int[] row : BOARD) {
            sb.append(Arrays.toString(row))
                    .append(lineSeparator).append("\t");
        }

        return "\tDifficulty: " + Integer.toString(DIFFICULTY) + "\n\tDate: "+ DATE.toString() + "\n\tBoard: \n\t" +sb.toString();


    }

    private class SudokuGenerator {

        private final int BOARD_START_INDEX = 0;
        private final int BOARD_SIZE = 9;
        private final int NO_VALUE = 0;
        private final int MIN_VALUE = 1;
        private final int MAX_VALUE = 9;
        private final int SUBSECTION_SIZE = 3;
        //hard sudokus = 27 numbers left - easy = 30~
        private final int NUMBER_LIMIT = (int) (Math.random() * 4) +27;
        private int[][] board;


        //private boolean column_row = ((int) (Math.random() * 2)) == 0;


        public SudokuGenerator(int[][] board){
            this.board = board;
        }
        public SudokuGenerator(){

            board = new int[][]{
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
            };
        }


        private boolean solve() {
            for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
                //looping through each row in the board
                for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
                    //looping through each column in board
                    //both loops will go through every number in the board
                    if (board[row][column] == NO_VALUE) {
                        //checking to see if the number has not been filled in
                        for (int k = MIN_VALUE; k <= MAX_VALUE; k++) {
                            //looping through every possible value of number that the position in board could have
                            board[row][column] = k;
                            if (isValid(board, row, column) && solve()) {
                                //calling itself to implement the backtracking part of the algorithm
                                //checking to see if that value from the loop is valid with previous numbers entered
                                return true;

                            }
                            board[row][column] = NO_VALUE;
                            //Setting value to 0 if 1-9 does not fit into the constraints of the algorithm
                        }
                        return false;
                    }
                }
            }
            return true;
        }

        private void genRandomBoard() {
            boolean n = true;
            int numberOfTimes = 0;
            while (n) {
                if (numberOfTimes < 20) {
                    int randomRow = ((int) (Math.random() * 9));
                    int randomColumn = ((int) (Math.random() * 9));
                    board[randomRow][randomColumn] = ((int) (Math.random() * 9));
                    if (!isValid(board, randomRow, randomColumn)) {
                        board[randomRow][randomColumn] = NO_VALUE;
                        numberOfTimes--;
                    }
                    numberOfTimes++;
                }else{
                    n = false;
                }
            }
            solve();
        }

        private void createSolveableBoard(){
            //Needs to have aleast 8 of the 9 numbers present on the board
            //Subtract 4 number for first 20 subtraction diagonally apart
            //then subtract 2 number for remain subtractions
        }



        private boolean isValid(int[][] board, int row, int column) {
            return (rowConstraint(board, row)
                    && columnConstraint(board, column)
                    && subsectionConstraint(board, row, column));
        }

        private boolean rowConstraint(int[][] board, int row) {
            boolean[] constraint = new boolean[BOARD_SIZE];
            return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
                    .allMatch(column -> checkConstraint(board, row, constraint, column));
        }

        private boolean columnConstraint(int[][] board, int column) {
            boolean[] constraint = new boolean[BOARD_SIZE];
            return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
                    .allMatch(row -> checkConstraint(board, row, constraint, column));
        }

        private boolean subsectionConstraint(int[][] board, int row, int column) {
            boolean[] constraint = new boolean[BOARD_SIZE];
            int subsectionRowStart = (row / SUBSECTION_SIZE) * SUBSECTION_SIZE;
            int subsectionRowEnd = subsectionRowStart + SUBSECTION_SIZE;

            int subsectionColumnStart = (column / SUBSECTION_SIZE) * SUBSECTION_SIZE;
            int subsectionColumnEnd = subsectionColumnStart + SUBSECTION_SIZE;

            for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
                for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                    if (!checkConstraint(board, r, constraint, c)) return false;
                }
            }
            return true;
        }

        private boolean checkConstraint(int[][] board, int row, boolean[] constraint, int column) {
            if (board[row][column] != NO_VALUE) {
                if (!constraint[board[row][column] - 1]) {
                    constraint[board[row][column] - 1] = true;
                } else {
                    return false;
                }
            }
            return true;
        }

        private void printBoard() {

            for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
                for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
                    System.out.print(board[row][column] + " ");
                }
                System.out.println();
            }
        }
    }


}

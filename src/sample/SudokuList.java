package sample;


import java.util.*;


public final class SudokuList extends ArrayList{

    private static SudokuList sudokuList = null;


    private SudokuList(Collection<Sudoku> sList){
        super.addAll(sList);
    }

    public static SudokuList getInstance(){
        if(sudokuList == null){
            sudokuList = new SudokuList(Database.getInstance().getSudokus());
        }
        return sudokuList;


    }

    public void addSudoku(Sudoku s){
        super.add(s);
        Database.getInstance().addSudoku(s);
    }

    public List<Integer> getDifficulties(){
        List<Integer> difficulties = new ArrayList<>();

        for(int i=0; i< super.size(); i++){
            difficulties.add(((Sudoku) super.toArray()[i]).getDifficulty()) ;
        }
        return difficulties;
    }

    public List<Date> getDates(){
        List<Date> dates = new ArrayList<>();
        for(int i=0; i< super.size(); i++){
            dates.add(((Sudoku) super.toArray()[i]).getDate());
        }
        return dates;
    }

    public List<String> getNames(){
        List<String> names = new ArrayList<>();
        for(int i=0; i< super.size(); i++){
            names.add(((Sudoku) super.toArray()[i]).getName());
        }

        return names;
    }


    public Object[] sortDifficulty(boolean reverse) {
        List<Object> list = Arrays.asList(super.toArray());
        List<Integer> dList = getDifficulties();

        //Looping through the list
        for (int outer = 1; outer < list.size(); ++outer) {
            //setting inner loop to outer loop index
            int inner = outer;
            if(reverse){
                while (inner > 0 && dList.get(inner - 1) > dList.get(inner)) {

                    Collections.swap(list, inner-1, inner);
                    Collections.swap(dList, inner-1, inner);

                    inner--;
                }
            }else{
                while (inner > 0 && dList.get(inner - 1) < dList.get(inner)) {

                    Collections.swap(list, inner-1, inner);
                    Collections.swap(dList, inner-1, inner);

                    inner--;

                }
            }


        }
        return list.toArray();
    }

    public Object[] sortDifficulty(){
        return sortDifficulty(false);
    }

    public Object[] sortDate(boolean reverse){
        List<Object> list = Arrays.asList(super.toArray());
        List<Date> dList = getDates();

        //Looping through the list
        for (int outer = 1; outer < list.size(); outer++) {
            //setting inner loop to outer loop index
            int inner = outer;

            if(reverse){
                //checking to see if inner > 0
                //and if the number before in the array is greater than the current position
                while (inner > 0 && dList.get(inner - 1).after(dList.get(inner))) {

                    Collections.swap(list, inner-1, inner);
                    Collections.swap(dList, inner-1, inner);
                    inner--;
                }
            }else{
                while (inner > 0 && dList.get(inner - 1).before(dList.get(inner))) {

                    Collections.swap(list, inner-1, inner);
                    Collections.swap(dList, inner-1, inner);
                    inner--;
                }
            }
        }
        return list.toArray();
    }

    public Object[] sortDate(){
        return sortDate(false);
    }


    @Override
    public String toString(){
        String lineSeparator = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        for (Sudoku s : ((Sudoku[]) super.toArray())) {
            sb.append(s.toString()).append(lineSeparator);
        }
        return "\nSudoku List: \n"+ sb;

    }



}

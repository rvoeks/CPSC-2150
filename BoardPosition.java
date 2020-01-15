package cpsc2150.extendedTicTacToe;

public class BoardPosition {
    private int row;
    private int col;

    /**
     * constructor for BoardPosition class
     * @param r - variable to hold the value of a row position for a player marker on the board
     * @param c - variable to hold the value of a col position for a player marker on the board
     * @pre
     * row and col param will be in their possible range (indexes 0-7)
     * @post
     * assigns param to row and col private members respectively
     */
    public BoardPosition(int r, int c) {
        row = r;
        col = c;
    }

    /**
     * overwritten equals function to see if two positions exactly match each other
     * @param pos - the BoardPosition object being compared to see if matches
     * @return true if index of row && index of col are respectively the same
     * @pre
     * pos param has row and col values that in bounds of their index range
     * @post
     * returns true if they match values otherwise return false
     */
    public boolean equals(BoardPosition pos){
        if(this.getRow() == pos.getRow() && this.getColumn() == pos.getColumn())
            return true;
        else
            return false;
    }

    /**
     * formats row and col into a string to return -> ("row,col")
     * @return - String of row and col values
     * @post
     * string with the two correct int values, following correct format
     */
    public String toString(){
        return this.getRow() + "," +this.getColumn();
    }

    /**
     * returns value of row
     * @return value of row index
     * @post returns the row data field
     */
    public int getRow(){
        return row;
    }

    /**
     * returns value of col
     * @return value of col index
     * @post returns the row data field
     */
    public int getColumn(){
        return col;
    }
}

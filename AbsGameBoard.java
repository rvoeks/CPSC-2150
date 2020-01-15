package cpsc2150.extendedTicTacToe;

public abstract class AbsGameBoard implements IGameBoard {
    @Override
    /**
     * makes a string of a the current board with all the placed player markers
     * @return string of the current board and all the placed player marker
     * @post
     * follows the correct format (show all index values above / besides their row / col)
     */
    public String toString() {
        BoardPosition pos;
        String format = "\n       COLUMN  \n";
        format += "    |";

        for(int i = 0; i < this.getNumColumns(); i++) {
            if( i < 10 )
                format += " " + i + "|";
            else
                format += i + "|";
        }
        format += "\n";

        for(int r = 0; r < this.getNumRows(); r++) {
            if( r == 0 )
                format += "R  " +r + "|";
            else if( r == 1 )
                format += "O  " +r + "|";
            else if( r == 2 )
                format += "W  " +r + "|";
            else if( r < 10 )
                format += "   " +r + "|";
            else
                format += "  " +r + "|";

            for(int c = 0; c < this.getNumColumns(); c++){
                pos = new BoardPosition(r,c);
                format += " " + this.whatsAtPos(pos) + "|";
            }
            format += "\n";
        }
        return format;
    }
}

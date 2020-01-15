package cpsc2150.extendedTicTacToe;

/**
 * IGameBoard represents a rectangular game grid for playing tictactoe
 * side lengths and winning amount are decided by the user
 * can win by vertical, horizontal, and diagonal connections
 * Initialization ensures: grid starts with only empty spaces with corrects side lengths
 * Defines:         grid
 *                  rowSideLength
 *                  columnSideLength
 *                  winningAmount
 *                  numberOfPlayers
 * Constraints:     MIN_SIDE_LENGTH <= rowSideLength <= MAX_SIDE_LENGTH
 *                  MIN_SIDE_LENGTH <= columnSideLength <= MAX_SIDE_LENGTH
 *                  MIN_TO_WIN <= winningAmount <= MAX_TO_WIN
 *                  MIN_PLAYERS <= numberOfPlayers <= MAX_PLAYERS
 */
public interface IGameBoard {

    public static final int MIN_SIDE_LENGTH = 3;
    public static final int MIN_TO_WIN = 3;


    /**
     * makes a string of a the current board with all the placed player markers
     * @return string of the current board and all the placed player marker
     * @post
     * follows the correct format (show all index values above / besides their row / col)
     */
    public String toString();

    /**
     * checks if the row and col values in pos already has a player marker in that spot in the game board grid
     * @param pos - BoardPosition object with row and col value
     * @return - true if those row and col values align with a blank spot in the game board grid
     * @pre row >= 0 && row < getNumRows
     *      col >= 0 && col < getNumColumns
     * @post returns false if pos not in bounds or already taken by a player
     */
    default public boolean checkSpace(BoardPosition pos) {
        return (this.whatsAtPos(pos) == ' ');
    }

    /**
     * places the player marker char in the position given by the marker param
     * @param marker - BoardPosition object holding in bounds row and col values
     * @param player - the character marker of which player's turn it is (either X or O)
     * @pre
     * only called if marker param has values that are all in bounds
     * player param follows consistent notation -> all capitals characters
     * marker corresponds with an empty spot
     * @post
     * adds new move to current game board grid
     */
    public void placeMarker(BoardPosition marker, char player);

    /**
     * checks to see if param lastpos resulted in a 5 in a row win
     * @param lastpos - the row and col position of the most recent player move
     * @return true if there is a winner
     * @pre row >= 0 && row < getNumRows
     *      col >= 0 && col < getNumColumns
     * @post
     * calls other check_Win functions
     * if any check_Win functions return true, then this function returns true
     */
    default public boolean checkForWinner(BoardPosition lastpos) {
        char playerMarker = this.whatsAtPos(lastpos);
        if( this.checkHorizontalWin(lastpos, playerMarker) )
            return true;
        else if( this.checkVerticalWin(lastpos, playerMarker) )
            return true;
        else if( this.checkDiagonalWin(lastpos, playerMarker) )
            return true;
        else
            return false;
    }

    /**
     * checks for a draw by seeing if there no winner and no available spaces to play in
     * @return true if there is no winner and no available spaces
     * @pre
     * checkForWinner was called after every marker was placed on the board, and did not return true
     * @post
     * false if there is still blank spaces in the current game board grid
     */
    default public boolean checkForDraw() {
        BoardPosition pos;
        for(int r = 0; r < getNumRows(); r++) {
            for(int c = 0; c < getNumColumns(); c++) {
                pos = new BoardPosition(r,c);
                if( this.checkSpace(pos) );
                    return false;
            }
        }
        return true;
    }

    /**
     * checks if there was a win condition for a horizontal line (5 in a row)
     * @param lastpos - the lastpos that was played
     * @param player - which player (X or O) that we're checking for
     * @return true if we found WINNING_AMOUNT of the same player marker in a horizontal line
     * @pre
     * lastpos has in bounds row and col values
     * player is correct player marker (gotten from current game board)
     * @post
     * false if no winner found
     * @invariant col stays in bounds
     */
    default public boolean checkHorizontalWin(BoardPosition lastpos, char player) {
        int howManyToTheRight = 0;
        int howManyToTheLeft = 0;
        int totalHorizontalCount = 1;
        int row = lastpos.getRow();
        int col = lastpos.getColumn();

        BoardPosition pos = lastpos;

        // loop to check how many to the right
        while( this.whatsAtPos(pos) == player ) {
            pos = new BoardPosition(row,col);

            //keeps col in bounds
            if( col < this.getNumColumns()-1 ) { col+=1; }
            else { break; }

            pos = new BoardPosition(row,col);

            //checks if same player marker continues and tracks how many
            if( this.whatsAtPos(pos) == player ) { howManyToTheRight+=1; }
            else { break; }
        }

        col = lastpos.getColumn(); // resets col back to original value
        pos = new BoardPosition(row,col);
        // loop to check how many to the left
        while( this.whatsAtPos(pos) == player ) {
            pos = new BoardPosition(row,col);

            //keeps col in bounds
            if( col > 0 ) { col-=1; }
            else { break; }

            pos = new BoardPosition(row,col);

            //checks if same player marker continues and tracks how many
            if( this.whatsAtPos(pos) == player ) { howManyToTheLeft+=1; }
            else { break; }
        }

        totalHorizontalCount += howManyToTheRight + howManyToTheLeft;

        if( totalHorizontalCount >= this.getNumToWin() )
            return true;
        else
            return false;
    }

    /**
     * checks if there was a win condition for a vertical line (5 in a row)
     * @param lastpos - the lastpos that was played
     * @param player - which player (X or O) that we're checking for
     * @return true if we found WINNING_AMOUNT of the same player marker in a vertical line
     * @pre
     * lastpos has in bounds row and col values
     * player is correct player marker (gotten from current game board)
     * @post
     * false if no winner found
     * @invariant col stays in bounds
     */
    default public boolean checkVerticalWin(BoardPosition lastpos, char player) {
        int howManyToTheTop = 0;
        int howManyToTheBottom = 0;
        int totalVerticalCount = 1;
        int row = lastpos.getRow();
        int col = lastpos.getColumn();

        BoardPosition pos = lastpos;

        // loop to check how many to the top
        while( this.whatsAtPos(pos) == player ) {
            pos = new BoardPosition(row,col);

            //keeps row in bounds
            if( row > 0 ) { row-=1; }
            else { break; }

            pos = new BoardPosition(row,col);

            //checks if same player marker continues and tracks how many
            if( this.whatsAtPos(pos) == player ) { howManyToTheTop+=1; }
            else { break; }
        }

        row = lastpos.getRow(); // resets row back to original value
        pos = new BoardPosition(row,col);
        // loop to check how many to the bottom
        while( this.whatsAtPos(pos) == player ) {
            pos = new BoardPosition(row,col);

            //keeps row in bounds
            if( row < this.getNumRows()-1 ) { row+=1; }
            else { break; }

            pos = new BoardPosition(row,col);

            //checks if same player marker continues and tracks how many
            if( this.whatsAtPos(pos) == player ) { howManyToTheBottom+=1; }
            else { break; }
        }

        totalVerticalCount += howManyToTheTop + howManyToTheBottom;

        if( totalVerticalCount >= this.getNumToWin() )
            return true;
        else
            return false;
    }

    /**
     * checks if there was a win condition for a diagonal line (5 in a row)
     * @param lastpos - the lastpos that was played
     * @param player - which player (X or O) that we're checking for
     * @return true if we found WINNING_AMOUNT of the same player marker in a diagonal line
     * @pre
     * lastpos has in bounds row and col values
     * player is correct player marker (gotten from current game board)
     * @post
     * false if no winner found
     */
    default public boolean checkDiagonalWin(BoardPosition lastpos, char player) {
        int howManyToTheTopSide = 0;
        int howManyToTheBottomSide = 0;
        int totalDiagonalCount = 1;
        int row = lastpos.getRow();
        int col = lastpos.getColumn();

        BoardPosition pos = lastpos;

        //////////////////// positive slope check first

        // loop to check how many to the top right
        while( this.whatsAtPos(pos) == player ) {
            pos = new BoardPosition(row,col);

            //keeps row and col in bounds
            if( row > 0 ) { row-=1; }
            else { break; }
            if( col < this.getNumColumns()-1 ) { col+=1; }
            else { break; }

            pos = new BoardPosition(row,col);

            //checks if same player marker continues and tracks how many
            if( this.whatsAtPos(pos) == player ) { howManyToTheTopSide+=1; }
            else { break; }
        }

        row = lastpos.getRow(); // resets row back to original value
        col = lastpos.getColumn(); // resets col back to original value
        pos = new BoardPosition(row,col);
        // loop to check how many to the bottom side
        while( this.whatsAtPos(pos) == player ) {
            pos = new BoardPosition(row,col);

            //keeps row and col in bounds
            if( row < this.getNumRows()-1 ) { row+=1; }
            else { break; }
            if( col > 0 ) { col-=1; }
            else { break; }

            pos = new BoardPosition(row,col);

            //checks if same player marker continues and tracks how many
            if( this.whatsAtPos(pos) == player ) { howManyToTheBottomSide+=1; }
            else { break; }
        }


        totalDiagonalCount += howManyToTheTopSide + howManyToTheBottomSide;
        if( totalDiagonalCount >= this.getNumToWin() )
            return true;
        else {
            row = lastpos.getRow(); // resets row back to original value
            col = lastpos.getColumn(); // resets col back to original value
            howManyToTheTopSide = 0;
            howManyToTheBottomSide = 0;
            totalDiagonalCount = 1;
            pos = new BoardPosition(row,col);
        }

        /////////////////////////////// negative slope now

        // loop to check how many to the top left
        while( this.whatsAtPos(pos) == player ) {
            pos = new BoardPosition(row,col);

            //keeps row and col in bounds
            if( row > 0 ) { row-=1; }
            else { break; }
            if( col > 0 ) { col-=1; }
            else { break; }

            pos = new BoardPosition(row,col);

            //checks if same player marker continues and tracks how many
            if( this.whatsAtPos(pos) == player ) { howManyToTheTopSide+=1; }
            else { break; }
        }

        row = lastpos.getRow(); // resets row back to original value
        col = lastpos.getColumn(); // resets col back to original value
        pos = new BoardPosition(row,col);
        // loop to check how many to the bottom right
        while( this.whatsAtPos(pos) == player ) {
            pos = new BoardPosition(row,col);

            //keeps row and col in bounds
            if( row < this.getNumRows()-1 ) { row+=1; }
            else { break; }
            if( col < this.getNumColumns()-1 ) { col+=1; }
            else { break; }

            pos = new BoardPosition(row,col);

            //checks if same player marker continues and tracks how many
            if( this.whatsAtPos(pos) == player ) { howManyToTheBottomSide+=1; }
            else { break; }
        }


        totalDiagonalCount += howManyToTheTopSide + howManyToTheBottomSide;
        if( totalDiagonalCount >= this.getNumToWin() )
            return true;
        else {
            return false;
        }

    }

    /**
     * returns what character is found in the current game board grid
     * (row and col values gotten from pos param)
     * @param pos - has the row and col values to use to check what is in the grid
     * @return - what character occupies that space
     * @pre
     * pos has in bounds row and col values
     * @post
     * returns either ' ' or 'X' or 'O'
     */
    public char whatsAtPos(BoardPosition pos); // not default

    /**
     * checks to see if player char is at the current game board grid position
     * provided by the pos param
     * @param pos - has the row and col values to use to check what is in the grid
     * @param player - the player marker being checked for (either 'X' or 'O')
     * @return true if the grid value from row and col pos equals the player param
     * @pre
     * pos has in bounds row and col values
     * @post
     * false if they don't match
     */
    default public boolean isPlayerAtPos(BoardPosition pos, char player) {
        if( this.whatsAtPos(pos) == player )
            return true;
        else
            return false;
    }

    /**
     * returns the chosen number of rows
     * @return - number of rows
     * @post returns the correct number of rows
     */
    public int getNumRows(); //not default

    /**
     * returns the chosen number of columns
     * @return - number of columns
     * @post returns the correct number of columns
     */
    public int getNumColumns(); //not default

    /**
     * returns the chosen number of similar player markers in a row to produce a win
     * @return - number of "in a row" for win condition
     * @post returns the correct number of "in a row" for win condition
     */
    public int getNumToWin(); //not default
}

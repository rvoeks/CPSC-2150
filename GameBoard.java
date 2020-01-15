package cpsc2150.extendedTicTacToe;

/**
 * @Invariants 0 <= BoardPosition.getRow() < rowSideLength
 *             0 <= BoardPosition.getColumn() < colSideLength
 *             MIN_SIDE_LENGTH <= rowSideLength <= MAX_SIDE_LENGTH
 *             MIN_SIDE_LENGTH <= colSideLength <= MAX_SIDE_LENGTH
 *             MIN_TO_WIN <= WINNING_AMOUNT <= MAX_TO_WIN
 * Correspondence grid = gameGrid
 *                rowSideLength = rowSideLength
 *                columnSideLength = colSideLength
 *                winningAmount = WINNING_AMOUNT
 */
public class GameBoard extends AbsGameBoard {

    private int rowSideLength;
    private int colSideLength;
    private int WINNING_AMOUNT;
    private char[][] gameGrid;
    private int totalNumberOfPlayerMarkers; // to make checkForDraw a lot faster

    /**
     * constructor for GameBoard Object
     * @param rowSide length of the row side. how many rows will be available for play
     * @param colSide length of the column side. how many columns will be available for play
     * @param winningAmount the amount of "in a row" for a win condition
     * @post
     * creates GameBoard Object and constructs empty grid for new game
     * sets totalNumberOfPlayerMarkers to zero
     */
    public GameBoard(int rowSide, int colSide, int winningAmount){
        rowSideLength = rowSide;
        colSideLength = colSide;
        WINNING_AMOUNT = winningAmount;

        gameGrid = new char[rowSideLength][colSideLength];
        for(int r = 0; r < rowSideLength; r++)
            for(int c = 0; c < colSideLength; c++)
                gameGrid[r][c] = ' ';

        totalNumberOfPlayerMarkers = 0;
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
    public void placeMarker(BoardPosition marker, char player){
        int row = marker.getRow();
        int col = marker.getColumn();

        gameGrid[row][col] = player;
        totalNumberOfPlayerMarkers += 1;
    }


    /**
     * checks for a draw by seeing if there no winner and no available spaces to play in
     * @return true if there is no winner and no available spaces
     * @pre
     * checkForWinner was called after every marker was placed on the board, and did not return true
     * @post
     * false if there is still blank spaces in the current game board grid
     */
    @Override
    public boolean checkForDraw(){
        int area = rowSideLength * colSideLength;
        if( area == totalNumberOfPlayerMarkers )
            return true;
        else
            return false;
    }

    /**
     * returns what character is found in the current game board grid
     * (row and col values gotten from pos param)
     * @param pos - has the row and col values to use to check what is in the grid
     * @return - what character occupies that space
     * @pre
     * pos has in bounds row and col values
     * @post
     * returns either ' ' or a player marker
     */
    public char whatsAtPos(BoardPosition pos){
        return gameGrid[pos.getRow()][pos.getColumn()];
    }

    /**
     * returns the chosen number of rows
     * @return - number of rows
     * @post returns the correct number of rows
     */
    public int getNumRows() {
        return rowSideLength;
    }

    /**
     * returns the chosen number of columns
     * @return - number of columns
     * @post returns the correct number of columns
     */
    public int getNumColumns() {
        return colSideLength;
    }

    /**
     * returns the chosen number of similar player markers in a row to produce a win
     * @return - number of "in a row" for win condition
     * @post returns the correct number of "in a row" for win condition
     */
    public int getNumToWin() {
        return WINNING_AMOUNT;
    }
}

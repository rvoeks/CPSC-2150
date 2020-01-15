package cpsc2150.extendedTicTacToe;

import java.util.*;

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
public class GameBoardMem extends AbsGameBoard {

    private int rowSideLength;
    private int colSideLength;
    private int WINNING_AMOUNT;
    private Map<Character, List<BoardPosition> > gameGrid;
    private int totalNumberOfPlayerMarkers; // to make checkForDraw a lot faster

    /**
     * constructor for GameBoardMem Object
     * @param rowSide length of the row side. how many rows will be available for play
     * @param colSide length of the column side. how many columns will be available for play
     * @param winningAmount the amount of "in a row" for a win condition
     * @post
     * creates GameBoardMem Object and constructs empty map for new game
     * sets totalNumberOfPlayerMarkers to zero
     */
    public GameBoardMem(int rowSide, int colSide, int winningAmount){
        rowSideLength = rowSide;
        colSideLength = colSide;
        WINNING_AMOUNT = winningAmount;

        totalNumberOfPlayerMarkers = 0;

        gameGrid = new HashMap<Character, List<BoardPosition>>();
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
        List<BoardPosition> values;
        if( !gameGrid.containsKey(player) ) {
            values = new ArrayList<BoardPosition>();
            values.add(marker);
            gameGrid.put(player, values);
        } else {
            values = gameGrid.get(player);
            values.add(marker);
            gameGrid.put(player, values);
        }
        totalNumberOfPlayerMarkers += 1;
    }

    /**
     * returns what character is found in the current game board 'grid'
     * (row and col values gotten from pos param)
     * @param pos - has the row and col values to use to check what is in the grid
     * @return - what character occupies that space
     * @pre
     * pos has in bounds row and col values
     * @post
     * returns either ' ' or a player marker
     */
    public char whatsAtPos(BoardPosition pos){

        if( gameGrid.size() == 0 )
            return ' ';
        List<BoardPosition> values;
        char key;
        for (Map.Entry<Character,List<BoardPosition>> entry : gameGrid.entrySet()) {
            key = entry.getKey();
            values = entry.getValue();
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i).equals(pos))
                    return key;
            }
        }
        return ' ';

    }

    /**
     * checks to see if player char is at the current game board grid position
     * provided by the pos param
     * @param pos - has the row and col values to use to check what is in the grid
     * @param player - the player marker being checked for (either 'X' or 'O')
     * @return true if the 'grid' value from row and col pos equals the player param
     * @pre
     * pos has in bounds row and col values
     * @post
     * false if they don't match
     */
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        if( !gameGrid.containsKey(player) ) {
            if( player != ' ' )
                return false;
            else if( player == whatsAtPos(pos) )
                return true;
            else
                return false;
        }

        List<BoardPosition> values = gameGrid.get(player);
        if( values.contains(pos) )
            return true;
        return false;
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

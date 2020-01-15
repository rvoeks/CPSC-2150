package cpsc2150.extendedTicTacToe;

import java.util.ArrayList;
import java.util.List;

/**
 * The TicTacToe controller class will handle communication between our TicTacToeView and our Model (IGameBoard and BoardPosition)
 *
 * This is where you will write code
 *
 * You will need to include your BoardPosition class, the IGameBoard interface
 * and the implementations from previous homeworks
 * If your code was correct you will not need to make any changes to your IGameBoard classes
 */
public class TicTacToeController{
    //our current game that is being played
    private IGameBoard curGame;

    //The screen that provides our view
    private TicTacToeView screen;


    public static final int MAX_PLAYERS = 10;
    public static final char[] possiblePlayers = {'X','O','T','V','R','M','Z','J','I','F'}; // list of the possible ten players

    private List<Character> playerList = new ArrayList<Character>(); // the list of players that will be decided after np is sent
    private int playerIndex; // tracks current index to decide current player

    private boolean gameIsCurrentlyOver; // to tell the program if it needs to call newGame()


    /**
     *
     * @param model the board implementation
     * @param view the screen that is shown
     * @param np The number of players for the game
     * @post the controller will respond to actions on the view using the model.
     */
    TicTacToeController(IGameBoard model, TicTacToeView view, int np){
        this.curGame = model;
        this.screen = view;

        for(int i = 0; i < np; i++) {
            playerList.add( possiblePlayers[i] ); // add the player markers that will be necessary for whatever number of players
        }
        playerIndex = 0;
        gameIsCurrentlyOver = false;

        screen.setMessage("Player " +playerList.get(playerIndex) +"'s Turn.");
    }

    /**
     *
     * @param row the row of the activated button
     * @param col the column of the activated button
     * @pre row and col are in the bounds of the game represented by the view
     * @post The button pressed will show the right token and check if a player has won.
     */
    public void processButtonClick(int row, int col) {
        if(gameIsCurrentlyOver) {
            gameIsCurrentlyOver = false;
            newGame();
        } else {

            BoardPosition pos = new BoardPosition(row, col);
            if( curGame.checkSpace(pos) ) { // makes sure valid input
                curGame.placeMarker(pos, playerList.get(playerIndex));
                screen.setMarker(row, col, playerList.get(playerIndex));

                if (curGame.checkForWinner(pos)) {
                    screen.setMessage("Player " + playerList.get(playerIndex) + " Wins! Click anywhere to start another game.");
                    gameIsCurrentlyOver = true;
                } else if (curGame.checkForDraw()) {
                    screen.setMessage("The game has ended in a draw. Click anywhere to start another game.");
                    gameIsCurrentlyOver = true;
                } else { // next player
                    playerIndex += 1;
                    if (playerIndex == playerList.size()) playerIndex = 0; // loop back to first if last player goes

                    screen.setMessage("Player " + playerList.get(playerIndex) + "'s Turn.");
                }

            } else { // no player marker gets placed and current player keeps their turn
                screen.setMessage("That spot is already taken. Player " + playerList.get(playerIndex) + "'s Turn.");
            }

        } // [ else ^ ] = game is in progress

    }

    private void newGame()
    {
        screen.dispose();
        GameSetupScreen screen = new GameSetupScreen();
        GameSetupController controller = new GameSetupController(screen);
        screen.registerObserver(controller);
    }
}

package server.game;

public class GameInfo {
    private boolean endGame;
    private int winnerCounter;
    private char declaratedColour;
    private boolean stopBattle;
    private int gameMove;

    public GameInfo(boolean endGame, int winnerCounter, char declaratedColour, boolean stopBattle, int gameMove) {
        this.endGame = endGame;
        this.winnerCounter = winnerCounter;
        this.declaratedColour = declaratedColour;
        this.stopBattle = stopBattle;
        this.gameMove = gameMove;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public int getWinnerCounter() {
        return winnerCounter;
    }

    public void setWinnerCounter(int winnerCounter) {
        this.winnerCounter = winnerCounter;
    }

    public char getDeclaratedColour() {
        return declaratedColour;
    }

    public void setDeclaratedColour(char declaratedColour) {
        this.declaratedColour = declaratedColour;
    }

    public boolean isStopBattle() {
        return stopBattle;
    }

    public void setStopBattle(boolean stopBattle) {
        this.stopBattle = stopBattle;
    }

    public int getGameMove() {
        return gameMove;
    }

    public void setGameMove(int gameMove) {
        this.gameMove = gameMove;
    }
}

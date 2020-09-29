package actors;


import java.util.ArrayList;

public class Table {

    private ArrayList<Player> players = new ArrayList();
    private int maxPlayers;
    private int playersCounter = 0;

    public Table(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void addPlayer(Player player){
        players.add(player);
        playersCounter += 1;
    }

    public void removePlayer(Player player){
        players.remove(player);
        playersCounter -= 1;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getPlayersCounter() {
        return playersCounter;
    }


}

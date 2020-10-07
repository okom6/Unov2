package server.actors;

import server.Game;

import java.util.ArrayList;

public class Room {

    private static final Room INSTANCE = new Room();
    private ArrayList<Table> tables = new ArrayList();
    private ArrayList<Player> players = new ArrayList();


    public static Room getInstance(){
        return INSTANCE;
    }

    public void addTable(int tableSize){
        tables.add(new Table(tableSize));
    }

    public void removeTable(Table table){
        tables.remove(table);
    }

    public void addPlayer(Player player, int maxPlayers){
        players.add(player);
        addPlayerToTable(player, maxPlayers);
    }

    public void removePlayer(Player player){
        player.getTable().removePlayer(player);
        player.setTable(null);
        players.remove(player);
        player.disconnect();
    }

    public void addPlayerToTable(Player player, int maxPlayers){
        try {
            Table table = tables.stream().filter(t -> t.getPlayersCounter() < t.getMaxPlayers())
                    .filter(t -> t.getMaxPlayers() == maxPlayers).findFirst().get();
            table.addPlayer(player);
            player.setTable(table);
            if(table.getMaxPlayers() == table.getPlayersCounter()){
                Game.startGame(table);
            }
        } catch (Exception NoSuchElementException){
            addTable(maxPlayers);
            addPlayerToTable(player, maxPlayers);
        }
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void makeRoomEmpty(){
        tables = new ArrayList<>();
        players = new ArrayList<>();
    }
}

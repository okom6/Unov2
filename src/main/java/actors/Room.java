package actors;

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

    public void addPlayer(Player player){
        players.add(player);
        addPlayerToTable(player);
    }

    public void removePlayer(Player player){
        player.getTable().removePlayer(player);
        player.setTable(null);
        players.remove(player);
    }

    public void addPlayerToTable(Player player){
        try {
            Table table = tables.stream().filter(t -> t.getPlayersCounter() < t.getMaxPlayers()).findFirst().get();
            table.addPlayer(player);
            player.setTable(table);
        } catch (Exception NoSuchElementException){
            addTable(3);
            addPlayerToTable(player);
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

package server.actors;

import server.PlayerConnector;

public class Player {

    private String hashID;
    private int place = -1;
    private boolean active = true;
    private Table table = null;
    private PlayerConnector playerConnector;

    public Player(String hashID, PlayerConnector playerConnector) {
        this.hashID = hashID;
        this.playerConnector = playerConnector;
    }

    public String getHashID() {
        return hashID;
    }

    public int getPlace() {
        return place;
    }

    public boolean isActive() {
        return active;
    }

    public Table getTable() {
        return table;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public PlayerConnector getPlayerConnector() {
        return playerConnector;
    }

    public void disconnect(){
        playerConnector.closeConnection();
    }

    public void leaveTable(){
        this.table.removePlayer(this);
    }
}

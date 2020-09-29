package actors;

public class Player {

    private String hashID;
    private int place = -1;
    private boolean active = true;
    private Table table = null;

    public Player(String hashID) {
        this.hashID = hashID;
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
}

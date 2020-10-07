package actorsTest;

import server.PlayerConnector;
import server.actors.Player;
import server.actors.Room;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RoomTests {

    @Test
    public void getRoomInstanceSuccessfull(){
        Room room = Room.getInstance();
        room.makeRoomEmpty();
        assertNotNull(room);
        assertEquals(room, Room.getInstance());
    }

    @Test
    public void addTableSuccessfull(){
        Room room = Room.getInstance();
        room.makeRoomEmpty();
        room.addTable(3);
        assertEquals(1, room.getTables().size());
    }

    @Test
    public void removeTableSuccessfull(){
        Room room = Room.getInstance();
        room.makeRoomEmpty();
        room.addTable(3);
        room.removeTable(room.getTables().get(0));
        assertEquals(0, room.getTables().size());
    }

    @Test
    public void addPlayerToTableSuccessfull(){
        Room room = Room.getInstance();
        room.makeRoomEmpty();
        Player player = new Player("1", new PlayerConnector(null, null, null));
        room.addTable(3);
        room.addPlayerToTable(player, 3);
        assertNotNull(player.getTable());
    }

    @Test
    public void addPlayerToFullTablesSuccessfull(){
        Room room = Room.getInstance();
        room.makeRoomEmpty();
        Player player = new Player("1", new PlayerConnector(null, null, null));
        room.addPlayerToTable(player, 3);
        assertEquals(1, room.getTables().size());
        assertNotNull(player.getTable());
    }

    @Test
    public void addPlayerSuccessfull(){
        Room room = Room.getInstance();
        room.makeRoomEmpty();
        room.addTable(3);
        room.addPlayer(new Player("1", new PlayerConnector(null, null, null)), 3);
        assertEquals(1, room.getPlayers().size());
    }

    @Test
    public void removePlayerSuccessfull(){
        Room room = Room.getInstance();
        room.makeRoomEmpty();
        Player player = new Player("1", new PlayerConnector(null, null, null));
        room.addTable(3);
        room.addPlayer(player, 3);
        room.removePlayer(player);
        assertEquals(0, room.getPlayers().size());
    }
}

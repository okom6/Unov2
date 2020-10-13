package actorsTest;

import server.PlayerConnector;
import server.actors.Player;
import server.actors.Table;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class PlayerTests {

    @Test
    public void createdPlayerSuccessfull(){
        Player player = new Player("1", new PlayerConnector(null, null, null));
        assertEquals("1", player.getHashID());
    }

    @Test
    public void setTableToPlayerSuccessfull(){
        Player player = new Player("1", new PlayerConnector(null, null, null));
        player.setTable(new Table(4));
        assertNotNull(player.getTable());
    }

    @Test
    public void setPlaceToPlayerSuccessfull(){
        Player player = new Player("1", new PlayerConnector(null, null, null));
        player.setPlace(2);
        assertEquals(2, player.getPlace());
    }

    @Test
    public void setActiveToPlayerSuccessfull(){
        Player player = new Player("1", new PlayerConnector(null, null, null));
        player.setActive(false);
        assertEquals(false, player.isActive());
    }

    @Test
    public void getPlayerConnectorSuccessfull(){
        PlayerConnector playerConnector = new PlayerConnector(null, null, null);
        Player player = new Player("1", playerConnector);
        assertEquals(playerConnector, player.getPlayerConnector());
    }

    @Test
    public void disconnectPlayerSuccessfull(){
        PlayerConnector playerConnector = mock(PlayerConnector.class);
        Player player = new Player("1", playerConnector);
        player.disconnect();
    }

    @Test
    public void playerLeaveTAbleSuccessfull(){
        Table table = new Table(3);
        Player player = new Player("1", new PlayerConnector(null, null, null));
        table.addPlayer(player);
        player.setTable(table);
        player.leaveTable();
        assertEquals(null, player.getTable());
    }
}

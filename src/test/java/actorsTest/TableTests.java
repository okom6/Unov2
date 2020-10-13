package actorsTest;

import server.PlayerConnector;
import server.actors.Player;
import server.actors.Table;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TableTests {

    @Test
    public void createdTableSuccessfull(){
        Table table = new Table(3);
        assertEquals(3, table.getMaxPlayers());
    }

    @Test
    public void getlayerConuterSuccessfull(){
        Table table = new Table(3);
        assertEquals(0, table.getPlayersCounter());
    }

    @Test
    public void addPlayerToTableSuccessfull(){
        Table table = new Table(3);
        table.addPlayer(new Player("1", new PlayerConnector(null, null, null)));
        assertEquals(1, table.getPlayersCounter());
    }

    @Test
    public void removePlayerFromTableSuccessfull(){
        Table table = new Table(3);
        Player player = new Player("1", new PlayerConnector(null, null, null));
        table.addPlayer(player);
        table.removePlayer(player);
        assertEquals(0, table.getPlayersCounter());
    }

    @Test
    public void getPlayersSuccessfull(){
        Table table = new Table(3);
        Player player = new Player("1", new PlayerConnector(null, null, null));
        table.addPlayer(player);
        assertEquals(player, table.getPlayers().get(0));
    }
}

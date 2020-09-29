package actorsTest;

import actors.Player;
import actors.Table;
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
        table.addPlayer(new Player("1"));
        assertEquals(1, table.getPlayersCounter());
    }

    @Test
    public void removePlayerFromTableSuccessfull(){
        Table table = new Table(3);
        Player player = new Player("1");
        table.addPlayer(player);
        table.removePlayer(player);
        assertEquals(0, table.getPlayersCounter());
    }
}

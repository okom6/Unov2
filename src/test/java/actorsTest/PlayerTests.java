package actorsTest;

import actors.Player;
import actors.Table;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlayerTests {

    @Test
    public void createdPlayerSuccessfull(){
        Player player = new Player("1");
        assertEquals("1", player.getHashID());
    }

    @Test
    public void setTableToPlayerSuccessfull(){
        Player player = new Player("1");
        player.setTable(new Table(4));
        assertNotNull(player.getTable());
    }

    @Test
    public void setPlaceToPlayerSuccessfull(){
        Player player = new Player("1");
        player.setPlace(2);
        assertEquals(2, player.getPlace());
    }

    @Test
    public void setActiveToPlayerSuccessfull(){
        Player player = new Player("1");
        player.setActive(false);
        assertEquals(false, player.isActive());
    }
}

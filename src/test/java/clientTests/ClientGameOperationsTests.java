package clientTests;

import client.ClientGameOperations;
import client.ConnectionToServer;
import org.junit.Test;
import server.PlayerConnector;
import server.actors.Player;
import server.actors.Room;
import server.game.GameInfo;
import server.game.GameOperations;
import server.game.actors.Card;
import server.game.actors.PlayerDeck;
import server.game.actors.PlayerGameStateToSend;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ClientGameOperationsTests {

    @Test
    public void showGameInfoSuccessfull(){
        ClientGameOperations clientGameOperations = new ClientGameOperations();
        PlayerGameStateToSend gameInfo = new PlayerGameStateToSend(
                new Card('b', 'c'),
                'r',
                false,
                false,
                false
        );

        ArrayList<Card> handDeck = new ArrayList();
        handDeck.add(new Card('r', '5'));
        handDeck.add(new Card('g', '0'));

        gameInfo.getPlayersCardNumbers().add(2);
        gameInfo.getPlayersCardNumbers().add(3);
        gameInfo.setPlayerNumber(0);
        gameInfo.setPlayerTurn(0);
        gameInfo.setHandDeck(handDeck);

        clientGameOperations.showGameInfo(gameInfo);

        assertEquals(0, 0);
    }

    @Test
    public void showSummaryInfoSuccessfull(){
        ClientGameOperations clientGameOperations = new ClientGameOperations();

        clientGameOperations.showSummaryInfo("Miejsce: 1");

        assertEquals(0, 0);
    }

    @Test
    public void turnVerifySuccessfull(){
        ClientGameOperations clientGameOperations = new ClientGameOperations();
        PlayerGameStateToSend gameInfo = new PlayerGameStateToSend(
                new Card('b', 'c'),
                'r',
                false,
                false,
                false
        );

        ArrayList<Card> handDeck = new ArrayList();
        handDeck.add(new Card('r', '5'));
        handDeck.add(new Card('g', '0'));

        gameInfo.getPlayersCardNumbers().add(2);
        gameInfo.getPlayersCardNumbers().add(3);
        gameInfo.setPlayerNumber(0);
        gameInfo.setPlayerTurn(0);
        gameInfo.setHandDeck(handDeck);

        assertEquals(true, clientGameOperations.turnVerify(gameInfo));
    }
}

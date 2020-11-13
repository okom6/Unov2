package gameTests;

import org.junit.Test;
import org.mockito.Mock;
import server.PlayerConnector;
import server.actors.Player;
import server.actors.Table;
import server.game.Game;
import server.game.GameInfo;
import server.game.GameOperations;
import server.game.actors.Card;
import server.game.actors.PlayerDeck;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class GameOperationsTests{

    @Mock
    Socket clientSocket;
    @Mock
    PrintWriter out;
    @Mock
    BufferedReader in;

    @Test
    public void addFirstCardToStackSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        ArrayList<Card> stack = new ArrayList<>();
        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card('g', '0'));
        gameOperations.addFirstCardToStack(stack, deck);
        assertEquals(1, stack.size());
        assertEquals(0, deck.size());
    }

    @Test
    public void addNotBlackCardToStackSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        ArrayList<Card> stack = new ArrayList<>();
        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card('b', '4'));
        deck.add(new Card('g', '0'));
        gameOperations.addFirstCardToStack(stack, deck);
        assertEquals(1, stack.size());
        assertEquals('g', stack.get(0).getColour());
        assertEquals(1, deck.size());
    }

    @Test
    public void takeCardsFromMainDeckToPlayerSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card('b', '4'));
        deck.add(new Card('g', '0'));
        deck.add(new Card('r', '5'));
        gameOperations.takeCardsFromMainDeckToPlayer(playerDeck, deck, 2);
        assertEquals(1, deck.size());
        assertEquals(2, playerDeck.getHandDeck().size());
        assertEquals('r', deck.get(0).getColour());
    }

    @Test
    public void checkNumberOfActivePlayersWithNoOneFinished(){
        GameOperations gameOperations = new GameOperations();
        PlayerDeck playerDeck1 = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));

        PlayerDeck playerDeck2 = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));

        ArrayList<PlayerDeck> playerDeckArrayList = new ArrayList<>();
        playerDeckArrayList.add(playerDeck1);
        playerDeckArrayList.add(playerDeck2);
        assertEquals(2, gameOperations.checkNumberOfActivePlayers(playerDeckArrayList));
    }

    @Test
    public void checkNumberOfActivePlayersWithFinished(){
        GameOperations gameOperations = new GameOperations();
        PlayerDeck playerDeck1 = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck1.getPlayer().setPlace(1);
        playerDeck1.getPlayer().setActive(false);

        PlayerDeck playerDeck2 = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));

        ArrayList<PlayerDeck> playerDeckArrayList = new ArrayList<>();
        playerDeckArrayList.add(playerDeck1);
        playerDeckArrayList.add(playerDeck2);
        assertEquals(1, gameOperations.checkNumberOfActivePlayers(playerDeckArrayList));
    }

    @Test
    public void checkRulesWhenPlayerTakeSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', false, 1);
        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('b', '0'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('r', '5'));

        assertEquals(true, gameOperations.checkRules(
                playerDeck, stack, "t-0-N", gameInfo));
    }

    @Test
    public void checkRulesWhenPlayerPutStopInStopBattleSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', true, 1);
        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('b', 's'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('r', 's'));

        assertEquals(true, gameOperations.checkRules(
                playerDeck, stack, "p-0-N", gameInfo));
    }

    @Test
    public void checkRulesWhenBlackCardOnTopSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', false, 1);
        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('b', 'c'));
        playerDeck.getHandDeck().add(new Card('r', '0'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('b', 'c'));

        assertEquals(true, gameOperations.checkRules(
                playerDeck, stack, "p-0-b", gameInfo));
        assertEquals(true, gameOperations.checkRules(
                playerDeck, stack, "p-1-N", gameInfo));
    }

    @Test
    public void checkRulesWhenBlackCardOnTopAndTakeSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', false, 1);
        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('b', '4'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('b', '4'));

        assertEquals(true, gameOperations.checkRules(
                playerDeck, stack, "p-0-b", gameInfo));
    }

    @Test
    public void checkRulesWhenColourCardOnTopAndBlackChoosenSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', false, 1);
        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('b', 'c'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('r', '4'));

        assertEquals(true, gameOperations.checkRules(
                playerDeck, stack, "p-0-b", gameInfo));
    }

    @Test
    public void checkRulesWhenColourCardOnTopAndColourChoosenSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', false, 1);
        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('g', '4'));
        playerDeck.getHandDeck().add(new Card('r', '9'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('r', '4'));

        assertEquals(true, gameOperations.checkRules(
                playerDeck, stack, "p-0-b", gameInfo));
        assertEquals(true, gameOperations.checkRules(
                playerDeck, stack, "p-1-b", gameInfo));
    }

    @Test
    public void checkRulesFailSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', false, 1);
        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('g', '9'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('r', '4'));

        assertEquals(false, gameOperations.checkRules(
                playerDeck, stack, "p-0-b", gameInfo));
    }

    @Test
    public void savePlayerMoveWhenIsStopAndPlayerTkeSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', true, 1);

        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('g', '9'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('r', '4'));

        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card('r', '5'));

        gameOperations.savePlayerMove(
                playerDeck, stack, deck, "t-0-_", gameInfo);

        assertEquals(false, gameInfo.isStopBattle());
    }

    @Test
    public void savePlayerMoveWhenTake4CardsSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', false, 1);

        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('g', '9'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('b', '4'));

        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card('r', '5'));
        deck.add(new Card('r', '6'));
        deck.add(new Card('r', '7'));
        deck.add(new Card('r', '8'));

        gameOperations.savePlayerMove(
                playerDeck, stack, deck, "t-0-_", gameInfo);

        assertEquals(5, playerDeck.getHandDeck().size());
        assertEquals(0, deck.size());
    }

    @Test
    public void savePlayerMoveWhenTake2CardsSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', false, 1);

        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('g', '9'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('r', 'g'));

        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card('r', '5'));
        deck.add(new Card('r', '6'));
        deck.add(new Card('r', '7'));
        deck.add(new Card('r', '8'));

        gameOperations.savePlayerMove(
                playerDeck, stack, deck, "t-0-_", gameInfo);

        assertEquals(3, playerDeck.getHandDeck().size());
        assertEquals(2, deck.size());
    }

    @Test
    public void savePlayerMoveWhenTake1CardsSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', false, 1);

        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('g', '9'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('r', '9'));

        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card('r', '5'));
        deck.add(new Card('r', '6'));
        deck.add(new Card('r', '7'));
        deck.add(new Card('r', '8'));

        gameOperations.savePlayerMove(
                playerDeck, stack, deck, "t-0-_", gameInfo);

        assertEquals(2, playerDeck.getHandDeck().size());
        assertEquals(3, deck.size());
    }

    @Test
    public void savePlayerMoveWhenHePutBlackCardSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', false, 1);

        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('b', 'c'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('r', '9'));

        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card('r', '5'));

        gameOperations.savePlayerMove(
                playerDeck, stack, deck, "p-0-r", gameInfo);

        assertEquals(0, playerDeck.getHandDeck().size());
        assertEquals(2, stack.size());
        assertEquals('r', gameInfo.getDeclaratedColour());
    }

    @Test
    public void savePlayerMoveWhenHePutStopCardSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', false, 1);

        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('g', 's'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('r', '9'));

        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card('r', '5'));

        gameOperations.savePlayerMove(
                playerDeck, stack, deck, "p-0-r", gameInfo);

        assertEquals(0, playerDeck.getHandDeck().size());
        assertEquals(2, stack.size());
        assertEquals(true, gameInfo.isStopBattle());
    }

    @Test
    public void savePlayerMoveWhenHePutTwistCardSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', false, 1);

        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('g', 't'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('r', 't'));

        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card('r', '5'));

        gameOperations.savePlayerMove(
                playerDeck, stack, deck, "p-0-r", gameInfo);

        assertEquals(0, playerDeck.getHandDeck().size());
        assertEquals(2, stack.size());
        assertEquals(-1, gameInfo.getGameMove());
    }

    @Test
    public void savePlayerMoveWhenHePutNormalCardSuccessfull(){
        GameOperations gameOperations = new GameOperations();
        GameInfo gameInfo = new GameInfo(false, 0, 'r', false, 1);

        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.getHandDeck().add(new Card('r', '3'));

        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card('r', 't'));

        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card('r', '5'));

        gameOperations.savePlayerMove(
                playerDeck, stack, deck, "p-0-r", gameInfo);

        assertEquals(0, playerDeck.getHandDeck().size());
        assertEquals(2, stack.size());
    }
}

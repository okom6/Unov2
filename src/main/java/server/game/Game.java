package server.game;

import server.actors.Player;
import server.actors.Table;
import server.game.actors.*;

import java.util.ArrayList;

public class Game {
    public static void startGame(Table table){
        GameOperations gameOperations = new GameOperations();
        DeckFactory deckFactory = new DeckFactory();
        ArrayList<Card> deck = deckFactory.produceShuffledDeck();
        Croupier croupier = new Croupier();
        ArrayList<PlayerDeck> playerDeckArrayList = croupier.deal(table, deck);
        ArrayList<Card> stack = new ArrayList<>();
        gameOperations.addFirstCardToStack(stack, deck);
        GameInfo gameInfo = new GameInfo(
                false, 0, stack.get(0).getColour(), false, false, 1);

        gameOperations.sendGameStateToAllPlayers(playerDeckArrayList, stack, -1, gameInfo);

        int i = 0;
        while(!gameInfo.isEndGame()){

            if (deck.size() < 5){
                while(stack.size() > 1){
                    System.out.println("Jestem w pętli");
                    deck.add(stack.remove(0));
                }
            }

            PlayerDeck actualPlayerTurn = playerDeckArrayList.get(i % table.getMaxPlayers());

            if(!actualPlayerTurn.getPlayer().isActive()){
                continue;
            }

            System.out.println("Size 2:" + Integer.toString(stack.size()));
            System.out.println("Card on top 3:" + stack.get(stack.size() - 1).getColour() + "-" + stack.get(stack.size() - 1).getCharacter());

            gameOperations.sendGameStateToAllPlayers(playerDeckArrayList, stack, i % table.getMaxPlayers(), gameInfo);

            String response = actualPlayerTurn.getPlayer().getPlayerConnector().getInfoFromPlayer();

            while(!gameOperations.checkRules(actualPlayerTurn, stack, response, gameInfo)){
                actualPlayerTurn.getPlayer().getPlayerConnector().sendInfoToPlayer("wrong");
                response = actualPlayerTurn.getPlayer().getPlayerConnector().getInfoFromPlayer();
            }

            actualPlayerTurn.getPlayer().getPlayerConnector().sendInfoToPlayer("good");

            gameOperations.savePlayerMove(actualPlayerTurn, stack, deck, response, gameInfo);
            System.out.println("Card on top 1:" + stack.get(stack.size() - 1).getColour() + "-" + stack.get(stack.size() - 1).getCharacter());
            System.out.println("Size 1:" + Integer.toString(stack.size()));

            if(actualPlayerTurn.getHandDeck().size() == 0){
                gameInfo.setWinnerCounter(gameInfo.getWinnerCounter() + 1);
                actualPlayerTurn.getPlayer().setPlace(gameInfo.getWinnerCounter());
                actualPlayerTurn.getPlayer().setActive(false);
                System.out.println("Gracz wygrał!!!");
            }

            System.out.println("Po sprawddzeniu zwyciezcy:");

            i += gameInfo.getGameMove();

            gameInfo.setEndGame(
                    (gameOperations.checkNumberOfActivePlayers(playerDeckArrayList) > 1) ? false: true);
        }

        System.out.println("Po grze");

        gameOperations.sendEndGameToAllPlayers(playerDeckArrayList, gameInfo);

        for (PlayerDeck pd: playerDeckArrayList) {
            pd.getPlayer().getPlayerConnector().sendInfoToPlayer(Integer.toString(pd.getPlayer().getPlace()));
            pd.getPlayer().disconnect();
        }
    }
}

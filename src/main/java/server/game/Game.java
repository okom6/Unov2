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
        gameOperations.addFirstCardToStack(stack, deck); //dodać weryfikacje czy aby pierwsza karta nie jest czarna, jeśli tak to wybrac nową
        GameInfo gameInfo = new GameInfo(
                false, 0, stack.get(0).getColour(), false, 1);

        /*boolean endGame = false;
        int winnerCounter = 0;
        char declaratedColour = stack.get(0).getColour();
        boolean stopBattle = false;
        int gameMove = 1;*/

        //wysłać karty do wszystkich graczy i informacje i ilości kart przeciwników
        gameOperations.sendGameStateToAllPlayers(playerDeckArrayList, stack, -1, gameInfo);

        int i = 0;
        while(!gameInfo.isEndGame()){
            //jesli deck ma mniej niz 5 kart to zabrać karty ze stosu i dodać do decka
            if (deck.size() < 5){
                while(stack.size() > 1){
                    System.out.println("Jestem w pętli");
                    deck.add(stack.remove(0));
                }
            }

            PlayerDeck actualPlayerTurn = playerDeckArrayList.get(i % table.getMaxPlayers());

            //sprawdzić czy gracz jeszcze gra (bo mógł juz wygrać a jeszcze reszta gra)
            if(!actualPlayerTurn.getPlayer().isActive()){
                continue;
            }

            System.out.println("Size 2:" + Integer.toString(stack.size()));
            System.out.println("Card on top 3:" + stack.get(stack.size() - 1).getColour() + "-" + stack.get(stack.size() - 1).getCharacter());

            //wysłać info graczowi że jest jego tura
            //wysłać graczowi wszystkie jego karty, ilość kart innych, kartę na stosie, a innym graczom ilość kart innych, kartę na stosie
            gameOperations.sendGameStateToAllPlayers(playerDeckArrayList, stack, i % table.getMaxPlayers(), gameInfo);

            //odebrać info od gracza jaką wybrał kartę (lub czy dobrał) ([czynność-numer (karty lub dobrania)-rządanie koloru]
            String response = actualPlayerTurn.getPlayer().getPlayerConnector().getInfoFromPlayer();
            //int responseNumberumber = Integer.parseInt(response.split("-")[0]);

            //jeśli źle wybrał to ponowić proźbę o wybranie
            while(!gameOperations.checkRules(actualPlayerTurn, stack, response, gameInfo)){
                actualPlayerTurn.getPlayer().getPlayerConnector().sendInfoToPlayer("wrong");
                response = actualPlayerTurn.getPlayer().getPlayerConnector().getInfoFromPlayer();
            }

            //jeśli dobrał to sprawdzić kartę na stosie czy ma byc dobrane 1, 2 lub 4 karty
            //jeśli dobrał to sprawdzic czy dobrana karta pasuje i jeśli tak to położyć
            //obsluzyć zmiane koloru kartą specjalną

            //zweryfikować czy dobrze wybrał i przesłać odpowiedź
            actualPlayerTurn.getPlayer().getPlayerConnector().sendInfoToPlayer("good");

            //nie zapomnieć o zmianie statusu declaratedColour
            //stworzyć dobieranie oparte o wartości karty na górze stosu
            //obsługa walki o stop
            //jeśli dobrze wybrał to dodać kartę do stosu
            gameOperations.savePlayerMove(actualPlayerTurn, stack, deck, response, gameInfo);
            System.out.println("Card on top 1:" + stack.get(stack.size() - 1).getColour() + "-" + stack.get(stack.size() - 1).getCharacter());
            System.out.println("Size 1:" + Integer.toString(stack.size()));


            //sprawdzić czy gracz w tej chwili wygrał (jeśli wygrał to przydzielic mu miejce [pole place])
            if(actualPlayerTurn.getHandDeck().size() == 0){
                gameInfo.setWinnerCounter(gameInfo.getWinnerCounter() + 1);
                actualPlayerTurn.getPlayer().setPlace(gameInfo.getWinnerCounter());
                actualPlayerTurn.getPlayer().setActive(false);
                System.out.println("Gracz wygrał!!!");
            }

            System.out.println("Po sprawddzeniu zwyciezcy:");


            //zmiana gracza (i++) lub (i--)
            i += gameInfo.getGameMove();

            //jeśli gra już tylko 1 gracz to zakończ rozgrywkę
            gameInfo.setEndGame(
                    (gameOperations.checkNumberOfActivePlayers(playerDeckArrayList) > 1) ? false: true );
        }

        System.out.println("Po grze");

        gameOperations.sendEndGameToAllPlayers(playerDeckArrayList, gameInfo);

        for (PlayerDeck pd: playerDeckArrayList) {
            //pd.getPlayer().getPlayerConnector().sendInfoToPlayer("end");
            pd.getPlayer().getPlayerConnector().sendInfoToPlayer(Integer.toString(pd.getPlayer().getPlace()));
            pd.getPlayer().disconnect();
        }
    }
}

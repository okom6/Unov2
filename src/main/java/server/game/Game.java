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
        boolean endGame = false;
        int winnerCounter = 0;
        char declaratedColour = stack.get(0).getColour();
        boolean stopBattle = false;

        //wysłać karty do wszystkich graczy i informacje i ilości kart przeciwników
        gameOperations.sendGameStateToAllPlayers(playerDeckArrayList, table, stack, -1, declaratedColour, stopBattle);

        int i = 0;
        while(!endGame){
            //jesli deck ma mniej niz 5 kart to zabrać karty ze stosu i dodać do decka
            if (deck.size() < 5){
                while(stack.size() > 1){
                    deck.add(stack.remove(1));
                }
            }

            PlayerDeck actualPlayerTurn = playerDeckArrayList.get(i % table.getMaxPlayers());

            //sprawdzić czy gracz jeszcze gra (bo mógł juz wygrać a jeszcze reszta gra)
            if(!actualPlayerTurn.getPlayer().isActive()){
                continue;
            }

            //wysłać info graczowi że jest jego tura
            //wysłać graczowi wszystkie jego karty, ilość kart innych, kartę na stosie, a innym graczom ilość kart innych, kartę na stosie
            gameOperations.sendGameStateToAllPlayers(playerDeckArrayList, table, stack, i % table.getMaxPlayers(), declaratedColour, stopBattle);

            //odebrać info od gracza jaką wybrał kartę (lub czy dobrał) ([czynność-numer (karty lub dobrania)-rządanie koloru]
            String response = actualPlayerTurn.getPlayer().getPlayerConnector().getInfoFromPlayer();
            //int responseNumberumber = Integer.parseInt(response.split("-")[0]);

            //jeśli źle wybrał to ponowić proźbę o wybranie
            while(!gameOperations.checkRules(actualPlayerTurn, stack, response, declaratedColour, stopBattle)){
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
            //gameOperations.savePlayerChoose(actualPlayerTurn, stack, response, declaratedColour, stopBattle);

            //sprawdzić czy gracz w tej chwili wygrał (jeśli wygrał to przydzielic mu miejce [pole place])
            if(actualPlayerTurn.getHandDeck().size() == 1){
                ++winnerCounter;
                actualPlayerTurn.getPlayer().setPlace(winnerCounter);
                actualPlayerTurn.getPlayer().setActive(false);
            }

            //zmiana gracza (i++) lub (i--)
            i++;

            //jeśli gra już tylko 1 gracz to zakończ rozgrywkę
            endGame = gameOperations.checkNumberOfActivePlayers(playerDeckArrayList);
        }

        for (PlayerDeck pd: playerDeckArrayList) {
            pd.getPlayer().getPlayerConnector().sendInfoToPlayer("end");
            pd.getPlayer().getPlayerConnector().sendInfoToPlayer("Miejsce: " + Integer.toString(pd.getPlayer().getPlace()));
            pd.getPlayer().disconnect();
        }
    }
}

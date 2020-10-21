package server.game;

import server.actors.Player;
import server.actors.Table;
import server.game.actors.*;

import java.util.ArrayList;

public class Game {
    public static void startGame(Table table){
        DeckFactory deckFactory = new DeckFactory();
        ArrayList<Card> deck = deckFactory.produceShuffledDeck();
        Croupier croupier = new Croupier();
        ArrayList<PlayerDeck> playerDeckArrayList = croupier.deal(table, deck);
        ArrayList<Card> stack = new ArrayList<>();
        stack.add(deck.remove(0));
        boolean endGame = false;

        //wysłać karty do wszystkich graczy i informacje i ilości kart przeciwników
        PlayerGameStateToSend playerGameStateToSend = new PlayerGameStateToSend(stack.get(stack.size() - 1));
        for(int i = 0; i < playerDeckArrayList.size(); ++i){
            playerGameStateToSend.getPlayersCardNumbers().add(playerDeckArrayList.get(i).getHandDeck().size());
        }

        for(int i = 0; i < playerDeckArrayList.size(); ++i){
            playerGameStateToSend.setPlayerNumber(i + 1);
            playerGameStateToSend.setHandDeck(playerDeckArrayList.get(i).getHandDeck());
            playerDeckArrayList.get(i).getPlayer().getPlayerConnector().sendObjectToPlayer(playerGameStateToSend);
        }

        int i = 0;
        /*while(!endGame){
            //jesli deck ma mniej niz 5 kart to zabrać karty ze stosu i dodać do decka
            if (deck.size() < 5){
                while(stack.size() > 1){
                    deck.add(stack.remove(1));
                }
            }

            //sprawdzić czy gracz jeszcze gra (bo mógł juz wygrać a jeszcze reszta gra)
            if(playerDeckArrayList.get(i % table.getMaxPlayers()).getPlayer().getPlace() != -1){
                continue;
            }

            //wysłać info graczowi że jest jego tura

            //wysłać graczowi wszystkie jego karty, ilość kart innych, kartę na stosie, a innym graczom ilość kart innych, kartę na stosie

            //odebrać info od gracza jaką wybrał kartę (lub czy dobrał)

            //zweryfikować czy dobrze wybrał i przesłać odpowiedź

            //jeśli źle wybrał to ponowić proźbę o wybranie

            //jeśli dobrze wybrał to dodać kartę do stosu

            //sprawdzić czy gracz w tej chwili wygrał (jeśłi wygrał to przydzielic mu miejce [pole place])

            //zmiana gracza (i++) lub (i--)
            i++;

            //jeśli gra już tylko 1 gracz to zakończ rozgrywkę
        }*/

        for (PlayerDeck pd: playerDeckArrayList) {
            pd.getPlayer().getPlayerConnector().sendInfoToPlayer("Miejsce: " + Integer.toString(pd.getPlayer().getPlace()));
            pd.getPlayer().disconnect();
        }
    }
}

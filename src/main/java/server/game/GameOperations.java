package server.game;

import error.*;
import server.game.actors.Card;
import server.game.actors.PlayerDeck;
import server.game.actors.PlayerGameStateToSend;


import java.util.ArrayList;

public class GameOperations {
    public void addFirstCardToStack(ArrayList<Card> stack, ArrayList<Card> deck){
        for(int i = 0; i < deck.size(); i++){
            if(deck.get(i).getColour() != 's'){
                stack.add(deck.remove(i));
                return;
            }
        }
    }

    public void sendGameStateToAllPlayers(ArrayList<PlayerDeck> playerDeckArrayList, ArrayList<Card> stack, int playerTurn, GameInfo gameInfo){
        PlayerGameStateToSend playerGameStateToSend = new PlayerGameStateToSend(
                stack.get(stack.size() - 1), gameInfo.getDeclaratedColour(), gameInfo.isStopBattle(), gameInfo.isTakeBattle(), gameInfo.isEndGame());
        System.out.println("Card on top 2:" + stack.get(stack.size() - 1).getColour() + "-" + stack.get(stack.size() - 1).getCharacter());

        playerGameStateToSend.setPlayerTurn(playerTurn);

        for(int i = 0; i < playerDeckArrayList.size(); ++i){
            playerGameStateToSend.getPlayersCardNumbers().add(playerDeckArrayList.get(i).getHandDeck().size());
        }

        for(int i = 0; i < playerDeckArrayList.size(); ++i){
            playerGameStateToSend.setPlayerNumber(i);
            playerGameStateToSend.setHandDeck(playerDeckArrayList.get(i).getHandDeck());
            playerDeckArrayList.get(i).getPlayer().getPlayerConnector().sendObjectToPlayer(playerGameStateToSend);
        }
    }

    public void sendEndGameToAllPlayers(ArrayList<PlayerDeck> playerDeckArrayList, GameInfo gameInfo){
        PlayerGameStateToSend playerGameStateToSend = new PlayerGameStateToSend(
                null, gameInfo.getDeclaratedColour(), gameInfo.isStopBattle(), gameInfo.isTakeBattle(), gameInfo.isEndGame());

        for(int i = 0; i < playerDeckArrayList.size(); ++i){
            playerDeckArrayList.get(i).getPlayer().getPlayerConnector().sendObjectToPlayer(playerGameStateToSend);
        }
    }

    public int checkNumberOfActivePlayers(ArrayList<PlayerDeck> playerDeckArrayList){
        int counter = 0;

        for (PlayerDeck pd: playerDeckArrayList) {
            if(pd.getPlayer().getPlace() == -1){
                counter++;
            }
        }

        return counter;
        //return (counter > 1) ? false : true;
    }

    public ErrorCode checkRules(PlayerDeck actualPlayerTurn, ArrayList<Card> stack, String response, GameInfo gameInfo){
        char action = response.split("-")[0].charAt(0);
        int responseNumber = Integer.parseInt(response.split("-")[1]);
        char colourRequest = response.split("-")[2].charAt(0);

        //dobieranie
        //jeśli masz akcję walki o stop to nie możesz dobierać ale dobranie oznacza poddanie się w walce
        if(action == 't'){
            return ErrorCodeFactory.produceGoodMoveCode();
        }

        Card choosenCard = actualPlayerTurn.getHandDeck().get(responseNumber);
        Card cardOnTop = stack.get(stack.size() - 1);

        if(gameInfo.isStopBattle() && choosenCard.getCharacter() == 's'){
            return ErrorCodeFactory.produceGoodMoveCode();
        }

        if(gameInfo.isTakeBattle()){
            if(cardOnTop.getCharacter() == 'g'){
                if(choosenCard.getCharacter() == 'g' || choosenCard.getCharacter() == '4'){
                    if(choosenCard.getColour() == 's' && colourRequest == '*'){
                        return ErrorCodeFactory.produceNoChoosenColourErrorCode();
                    }
                    return ErrorCodeFactory.produceGoodMoveCode();
                }
            }
            if(cardOnTop.getCharacter() == '4' && choosenCard.getCharacter() == '4'){
                if(colourRequest == '*'){
                    return ErrorCodeFactory.produceNoChoosenColourErrorCode();
                }
                return ErrorCodeFactory.produceGoodMoveCode();
            }
        }

        //kładzenie
        if(cardOnTop.getColour() == 's'){
            if((gameInfo.getDeclaratedColour() == choosenCard.getColour())
                    || choosenCard.getColour() == 's'){
                if(choosenCard.getColour() == 's' && colourRequest == '*'){
                    return ErrorCodeFactory.produceNoChoosenColourErrorCode();
                }
                return ErrorCodeFactory.produceGoodMoveCode();
            }
        }

        //wybrano czarną kartę, a na stosie jest kolor
        if(choosenCard.getColour() == 's'){
            if(colourRequest == '*'){
                return ErrorCodeFactory.produceNoChoosenColourErrorCode();
            }
            return ErrorCodeFactory.produceGoodMoveCode();
        }
        //dodać obsługę "walki o stop"
        //masz w ręku kolor lub czarna kartę
        if(cardOnTop.getColour() == choosenCard.getColour() || cardOnTop.getCharacter() == choosenCard.getCharacter()){
            return ErrorCodeFactory.produceGoodMoveCode();
        }

        return ErrorCodeFactory.produceWrongCardErrorCode();
    }

    public void savePlayerMove(PlayerDeck actualPlayerTurn, ArrayList<Card> stack, ArrayList<Card> deck, String response, GameInfo gameInfo){
        char action = response.split("-")[0].charAt(0);
        int responseNumber = Integer.parseInt(response.split("-")[1]);
        char colourRequest = response.split("-")[2].charAt(0);
        Card cardOnTop = stack.get(stack.size() - 1);

        //jeśli dobrał to sprawdzić kartę na stosie czy ma byc dobrane 1, 2 lub 4 karty
        //obsluzyć zmiane koloru kartą specjalną
        //nie zapomnieć o zmianie statusu declaratedColour
        //stworzyć dobieranie oparte o wartości karty na górze stosu
        //obsługa walki o stop
        //jeśli dobrze wybrał to dodać kartę do stosu

        //dobieranie
        //jeśli masz akcję walki o stop to nie możesz dobierać ale dobranie oznacza poddanie się w walce
        if(action == 't'){
            if(gameInfo.isStopBattle()){
                gameInfo.setStopBattle(false);
            } else if(gameInfo.isTakeBattle() && cardOnTop.getColour() == 's' && cardOnTop.getCharacter() == '4'){
                takeCardsFromMainDeckToPlayer(actualPlayerTurn, deck, 4);
                gameInfo.setTakeBattle(false);
            } else if(gameInfo.isTakeBattle() && cardOnTop.getCharacter() == 'g'){
                takeCardsFromMainDeckToPlayer(actualPlayerTurn, deck, 2);
                gameInfo.setTakeBattle(false);
            } else{
                takeCardsFromMainDeckToPlayer(actualPlayerTurn, deck, 1);
            }
            return;
        }

        Card choosenCard = actualPlayerTurn.getHandDeck().remove(responseNumber);
        stack.add(choosenCard);

        if(choosenCard.getColour() == 's'){
            gameInfo.setDeclaratedColour(colourRequest);
            if(choosenCard.getCharacter() == '4'){
                gameInfo.setTakeBattle(true);
            }
        } else if(choosenCard.getCharacter() == 'g'){
            gameInfo.setTakeBattle(true);
        } else if(choosenCard.getCharacter() == 's'){
            gameInfo.setStopBattle(true);
        } else if(choosenCard.getCharacter() == 't'){
            gameInfo.setGameMove(gameInfo.getGameMove() * -1);
        }
    }

    public void takeCardsFromMainDeckToPlayer(PlayerDeck actualPlayerTurn, ArrayList<Card> deck, int cardNumbers){
        for(int i = 0; i < cardNumbers; ++i){
            actualPlayerTurn.getHandDeck().add(deck.remove(0));
        }
    }
}

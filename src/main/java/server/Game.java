package server;

import server.actors.Player;
import server.actors.Table;

public class Game {
    public static void startGame(Table table){
        for (Player p: table.getPlayers()) {
            p.getPlayerConnector().sendInfoToPlayer("gra rozpoczeta");
            p.disconnect();
        }
    }
}

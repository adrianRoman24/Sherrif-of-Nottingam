package com.tema1.main;
//import com.tema1.helpers.GameEngine;

import com.tema1.player.Bribed;
import com.tema1.player.Greedy;
import com.tema1.player.Player;
import java.util.ArrayList;;

/**Main class.*/
public final class Main {
    private Main() {
    }
    /**@param args is apram.*/
    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();

        //get cards ids
        ArrayList<Integer> deck = (ArrayList<Integer>) gameInput.getAssetIds();

        //get players
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < gameInput.getPlayerNames().size(); i++) {
            switch (gameInput.getPlayerNames().get(i)) {
                case "bribed":
                    players.add(new Bribed(i, "BRIBED"));
                    break;
                case "greedy":
                    players.add(new Greedy(i, "GREEDY"));
                    break;
                case "basic":
                    players.add(new Player(i, "BASIC"));
                    break;
                default:
                    break;
            }
        }

        //start game
        Game game = new Game(gameInput.getRounds(), players, deck);
        game.play();
        ArrayList<Integer> list = new ArrayList<>();

        //print final player list
        players.sort(Player::compareTo);
        for (Player player : players) {
            System.out.println(player.getIndex() + " "
                    + player.getType() + " " + player.getMoney());
        }
    }
}

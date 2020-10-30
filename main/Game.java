package com.tema1.main;

import com.tema1.common.Constants;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.LegalGoods;
import com.tema1.player.Player;
import java.util.ArrayList;
import java.util.Collections;

public final class Game {

    /**number of rounds per game.*/
    private int nrRounds;

    /**list of players to play the game.*/
    private ArrayList<Player> players;

    /**deck with cards representing all goods.*/
    private ArrayList<Integer> deck;

    /**
     *
     * @param newNrRounds is param
     * @param newPlayers is param
     * @param newDeck is param
     */
    public Game(final int newNrRounds, final ArrayList<Player> newPlayers,
                final ArrayList<Integer> newDeck) {
        this.nrRounds = newNrRounds;
        this.players = newPlayers;
        this.deck = newDeck;
    }


   private void addGoodsConfiscatedToDeck(
           final ArrayList<Integer> goodsConfiscated) {
        if (goodsConfiscated == null) {
            return;
        }

        while (goodsConfiscated.size() > 0) {
            deck.add(goodsConfiscated.get(0));
            goodsConfiscated.remove(0);
        }
    }

    /**Add king and queen bonus for each type of good.*/
    public void addKingAndQueenBonus() {
        for (Integer i : GoodsFactory.getInstance().getAllGoods().keySet()) {
            //if legal good
            if (i < Constants.getInstance().getMinIllegalGoodsIndex()) {
                int king = -1;
                int maxFrequency = 0;
                //select king
                for (Player player : players) {
                    if (Collections.frequency(player.
                            getBroughtGoods(), i) > maxFrequency) {
                        maxFrequency = Collections.
                                frequency(player.getBroughtGoods(), i);
                        king = player.getIndex();
                    }
                }

                //if found king
                if (king != -1) {
                    int kingBonus = ((LegalGoods) (GoodsFactory.
                            getInstance().getGoodsById(i))).getKingBonus();
                    players.get(king).setMoney(players.
                            get(king).getMoney() + kingBonus);

                    //select queen
                    int queen = -1;
                    maxFrequency = 0;
                    for (Player player : players) {
                        if (Collections.frequency(player.
                                getBroughtGoods(), i) > maxFrequency
                                && player.getIndex() != king) {
                            maxFrequency = Collections.
                                    frequency(player.getBroughtGoods(), i);
                            queen = player.getIndex();
                        }
                    }

                    //if found queen
                    if (queen != -1) {
                        int queenBonus = ((LegalGoods) (GoodsFactory.
                                getInstance().getGoodsById(i))).getQueenBonus();
                        players.get(queen).setMoney(players.
                                get(queen).getMoney() + queenBonus);
                    }
                }

                //add queen
            }
        }
    }

    private int left(final int position) {
        if (position == 0) {
            return players.size() - 1;
        } else {
            return position - 1;
        }
    }

    private int right(final int position) {
        if (position == players.size() - 1) {
            return 0;
        } else {
            return position + 1;
        }
    }
    /**Start game.*/
    public void play() {
        if (nrRounds > Constants.getInstance().getMaxNrRounds()) {
            nrRounds = Constants.getInstance().getMaxNrRounds();
        }

        ArrayList<Integer> goodsConfiscated = null;

        for (int currRound = 1; currRound <= nrRounds; currRound++) {
            //start sub round
            for (int i = 0; i < players.size(); i++) {
                //get the 10 goods
                for (int j = 0; j < players.size(); j++) {
                    if (j != i) {
                        while (players.get(j).
                                getGoodsInHand().size() < Constants.
                                getInstance().getMaxNrGoodsInHand()) {
                            players.get(j).getGoodsInHand().add(deck.get(0));
                            deck.remove(0);
                        }
                    }
                }
                //player i is the sheriff
                if (players.get(i).getType().equals("BRIBED")) {
                    players.get(left(i)).prepareBox(currRound);
                    goodsConfiscated = players.get(i).
                            playAsSheriff(players.get(left(i)));
                    addGoodsConfiscatedToDeck(goodsConfiscated);
                    if (left(i) != right(i)) {
                        players.get(right(i)).prepareBox(currRound);
                        goodsConfiscated = players.get(i).
                                playAsSheriff(players.get(right(i)));
                        addGoodsConfiscatedToDeck(goodsConfiscated);
                    }
                }
                for (int j = 0; j < players.size(); j++) {
                    if (j != i) {
                        //step 1 --- create box and
                        // throw away all the other cards
                        //+
                        //step 2 --- declare goods
                        if (players.get(i).getType().equals("BRIBED")) {
                            if (j != left(i) && j != right(i)) {
                                players.get(j).prepareBox(currRound);
                            }
                        } else {
                            players.get(j).prepareBox(currRound);
                        }
                        //step 3 --- player i checks player j
                        if (players.get(i).getType().equals("BRIBED")) {
                            if (j != left(i) && j != right(i)) {
                                players.get(i).acceptBribe(players.get(j));
                            }
                        } else {
                            goodsConfiscated = players.get(i).
                                    playAsSheriff(players.get(j));
                            addGoodsConfiscatedToDeck(goodsConfiscated);
                        }
                    }
                }

                //step 4 --- complete brought goods
                for (int j = 0; j < players.size(); j++) {
                    if (j != i) {
                        //add  sac to broughtGoods
                        players.get(j).addBoxToBroughtGoods();
                        //throw away unused cards
                        players.get(j).getGoodsInHand().clear();
                    }
                }
                //end sub round
            }

            //end round
        }

        //add Illegal bonus and add total profit in money
        for (Player player : players) {
            player.addIllegalBonusToBroughtGoods();
            player.calculateProfit();
        }
        addKingAndQueenBonus();
    }
}

package com.tema1.player;

import com.tema1.common.Constants;
import com.tema1.goods.GoodsFactory;
import java.util.ArrayList;
import java.util.Collections;

public class Greedy extends  Player {
    /**
     * @param index This is the type parameter
     * @param type The other parameter.
     */
    public Greedy(final int index, final String type) {
        super(index, type);
    }

    /**Prepare sac with greedy strategy.*/
    @Override
    public void prepareBox(final int currRound) {
        //base strategy
        super.prepareBox(currRound);
        if (currRound % 2 == 1) {
            return;
        }

        //case even round
        if (getBox().size() == Constants.getInstance().getBoxMaxSize()) {
            return;
        }

        //case odd round
        int maxProfit = 0;
        int illegalId = -1;
        //add one illegal good to maximize profit if possible
        for (int id:getGoodsInHand()) {
            if (id >= Constants.getInstance().
                    getMinIllegalGoodsIndex() && maxProfit
                    < GoodsFactory.getInstance().
                    getGoodsById(id).getProfit()) {
                //if good not already added to box
                if ((Collections.frequency(getGoodsInHand(), id) - Collections.
                        frequency(getBox(), id)) > 0) {
                    if (getBox().size() > 0) {
                        if (getBox().get(0) >= Constants.getInstance().
                                getMinIllegalGoodsIndex()) {
                            if (getMoney() > (GoodsFactory.getInstance().
                                    getGoodsById(getBox().get(0)).getPenalty()
                                    + GoodsFactory.getInstance().
                                    getGoodsById(id).getPenalty())) {
                                maxProfit = GoodsFactory.getInstance().
                                        getGoodsById(id).getProfit();
                                illegalId = id;
                            }
                        } else {
                            maxProfit = GoodsFactory.getInstance().
                                    getGoodsById(id).getProfit();
                            illegalId = id;
                        }
                    }
                }
            }
        }

        //add illegal good
        if (illegalId != -1) {
            getBox().add(illegalId);
            setLiar(true);
        }
    }

    /**@param checkedPlayer is param
     * @return .*/
    public ArrayList<Integer> playAsSheriff(final Player checkedPlayer) {
        if (checkedPlayer.getBribe() == 0) {
            //check player if bribe not offered
            return super.playAsSheriff(checkedPlayer);
        } else {
            acceptBribe(checkedPlayer);
            return null;
        }
    }
}

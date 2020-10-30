package com.tema1.player;

import com.tema1.common.Constants;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.IllegalGoods;
import java.util.ArrayList;
import java.util.Collections;

public class Bribed extends Player {
    /**
     * @param index is param
     * @param type is param
     */
    public Bribed(final int index, final String type) {
        super(index, type);
    }
    /**@return *
     * @param checkedPlayer */
    public ArrayList<Integer> playAsSheriff(final Player checkedPlayer) {
        return super.playAsSheriff(checkedPlayer);
    }

    /**@param currRound */
    public void prepareBox(final int currRound) {
        //case no money to pay bribe
        if (getMoney() < Constants.getInstance().getMinBribe()) {
            super.prepareBox(currRound);
            return;
        }

        int nrOfIllegalGoods = 0;
        for (int good:getGoodsInHand()) {
            if (good >= Constants.getInstance().getMinIllegalGoodsIndex()) {
                nrOfIllegalGoods++;
            }
        }

        //case no illegal goods in hand
        if (nrOfIllegalGoods == 0) {
            super.prepareBox(currRound);
            return;
        }

        //case money not enough to pay bribe and penalty
        if (getMoney() <= Constants.getInstance().getMinBribe()) {
            super.prepareBox(currRound);
            return;
        }

        getGoodsInHand().sort(Collections.reverseOrder());
        setLiar(true);

        //sort by profit and id all goods
        for (int i = 0; i < getGoodsInHand().size() - 1; i++) {
            for (int j = i + 1; j < getGoodsInHand().size(); j++) {
                if ((GoodsFactory.getInstance().
                        getGoodsById(getGoodsInHand().get(i)).getProfit()
                        < GoodsFactory.getInstance().
                        getGoodsById(getGoodsInHand().get(j)).getProfit())
                        || (((GoodsFactory.getInstance().
                        getGoodsById(getGoodsInHand().get(i)).getProfit()
                        == GoodsFactory.getInstance().
                        getGoodsById(getGoodsInHand().get(j)).getProfit())
                        && (GoodsFactory.getInstance().
                        getGoodsById(getGoodsInHand().get(i)).getId()
                        < GoodsFactory.getInstance().
                        getGoodsById(getGoodsInHand().get(j)).getId())))) {
                    int swap = getGoodsInHand().get(i);
                    getGoodsInHand().set(i, getGoodsInHand().get(j));
                    getGoodsInHand().set(j, swap);
                }
            }
        }
        //sort Illegal goods
        for (int i = 0; i < getGoodsInHand().size(); i++) {
            if (getGoodsInHand().get(i) >= Constants.getInstance()
                    .getMinIllegalGoodsIndex()) {
                for (int j = i + 1; j < getGoodsInHand().size(); j++) {
                    if (getGoodsInHand().get(j) >= Constants.getInstance().
                            getMinIllegalGoodsIndex()) {
                        if (((IllegalGoods) (GoodsFactory.getInstance().
                                getGoodsById(getGoodsInHand().
                                        get(j)))).totalProfit()
                                > ((IllegalGoods) (GoodsFactory.getInstance().
                                getGoodsById(getGoodsInHand().
                                        get(j)))).totalProfit()) {
                            int swap = getGoodsInHand().get(i);
                            getGoodsInHand().set(i, getGoodsInHand().get(j));
                            getGoodsInHand().set(j, swap);
                        }
                    }
                }
            }
        }

        setDeclaredGoods(0);
        int nrOfIllegalGoodsAdded = 0;
        int possiblePenalty = 0;
        setBribe(0);
        //add goods to the box if penalty and bribe can be paid
        for (int good:getGoodsInHand()) {
            if (getBox().size() == Constants.getInstance().getBoxMaxSize()) {
                return;
            }
            //case illegal good
            if (good >= Constants.getInstance().getMinIllegalGoodsIndex()) {
                //case bribe should be 5
                if (nrOfIllegalGoodsAdded < 2) {
                    possiblePenalty += GoodsFactory.getInstance().
                            getGoodsById(good).getPenalty();
                    if (possiblePenalty < getMoney()) {
                        getBox().add(good);
                        setBribe(Constants.getInstance().getMinBribe());
                        nrOfIllegalGoodsAdded++;
                    } else {
                        possiblePenalty -= GoodsFactory.getInstance().
                                getGoodsById(good).getPenalty();
                    }
                } else {
                    //case bribe should be 10
                    if (getMoney() > Constants.getInstance().getMaxBribe()) {
                        possiblePenalty += GoodsFactory.getInstance().
                                getGoodsById(good).getPenalty();
                        if (possiblePenalty < getMoney()) {
                            getBox().add(good);
                            setBribe(Constants.getInstance().getMaxBribe());
                            nrOfIllegalGoodsAdded++;
                        } else {
                            possiblePenalty -= GoodsFactory.getInstance().
                                    getGoodsById(good).getPenalty();
                        }
                    }
                }
            } else {
                //case legal good
                possiblePenalty += GoodsFactory.getInstance().
                        getGoodsById(good).getPenalty();
                if (possiblePenalty < getMoney()) {
                    getBox().add(good);
                } else {
                    return;
                }
            }

        }
    }
}

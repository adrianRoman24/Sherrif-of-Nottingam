package com.tema1.player;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.IllegalGoods;
import java.util.ArrayList;
import java.util.Collections;

public class Player {
    /**goods a player can use to play a sub round.*/
    private ArrayList<Integer> goodsInHand = new ArrayList<Integer>();

    /**initialize box.*/
    private ArrayList<Integer> box = new ArrayList<Integer>();

    /**initialize bunuri aduse.*/
    private ArrayList<Integer> broughtGoods = new ArrayList<Integer>();

    /**Player's index in players list.*/
    private final int index;
    /**declared goods.*/
    private int declaredGoods;
    /**money.*/
    private int money = Constants.getInstance().getStartMoney();
    /**bribe.*/
    private int bribe = 0;
    /**liar.*/
    private boolean liar = false;
    /**type or strategy.*/
    private String type;
    /**@param newIndex param
     @param newType is param.*/
    public Player(final int newIndex, final String newType) {
        this.index = newIndex;
        this.type = newType;
    }
    /**@return .*/
    public ArrayList<Integer> getGoodsInHand() {
        return goodsInHand;
    }
    /**@return .*/
    public ArrayList<Integer> getBox() {
        return box;
    }
    /**@return .*/
    public ArrayList<Integer> getBroughtGoods() {
        return broughtGoods;
    }
    /**@return .*/
    public int getIndex() {
        return index;
    }
    /**@return .*/
    public int getDeclaredGoods() {
        return declaredGoods;
    }
    /**@param newDeclaredGoods  is param.*/
    public void setDeclaredGoods(final int newDeclaredGoods) {
        this.declaredGoods = newDeclaredGoods;
    }
    /**@return .*/
    public int getMoney() {
        return money;
    }
    /**@param newMoney is param.*/
    public void setMoney(final int newMoney) {
        this.money = newMoney;
    }
    /**@return .*/
    public int getBribe() {
        return bribe;
    }
    /**@param newBribe is param.*/
    public void setBribe(final int newBribe) {
        this.bribe = newBribe;
    }
    /**@return .*/
    public boolean isLiar() {
        return liar;
    }
    /** @param newLiar is param.*/
    public void setLiar(final boolean newLiar) {
        this.liar = newLiar;
    }
    /**@return .*/
    public String getType() {
        return type;
    }
    /**@param player is param
     * @return .*/
    public int compareTo(final Player player) {
        return player.getMoney() - money;
    }
    /**If player not checked by sheriff.*/
    public void addBoxToBroughtGoods() {
        while (box.size() > 0) {
            broughtGoods.add(box.get(box.size() - 1));
            box.remove(box.size() - 1);
        }
    }
    /**@param otherPlayer is param.*/
    public void acceptBribe(final Player otherPlayer) {
        money += otherPlayer.getBribe();
        otherPlayer.setMoney(otherPlayer.getMoney() - otherPlayer.getBribe());
        otherPlayer.setBribe(0);
    }

    /** @param currRound is param.*/
    public void prepareBox(final int currRound) {
        //sort by id
        goodsInHand.sort(Collections.reverseOrder());
        liar = false;
        bribe = 0;
        //sort by frequency
        for (int i = 0; i < goodsInHand.size() - 1; i++) {
            for (int j = i + 1; j < goodsInHand.size(); j++) {
                if (Collections.frequency(goodsInHand, goodsInHand.get(j))
                        > Collections.
                        frequency(goodsInHand, goodsInHand.get(i))) {
                    int swap = goodsInHand.get(i);
                    goodsInHand.set(i, goodsInHand.get(j));
                    goodsInHand.set(j, swap);
                }
            }
        }

        int maxFrequency = 0;
        int maxProfit = 0;
        int good = -1;

        //get most frequent legal good profit
        for (int goodIndex :goodsInHand) {
            if (goodIndex < Constants.getInstance()
                    .getMinIllegalGoodsIndex()) {
                if (maxFrequency <= Collections.
                        frequency(goodsInHand, goodIndex)) {
                    maxFrequency = Collections.
                            frequency(goodsInHand, goodIndex);
                    if (maxProfit < GoodsFactory.
                            getInstance().getGoodsById(goodIndex).getProfit()) {
                        maxProfit = GoodsFactory.getInstance().
                                getGoodsById(goodIndex).getProfit();
                        good = goodIndex;
                    }
                }
            }
        }

        //case legal goods
        if (maxProfit > 0) {
            for (int nrGoods = 0; nrGoods < Collections.
                    frequency(goodsInHand, good); nrGoods++) {
                box.add(good);
            }
            declaredGoods = good;
            return;
        }

        //case with no legal goods
        declaredGoods = 0;
        maxProfit = 0;
        int illegalGood = -1;
        liar = true;

        for (int id:goodsInHand) {
            //get the good with the highest id and highest profit
            if (maxProfit < GoodsFactory.getInstance().
                    getGoodsById(id).getProfit()) {
                maxProfit = GoodsFactory.getInstance().
                        getGoodsById(id).getProfit();
                illegalGood = id;
            }
        }

       if (money >= GoodsFactory.getInstance().
               getGoodsById(illegalGood).getPenalty()) {
           box.add(illegalGood);
       }
    }

    //return taken goods
    /**@param checkedPlayer is param
     * @return .*/
    public ArrayList<Integer> playAsSheriff(final Player checkedPlayer) {
        //case basic with no money
        if (checkedPlayer.getBox().size() == 0) {
            return null;
        }
        //case no money left
        if (money < Constants.getInstance().getMinMoney()) {
            return null;
        }

        //case not liar
        if (!checkedPlayer.isLiar()) {
            int totalGoods = checkedPlayer.getBox().size();
            int penalty = GoodsFactory.getInstance().
                    getGoodsById(checkedPlayer.getDeclaredGoods()).getPenalty();
            int totalPenalty = totalGoods * penalty;
            money -= totalPenalty;
            checkedPlayer.setMoney(checkedPlayer.getMoney() + totalPenalty);
            return null;
        }
        //case liar
        ArrayList<Integer> goodsConfiscated = new ArrayList<>();
        for (int i = 0; i < checkedPlayer.getBox().size(); i++) {
            if (checkedPlayer.declaredGoods != checkedPlayer.getBox().get(i)) {
                setMoney(money
                        + GoodsFactory.getInstance().
                        getGoodsById(checkedPlayer.getBox().get(i))
                                .getPenalty());
                checkedPlayer.setMoney(checkedPlayer.getMoney()
                        - GoodsFactory.getInstance().getGoodsById(checkedPlayer.
                        getBox().get(i)).getPenalty());
                goodsConfiscated.add(checkedPlayer.getBox().get(i));
                checkedPlayer.getBox().remove(i);
                i--;
            }
        }
        return goodsConfiscated;
    }

    /**.*/
    public void addIllegalBonusToBroughtGoods() {
        if (broughtGoods == null) {
            return;
        }

        int initialSize = broughtGoods.size();
        for (int i = 0; i < initialSize; i++) {
            //found illegal good
            if (broughtGoods.get(i)
                    >= Constants.getInstance().getMinIllegalGoodsIndex()) {
                IllegalGoods goods = (IllegalGoods) GoodsFactory.
                        getInstance().getGoodsById(broughtGoods.get(i));
                //add all legal goods
                for (Goods good : goods.getIllegalBonus().keySet()) {
                    for (int nrBonus = 0; nrBonus < goods.
                            getIllegalBonus().get(good); nrBonus++) {
                        broughtGoods.add(good.getId());
                    }
                }
            }
        }
    }

    /**Add profit for brought goods.*/
    public void calculateProfit() {
        //add profit for each good
        for (int id : broughtGoods) {
            money += GoodsFactory.getInstance().getGoodsById(id).getProfit();
        }
    }
}

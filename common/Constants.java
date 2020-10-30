package com.tema1.common;

public final class Constants {
    /**instance.*/
    private static Constants instance = null;
    /**.*/
    private final int minIllegalGoodsIndex = 20;
    /**.*/
    private final int startMoney = 80;
    /**.*/
    private final int minMoney = 16;
    /**.*/
    private final int boxMaxSize = 8;
    /**.*/
    private final int minBribe = 5;
    /**.*/
    private final int maxBribe = 10;
    /**.*/
    private final int maxNrRounds = 5;
    /**.*/
    private final int maxNrGoodsInHand = 10;
    /** @return .*/
    public int getMaxNrGoodsInHand() {
        return maxNrGoodsInHand;
    }
    /** @return .*/
    public int getMaxBribe() {
        return maxBribe;
    }
    /** @return .*/
    public int getMaxNrRounds() {
        return maxNrRounds;
    }
    /** @return .*/
    public int getMinBribe() {
        return minBribe;
    }
    /** @return .*/
    public int getBoxMaxSize() {
        return boxMaxSize;
    }
    /** @return .*/
    public int getMinIllegalGoodsIndex() {
        return minIllegalGoodsIndex;
    }
    /** @return .*/
    public int getStartMoney() {
        return startMoney;
    }

    /** @return .*/
    public int getMinMoney() {
        return minMoney;
    }

    /** @return .*/
    public static Constants getInstance() {
        if (instance == null) {
            instance = new Constants();
        }
        return instance;
    }
}

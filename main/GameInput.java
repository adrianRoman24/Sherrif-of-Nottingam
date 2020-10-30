package com.tema1.main;

import java.util.List;

public class GameInput {
    // DO NOT MODIFY
    /**.*/
    private final List<Integer> mAssetOrder;
    /**.*/
    private final List<String> mPlayersOrder;
    /**.*/
    private int mRounds;

    GameInput() {
        mAssetOrder = null;
        mPlayersOrder = null;
        mRounds = -1;
    }

    /**
     * @param rounds is param
     * @param assets is param
     * @param players is param
     */
    public GameInput(final int rounds,
                     final List<Integer> assets, final List<String> players) {
        mAssetOrder = assets;
        mPlayersOrder = players;
        mRounds = rounds;
    }

    /**@return .*/
    public final List<Integer> getAssetIds() {
        return mAssetOrder;
    }

    /**@return .*/
    public final List<String> getPlayerNames() {
        return mPlayersOrder;
    }

    /**@return .*/
    public final int getRounds() {
        return mRounds;
    }

    /**@return .*/
    public final boolean isValidInput() {
        boolean membersInstantiated = mAssetOrder
                != null && mPlayersOrder != null;
        boolean membersNotEmpty = mAssetOrder.size() > 0
                && mPlayersOrder.size() > 0 && mRounds > 0;

        return membersInstantiated && membersNotEmpty;
    }
}

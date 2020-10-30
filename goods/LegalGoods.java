package com.tema1.goods;

public class LegalGoods extends Goods {
	/**.*/
    private final int kingBonus;
	/**.*/
    private final int queenBonus;

	/**
	 * @param id is param
	 * @param profit is param
	 * @param penalty is param
	 * @param kingBonus1 is param
	 * @param queenBonus1 is param
	 */
    public LegalGoods(
    		final int id,
			final int profit,
			final int penalty,
			final int kingBonus1,
			final int queenBonus1) {
		super(id, GoodsType.Legal, profit, penalty);
		this.kingBonus = kingBonus1;
		this.queenBonus = queenBonus1;
	}

	/** @return .*/
    public int getKingBonus() {
		return kingBonus;
	}

	/** @return .*/
    public int getQueenBonus() {
		return queenBonus;
	}
}

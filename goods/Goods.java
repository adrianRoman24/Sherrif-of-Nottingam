package com.tema1.goods;

public abstract class Goods {
	/**.*/
    private final int id;
	/**.*/
    private final GoodsType type;
	/**.*/
    private final int profit;
	/**.*/
    private final int penalty;

	/**
	 *
	 * @param newId is param
	 * @param newType is param
	 * @param newProfit is param
	 * @param newPenalty is param
	 */
	public Goods(final int newId, final GoodsType newType,
				 final int newProfit, final int newPenalty) {
		this.id = newId;
		this.type = newType;
		this.profit = newProfit;
		this.penalty = newPenalty;
	}

	/** @return .*/
    public final int getId() {
		return id;
	}

	/**@return .*/
    public final GoodsType getType() {
		return type;
	}
	/**@return .*/
    public final int getProfit() {
		return profit;
	}
	/**@return .*/
    public final int getPenalty() {
		return penalty;
	}
}

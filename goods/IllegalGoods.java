package com.tema1.goods;

import java.util.Map;

public class IllegalGoods extends Goods {
	/**.*/
    private final Map<Goods, Integer> illegalBonus;

	/**
	 * @param id is param
	 * @param profit is param
	 * @param penalty is param
	 * @param illegalBonus is param
	 */
    public IllegalGoods(final int id,
						final int profit,
						final int penalty,
						final Map<Goods,
								Integer> illegalBonus) {
		super(id, GoodsType.Illegal, profit, penalty);
		this.illegalBonus = illegalBonus;
	}

	/**@return .*/
    public Map<Goods, Integer> getIllegalBonus() {
		return illegalBonus;
	}

	/**@return .*/
    public int totalProfit() {
		int totalProfit = getProfit();
		IllegalGoods goods = (IllegalGoods) GoodsFactory.
				getInstance().getGoodsById(getId());
		//add all legal goods
		for (Goods good : goods.getIllegalBonus().keySet()) {
			totalProfit += goods.getIllegalBonus().
					get(good) * good.getProfit();
		}
		return totalProfit;
	}

}

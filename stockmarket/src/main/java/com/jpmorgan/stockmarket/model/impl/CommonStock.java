package com.jpmorgan.stockmarket.model.impl;

import com.jpmorgan.stockmarket.model.Stock;

/**
 * Implementation of the common stock
 * 
 * @author Chris Gibson
 *
 */
public class CommonStock extends AbstractStock implements Stock {

	public CommonStock(String stockSymbol, long lastDividend, long parValue) {
		super(stockSymbol, lastDividend, parValue);
	}

	/**
	 * Calculates the common dividend yield
	 */
	@Override
	public double calculateDividendYield(long stockPrice) {
		return stockCalculator.calculateCommonDividendYield(lastDividend, stockPrice);
	}

	@Override
	public String toString() {
		return "CommonStock [stockSymbol: " + getStockSymbol() + ", lastDividend: " + lastDividend + ", parValue: "
				+ parValue + "]";
	}
}

package com.jpmorgan.stockmarket.model.impl;

import com.jpmorgan.stockmarket.model.Stock;

/**
 * Implementation of the preferred stock
 * 
 * @author Chris Gibson
 *
 */
public class PreferredStock extends AbstractStock implements Stock {

	private double fixedDividend;

	public PreferredStock(String stockSymbol, long lastDividend, double fixedDividend, long parValue) {
		super(stockSymbol, lastDividend, parValue);
		this.fixedDividend = fixedDividend;
	}

	/**
	 * @return the fixedDividend
	 */
	public double getFixedDividend() {
		return fixedDividend;
	}

	/**
	 * @param fixedDividend
	 *            the fixedDividend to set
	 */
	public void setFixedDividend(double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	/**
	 * Calculates the preferred dividend yield
	 */
	@Override
	public double calculateDividendYield(long stockPrice) {
		return stockCalculator.calculatePreferredDividendYield(fixedDividend, parValue, stockPrice);
	}

	@Override
	public String toString() {
		return "PreferredStock [" + "stockSymbol: " + getStockSymbol() + " lastDividend: " + lastDividend
				+ ", fixedDividend: " + fixedDividend + " parValue: " + parValue + "]";
	}
}

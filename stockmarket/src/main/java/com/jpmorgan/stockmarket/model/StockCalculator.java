package com.jpmorgan.stockmarket.model;

import java.util.TreeMap;

import org.joda.time.DateTime;

public interface StockCalculator {

	/**
	 * Calculates a common stocks dividend yield based on its last dividend /
	 * stock price
	 * 
	 * @param lastDividend
	 *            - The stocks last dividend
	 * @param stockPrice
	 *            - The stocks price
	 * @return double - The stocks dividend yield
	 */
	public double calculateCommonDividendYield(double lastDividend, double stockPrice);

	/**
	 * Calculates a preferred stocks dividend yield based on fixed dividend x
	 * par value / stock price
	 * 
	 * @param fixedDividendPercentage
	 *            - The stocks fixed dividend percentage
	 * @param parValue
	 *            - The stocks par value
	 * @param stockPrice
	 *            - The stocks price
	 * @return double - The stocks dividend yield
	 */
	public double calculatePreferredDividendYield(double fixedDividendPercentage, long parValue, long stockPrice);

	/**
	 * Calculate a stocks P/E value based on stock price / last dividend
	 * 
	 * @param stockPrice
	 *            - The stocks price
	 * @param dividend
	 *            - The stocks last dividend value
	 * @return -double - The stocks P/E value
	 */
	public double calculatePeRatio(long stockPrice, long dividend);

	/**
	 * Calculates the volume weighted stock price based on the the (sum of
	 * tradedPricei x quantityi) / sum of quantity
	 * 
	 * @param stockTrade
	 *            - List of all trades for a stock
	 * @param fromDateTime
	 *            - The timestamp which the will limit the trade timestamp
	 * @return long - Volume weighted stock price
	 */
	public long calculateVolumeWeightedStockPrice(TreeMap<DateTime, StockTrade> stockTrade, DateTime fromDateTime);
}

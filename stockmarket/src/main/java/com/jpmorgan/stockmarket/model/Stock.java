package com.jpmorgan.stockmarket.model;

import java.util.TreeMap;

import org.joda.time.DateTime;

import com.jpmorgan.stockmarket.model.impl.BuyOrSellEnum;

public interface Stock {

	public String getStockSymbol();

	public long getLastDividend();

	public void setLastDividend(long lastDividend);

	public long getParValue();

	public void setParValue(long parValue);

	public double calculatePeRatio(long price);

	/**
	 * Calculates the volume weighted stock price of all stock trades recorded
	 * after the given datetime
	 * 
	 * @param fromDateTime
	 *            - Calculation start time
	 * @return - Volume weighted stock price
	 */
	public long calculateVolumeWeightedStockPrice(DateTime fromDateTime);

	/**
	 * Calculates the dividend yield for the given stock price
	 * 
	 * @param stockPrice
	 *            - Price of the stock
	 * @return - Dividend yield
	 */
	public double calculateDividendYield(long stockPrice);

	/**
	 * @return - A Map of Stock Trades mapped by their timestamps
	 */
	public TreeMap<DateTime, StockTrade> getRecordedTrades();

	/**
	 * Records a trade of shares
	 * 
	 * @param numberOfSharesToSell
	 *            - Number of stocks to sell
	 * @param tradedPrice
	 *            - Price of the stock to trade
	 * @param buyOrSell
	 *            - Indicator of a buy or sell trade
	 */
	public StockTrade recordStockTrade(long numberOfSharesToSell, long tradedPrice, BuyOrSellEnum buyOrSell);
}

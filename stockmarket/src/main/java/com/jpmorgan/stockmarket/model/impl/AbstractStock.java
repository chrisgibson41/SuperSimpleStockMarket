package com.jpmorgan.stockmarket.model.impl;

import java.util.TreeMap;

import org.joda.time.DateTime;

import com.jpmorgan.stockmarket.model.StockTrade;

/**
 * Abstract stock base class.
 * 
 * @author Chris Gibson
 *
 */
public abstract class AbstractStock {

	private final String stockSymbol;
	protected long lastDividend;
	protected long parValue;
	protected final StockCalculatorImpl stockCalculator;
	private TreeMap<DateTime, StockTrade> recordedStockTrades;

	public AbstractStock(String stockSymbol, long lastDividend, long parValue) {
		this.stockSymbol = stockSymbol;
		this.lastDividend = lastDividend;
		this.parValue = parValue;
		this.stockCalculator = new StockCalculatorImpl();
		this.recordedStockTrades = new TreeMap<DateTime, StockTrade>();
	}

	/**
	 * @return the stockSymbol
	 */
	public String getStockSymbol() {
		return stockSymbol;
	}

	/**
	 * @return the lastDividend
	 */
	public long getLastDividend() {
		return lastDividend;
	}

	/**
	 * @param lastDividend
	 *            the lastDividend to set
	 */
	public void setLastDividend(long lastDividend) {
		this.lastDividend = lastDividend;
	}

	/**
	 * @return the parValue
	 */
	public long getParValue() {
		return parValue;
	}

	/**
	 * @param parValue
	 *            the parValue to set
	 */
	public void setParValue(long parValue) {
		this.parValue = parValue;
	}

	public StockTrade recordStockTrade(long numberOfSharesToSell, long tradedPrice, BuyOrSellEnum buyOrSell) {
		DateTime tradeTimestamp = new DateTime();
		StockTrade trade = new StockTradeImpl(numberOfSharesToSell, tradedPrice, tradeTimestamp, buyOrSell);
		recordedStockTrades.put(tradeTimestamp, trade);
		return trade;
	}

	public TreeMap<DateTime, StockTrade> getRecordedTrades() {
		return recordedStockTrades;
	}

	public double calculatePeRatio(long stockPrice) {
		return stockCalculator.calculatePeRatio(stockPrice, lastDividend);
	}

	public long calculateVolumeWeightedStockPrice(DateTime fromDateTime) {
		return stockCalculator.calculateVolumeWeightedStockPrice(recordedStockTrades, fromDateTime);
	}
}

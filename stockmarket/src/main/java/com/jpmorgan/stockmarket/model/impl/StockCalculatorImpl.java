package com.jpmorgan.stockmarket.model.impl;

import java.util.SortedMap;
import java.util.TreeMap;

import org.joda.time.DateTime;

import com.jpmorgan.stockmarket.model.StockCalculator;
import com.jpmorgan.stockmarket.model.StockTrade;

public class StockCalculatorImpl implements StockCalculator {

	@Override
	public double calculateCommonDividendYield(double lastDividend, double stockPrice) {
		double dividendYield = 0.0d;

		if (stockPrice != 0) {
			dividendYield = lastDividend / stockPrice;
		}
		return dividendYield;
	}

	@Override
	public double calculatePreferredDividendYield(double fixedDividendPercentage, long parValue, long stockPrice) {
		double dividendYield = 0.0d;

		if (stockPrice != 0) {
			dividendYield = (fixedDividendPercentage * parValue) / stockPrice;
		}
		return dividendYield;
	}

	@Override
	public double calculatePeRatio(long stockPrice, long dividend) {
		double peRatio = 0.0d;

		if (dividend != 0) {
			peRatio = stockPrice / dividend;
		}
		return peRatio;
	}

	@Override
	public long calculateVolumeWeightedStockPrice(TreeMap<DateTime, StockTrade> stockTrade, DateTime fromDateTime) {
		SortedMap<DateTime, StockTrade> stockTrades = stockTrade.tailMap(fromDateTime);

		long aggregateTradedStockPrice = 0;
		long aggregateStockQuantity = 0;
		for (StockTrade trade : stockTrades.values()) {
			aggregateStockQuantity += trade.getQuantityOfShares();
			aggregateTradedStockPrice += trade.getTradedPrice() * trade.getQuantityOfShares();
		}

		long volumeWeightedStockPrice = 0;
		if (aggregateStockQuantity != 0) {
			volumeWeightedStockPrice = aggregateTradedStockPrice / aggregateStockQuantity;
		}
		return volumeWeightedStockPrice;
	}
}

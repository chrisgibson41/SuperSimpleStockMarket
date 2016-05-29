package com.jpmorgan.stockmarket.model;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMockSupport;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.junit.Test;

import com.jpmorgan.stockmarket.model.impl.BuyOrSellEnum;
import com.jpmorgan.stockmarket.model.impl.CommonStock;
import com.jpmorgan.stockmarket.model.impl.PreferredStock;
import com.jpmorgan.stockmarket.model.impl.StockTradeImpl;

public class StockTest extends EasyMockSupport {

	@Test
	public void calculateCommonDividendYieldTest() {
		// Setup
		String stockSymbol = "POP";
		long lastDividend = 8;
		long parValue = 100;
		Stock stock = new CommonStock(stockSymbol, lastDividend, parValue);
		long stockPrice = 16;

		// Test
		double result = stock.calculateDividendYield(stockPrice);

		System.out.println(result);
		// Verify
		assertEquals(0.5, result, 0);
	}

	@Test
	public void calculateCommonDividendYieldTest_ZeroDivisor() {
		// Setup
		String stockSymbol = "POP";
		long lastDividend = 8;
		long parValue = 100;
		Stock stock = new CommonStock(stockSymbol, lastDividend, parValue);
		long stockPrice = 0;

		// Test
		double result = stock.calculateDividendYield(stockPrice);

		// Verify
		assertEquals(0, result, 0);
	}

	@Test
	public void calculatePreferredDividendYieldTest() {
		// Setup
		String stockSymbol = "GIN";
		long lastDividend = 8;
		double fixedDividend = 0.02;
		long parValue = 100;
		Stock stock = new PreferredStock(stockSymbol, lastDividend, fixedDividend, parValue);
		long stockPrice = 4;

		// Test
		stock.calculateDividendYield(stockPrice);

		// Test
		double result = stock.calculateDividendYield(stockPrice);

		// Verify
		assertEquals(0.5, result, 0);
	}

	@Test
	public void calculatePreferredDividendYieldTest_ZeroDivisor() {
		// Setup
		String stockSymbol = "GIN";
		long lastDividend = 8;
		double fixedDividend = 0.02;
		long parValue = 100;
		Stock stock = new PreferredStock(stockSymbol, lastDividend, fixedDividend, parValue);
		long stockPrice = 0;

		// Test
		double result = stock.calculateDividendYield(stockPrice);

		// Verify
		assertEquals(0, result, 0);
	}

	@Test
	public void calculatePeRatioTest() {
		// Setup
		long lastDividend = 8;
		double fixedDividend = 0.02;
		long parValue = 100;

		Stock preferredStock = new PreferredStock("GIN", lastDividend, fixedDividend, parValue);
		Stock commonStock = new CommonStock("POP", lastDividend, parValue);
		long stockPrice = 16;

		// Test
		double preferreStockResult = preferredStock.calculatePeRatio(stockPrice);
		double commonStockResult = commonStock.calculatePeRatio(stockPrice);

		// Verify
		assertEquals(2, preferreStockResult, 0);
		assertEquals(2, commonStockResult, 0);
	}

	@Test
	public void calculatePeRatio_ZeroDivisor() {
		// Setup
		long lastDividend = 0; // Zero divisor
		double fixedDividend = 0.02;
		long parValue = 100;

		Stock preferredStock = new PreferredStock("GIN", lastDividend, fixedDividend, parValue);
		Stock commonStock = new CommonStock("POP", lastDividend, parValue);
		long stockPrice = 16;

		// Test
		double preferreStockResult = preferredStock.calculatePeRatio(stockPrice);
		double commonStockResult = commonStock.calculatePeRatio(stockPrice);

		// Verify
		assertEquals(0, preferreStockResult, 0);
		assertEquals(0, commonStockResult, 0);
	}

	@Test
	public void calculateVolumeWeightedStockPriceTest() {
		// Setup
		long lastDividend = 0;
		long parValue = 100;

		Set<Stock> availableStocks = new HashSet<Stock>();
		Stock teaStockstock = new CommonStock("POP", lastDividend, parValue);

		// Record some trades for this stock
		DateTime timestamp = new DateTime();
		teaStockstock.getRecordedTrades().put(timestamp, new StockTradeImpl(100, 204, timestamp, BuyOrSellEnum.BUY));
		timestamp = timestamp.minus(Minutes.ONE);
		teaStockstock.getRecordedTrades().put(timestamp, new StockTradeImpl(34, 255, timestamp, BuyOrSellEnum.BUY));
		timestamp = timestamp.minus(Minutes.TWO);
		teaStockstock.getRecordedTrades().put(timestamp, new StockTradeImpl(12, 91, timestamp, BuyOrSellEnum.SELL));

		availableStocks.add(teaStockstock);

		// Test
		DateTime timeFrom = new DateTime().minusMinutes(15);
		long result = teaStockstock.calculateVolumeWeightedStockPrice(timeFrom);

		// Verify
		assertEquals(206, result);
	}

	@Test
	public void calculateVolumeWeightedStockPriceTest_TradesOutwithTimeRange() {
		// Setup
		long lastDividend = 0;
		long parValue = 100;

		Set<Stock> availableStocks = new HashSet<Stock>();
		Stock teaStockstock = new CommonStock("POP", lastDividend, parValue);

		// Record some trades for this stock
		DateTime timestamp = new DateTime();
		timestamp = timestamp.minus(Days.ONE);
		teaStockstock.getRecordedTrades().put(timestamp, new StockTradeImpl(100, 204, timestamp, BuyOrSellEnum.BUY));
		timestamp = timestamp.minusMinutes(16);
		teaStockstock.getRecordedTrades().put(timestamp, new StockTradeImpl(34, 255, timestamp, BuyOrSellEnum.BUY));
		timestamp = timestamp.minus(Hours.ONE);
		teaStockstock.getRecordedTrades().put(timestamp, new StockTradeImpl(12, 91, timestamp, BuyOrSellEnum.SELL));

		availableStocks.add(teaStockstock);

		// Test
		DateTime timeFrom = new DateTime().minusMinutes(15);
		long result = teaStockstock.calculateVolumeWeightedStockPrice(timeFrom);

		// Verify
		assertEquals(0, result);
	}

	@Test
	public void calculateVolumeWeightedStockPriceTest_EmptyTrades() {
		// Setup
		String stockSymbol = "GIN";
		long lastDividend = 8;
		double fixedDividend = 0.02;
		long parValue = 100;
		Stock stock = new PreferredStock(stockSymbol, lastDividend, fixedDividend, parValue);

		DateTime startDateTime = new DateTime();

		// Test
		long volumeWeightedStockPrice = stock.calculateVolumeWeightedStockPrice(startDateTime);

		// Verify
		assertEquals(0, volumeWeightedStockPrice);
	}
}

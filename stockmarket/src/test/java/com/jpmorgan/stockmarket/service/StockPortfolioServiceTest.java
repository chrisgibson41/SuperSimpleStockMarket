package com.jpmorgan.stockmarket.service;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.stockmarket.dao.StockDao;
import com.jpmorgan.stockmarket.model.Stock;
import com.jpmorgan.stockmarket.model.impl.BuyOrSellEnum;
import com.jpmorgan.stockmarket.model.impl.CommonStock;
import com.jpmorgan.stockmarket.model.impl.PreferredStock;
import com.jpmorgan.stockmarket.model.impl.StockTradeImpl;
import com.jpmorgan.stockmarket.service.impl.StockPortfolioServiceImpl;

public class StockPortfolioServiceTest extends EasyMockSupport {

	private StockPortfolioService stockPortfolioService;
	private StockDao stockDao;

	@Before
	public void setup() {
		stockDao = createMock(StockDao.class);
		stockPortfolioService = new StockPortfolioServiceImpl(stockDao);
	}

	@Test
	public void addStockToPortfolioTest() {
		Stock stock = new CommonStock("POP", 10, 10);

		@SuppressWarnings("deprecation")
		Capture<Stock> capturedStock = new Capture<Stock>();
		stockDao.addStockToPortfolio(EasyMock.capture(capturedStock));
		EasyMock.expectLastCall();

		EasyMock.replay(stockDao);

		// Test
		stockPortfolioService.addStockToPortfolio(stock);

		// Verify
		Stock savedStock = capturedStock.getValue();

		assertEquals(stock, savedStock);

		EasyMock.verify(stockDao);
	}

	@Test
	public void calculateAllShareIndexTest_MultipleStocks() {
		// Setup
		long lastDividend = 0;
		long parValue = 100;

		Set<Stock> availableStocks = new HashSet<Stock>();
		Stock teaStockstock = new CommonStock("POP", lastDividend, parValue);
		Stock popStock = new CommonStock("POP", lastDividend, parValue);

		// Record some trades for this stock
		DateTime timestamp = new DateTime();
		teaStockstock.getRecordedTrades().put(timestamp, new StockTradeImpl(100, 204, timestamp, BuyOrSellEnum.BUY));
		timestamp = timestamp.minus(Hours.ONE);
		teaStockstock.getRecordedTrades().put(timestamp, new StockTradeImpl(34, 255, timestamp, BuyOrSellEnum.BUY));
		timestamp = timestamp.minus(Hours.TWO);
		popStock.getRecordedTrades().put(timestamp, new StockTradeImpl(12, 91, timestamp, BuyOrSellEnum.BUY));
		timestamp = timestamp.minus(Hours.THREE);
		popStock.getRecordedTrades().put(timestamp, new StockTradeImpl(1000, 24, timestamp, BuyOrSellEnum.BUY));

		availableStocks.add(popStock);
		availableStocks.add(teaStockstock);

		EasyMock.expect(stockDao.getAllAvailableStocks()).andReturn(availableStocks);
		EasyMock.replay(stockDao);

		// Test
		double result = stockPortfolioService.calculateGbceAllShareIndex();

		// Assert
		assertEquals(15.255402591824106, result, 0);

		// Verify
		EasyMock.verify(stockDao);
	}

	@Test
	public void calculateAllShareIndexTest_SingleStock() {
		// Setup
		long lastDividend = 0;
		long parValue = 100;

		Set<Stock> availableStocks = new HashSet<Stock>();
		Stock stock = new CommonStock("POP", lastDividend, parValue);

		// Record some trades for this stock
		DateTime currentTime = new DateTime();
		stock.getRecordedTrades().put(currentTime, new StockTradeImpl(100, 204, new DateTime(), BuyOrSellEnum.BUY));
		stock.getRecordedTrades().put(currentTime.minus(Hours.ONE),
				new StockTradeImpl(34, 255, currentTime.minus(Hours.TWO), BuyOrSellEnum.BUY));
		stock.getRecordedTrades().put(currentTime.minus(Hours.THREE),
				new StockTradeImpl(12, 91, currentTime.minus(Minutes.TWO), BuyOrSellEnum.BUY));
		stock.getRecordedTrades().put(currentTime.minus(Minutes.THREE),
				new StockTradeImpl(1000, 24, currentTime.minus(Days.ONE), BuyOrSellEnum.BUY));
		availableStocks.add(stock);

		EasyMock.expect(stockDao.getAllAvailableStocks()).andReturn(availableStocks);
		EasyMock.replay(stockDao);

		// Test
		double result = stockPortfolioService.calculateGbceAllShareIndex();

		// Assert
		assertEquals(15.255402591824106, result, 0);

		// Verify
		EasyMock.verify(stockDao);
	}

	@Test
	public void calculateAllShareIndexTest_NoStocks() {
		// Setup
		Set<Stock> availableStocks = new HashSet<Stock>();
		EasyMock.expect(stockDao.getAllAvailableStocks()).andReturn(availableStocks);
		EasyMock.replay(stockDao);

		// Test
		double result = stockPortfolioService.calculateGbceAllShareIndex();

		// Assert
		assertEquals(0d, result, 0);

		// Verify
		EasyMock.verify(stockDao);
	}

	@Test
	public void calculateAllShareIndexTest_NoTradesForStock() {
		// Setup
		long lastDividend = 0;
		double fixedDividend = 0.02;
		long parValue = 100;

		Set<Stock> availableStocks = new HashSet<Stock>();
		availableStocks.add(new PreferredStock("GIN", lastDividend, fixedDividend, parValue));
		availableStocks.add(new CommonStock("POP", lastDividend, parValue));

		EasyMock.expect(stockDao.getAllAvailableStocks()).andReturn(availableStocks);
		EasyMock.replay(stockDao);

		// Test
		double result = stockPortfolioService.calculateGbceAllShareIndex();

		// Assert
		assertEquals(0, result, 0);

		// Verify
		EasyMock.verify(stockDao);
	}
}

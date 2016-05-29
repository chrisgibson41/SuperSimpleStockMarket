package com.jpmorgan.stockmarket.service.impl;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpmorgan.stockmarket.dao.StockDao;
import com.jpmorgan.stockmarket.model.Stock;
import com.jpmorgan.stockmarket.model.StockTrade;
import com.jpmorgan.stockmarket.service.StockPortfolioService;

/**
 * Stock trading service is responsible for the functions relating to the stock
 * portfolios individual stocks and their relating trades
 * 
 * @author Chris
 *
 */
@Service("stockTradingService")
public class StockPortfolioServiceImpl implements StockPortfolioService {

	private StockDao stockDao;

	@Autowired
	public StockPortfolioServiceImpl(StockDao stockDao) {
		this.stockDao = stockDao;
	}

	@Override
	public void addStockToPortfolio(Stock stock) {
		stockDao.addStockToPortfolio(stock);
	}

	@Override
	public Set<Stock> getAllAvailableStocks() {
		return stockDao.getAllAvailableStocks();
	}

	@Override
	public double calculateGbceAllShareIndex() {
		Set<Stock> gbceStocks = getAllAvailableStocks();

		long aggregateTradedPrices = 0;
		long aggregateNumberOfTrades = 0;

		for (Stock stock : gbceStocks) {
			Collection<StockTrade> stockTrades = stock.getRecordedTrades().values();

			for (StockTrade trade : stockTrades) {
				aggregateTradedPrices += trade.getTradedPrice() * trade.getQuantityOfShares();
				aggregateNumberOfTrades++;
			}
		}

		double allShareIndex = 0d;
		if (aggregateNumberOfTrades != 0) {
			allShareIndex = Math.pow(aggregateTradedPrices, 1.0d / aggregateNumberOfTrades);
		}

		return allShareIndex;
	}
}

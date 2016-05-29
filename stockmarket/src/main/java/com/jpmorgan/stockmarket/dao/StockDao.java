package com.jpmorgan.stockmarket.dao;

import java.util.Set;

import com.jpmorgan.stockmarket.model.Stock;

public interface StockDao {

	/**
	 * @return Set<Stock> A set of all available stocks in the portfolio
	 */
	Set<Stock> getAllAvailableStocks();

	/**
	 * Adds the given stock to the stock portfolio
	 * 
	 * @param stock
	 *            - Stock to be added to the portfolio
	 */
	void addStockToPortfolio(Stock stock);
}

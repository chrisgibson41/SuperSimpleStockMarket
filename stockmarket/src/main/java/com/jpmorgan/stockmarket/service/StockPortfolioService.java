package com.jpmorgan.stockmarket.service;

import java.util.Set;

import com.jpmorgan.stockmarket.model.Stock;

public interface StockPortfolioService {

	/**
	 * Calculates the GBCE All Share Index of all stocks in the portfolio
	 * 
	 * @return
	 */
	public double calculateGbceAllShareIndex();

	/**
	 * Returns all available stocks from the portfolio
	 * 
	 * @return Set<Stock> - Available Stocks
	 */
	Set<Stock> getAllAvailableStocks();

	/**
	 * Adds a stock to the stock portfolio
	 * 
	 * @param stock
	 *            - Stock to add to the portfolio
	 */
	void addStockToPortfolio(Stock stock);
}

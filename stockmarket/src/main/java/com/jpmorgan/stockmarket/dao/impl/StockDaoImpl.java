package com.jpmorgan.stockmarket.dao.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.jpmorgan.stockmarket.dao.StockDao;
import com.jpmorgan.stockmarket.model.Stock;

@Component
public class StockDaoImpl implements StockDao {

	// For the purposes of this application, this set will act as our datasource
	private Set<Stock> availableStocks;

	public StockDaoImpl() {
		availableStocks = new HashSet<Stock>();
	}

	@Override
	public Set<Stock> getAllAvailableStocks() {
		return availableStocks;
	}

	@Override
	public void addStockToPortfolio(Stock stock) {
		if (stock != null) {
			availableStocks.add(stock);
		}
	}
}

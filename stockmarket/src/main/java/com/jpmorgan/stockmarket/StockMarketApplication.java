package com.jpmorgan.stockmarket;

import java.util.Random;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import com.jpmorgan.stockmarket.config.AppConfig;
import com.jpmorgan.stockmarket.model.Stock;
import com.jpmorgan.stockmarket.model.StockTrade;
import com.jpmorgan.stockmarket.model.impl.BuyOrSellEnum;
import com.jpmorgan.stockmarket.model.impl.CommonStock;
import com.jpmorgan.stockmarket.model.impl.PreferredStock;
import com.jpmorgan.stockmarket.service.StockPortfolioService;

@Service("stockMarketApplication")
public class StockMarketApplication {

	private StockPortfolioService stockTradingService;

	@Autowired
	public StockMarketApplication(StockPortfolioService stockTradingService) {
		this.stockTradingService = stockTradingService;
	}

	/**
	 * Calculates the stock details for each stock in the portfolio for the
	 * given price
	 * 
	 * @param stockPrice
	 */
	public void runApplication(long stockPrice) {
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("Calculating details for each stock using the the input price: " + stockPrice);

		for (Stock stock : stockTradingService.getAllAvailableStocks()) {
			calculateAndDisplayStockDetailsForPrice(stock, stockPrice);
		}

		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("GBCE All Share Index: " + stockTradingService.calculateGbceAllShareIndex());
		System.out.println("---------------------------------------------------------------------------------");
	}

	/**
	 * Calculates the stock details for the given stock and stock price
	 * 
	 * @param stock
	 *            - Stock to calculate
	 * @param stockPrice
	 *            - The price of the stock
	 */
	public void calculateAndDisplayStockDetailsForPrice(Stock stock, long stockPrice) {
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println(stock.toString());
		System.out.println("Stock Price: " + stockPrice + "p");
		System.out.println("Dividend Yield: " + stock.calculateDividendYield(stockPrice));
		System.out.println("P/E Ratio: " + stock.calculatePeRatio(stockPrice));

		System.out.println("Trades : ");
		long range = 100;
		Random r = new Random();
		long numberOfSharesToBuy = (long) (r.nextDouble() * range);

		StockTrade sellTrade = stock.recordStockTrade(numberOfSharesToBuy, stockPrice, BuyOrSellEnum.SELL);
		System.out.println("The following sell trade has been recorded : " + sellTrade.toString());
		StockTrade buyTrade = stock.recordStockTrade(numberOfSharesToBuy, stockPrice, BuyOrSellEnum.BUY);
		System.out.println("The following buy trade has been recorded : " + buyTrade.toString());

		DateTime currentTime = new DateTime();
		long volumeWeightedStockPrice = stock.calculateVolumeWeightedStockPrice(currentTime.minus(15));
		System.out.println(
				"Volume Weighted Stock Price based on trades in past 15 minutes: " + volumeWeightedStockPrice + "p");
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		StockMarketApplication stockTradingService = (StockMarketApplication) context.getBean("stockMarketApplication");

		// Setup the applications stock data
		stockTradingService.setupStockData();

		Long inputStockPrice = Long.valueOf(args[0]);
		if (inputStockPrice != null) {
			stockTradingService.runApplication(inputStockPrice);
		} else {
			System.out.println("Cannot calculate stock data as the input stock price is null");
		}
		context.close();
	}

	/**
	 * For the purpose of this test application, we will set up some test in
	 * memory stock data
	 */
	private void setupStockData() {
		Stock teaStock = new CommonStock("TEA", 0l, 100l);
		Stock popStock = new CommonStock("POP", 8l, 100l);
		Stock aleStock = new CommonStock("ALE", 23l, 60l);
		Stock ginStock = new PreferredStock("GIN", 8l, 0.02d, 100l);
		Stock joeStock = new CommonStock("JOE", 23l, 250l);

		stockTradingService.addStockToPortfolio(teaStock);
		stockTradingService.addStockToPortfolio(popStock);
		stockTradingService.addStockToPortfolio(aleStock);
		stockTradingService.addStockToPortfolio(ginStock);
		stockTradingService.addStockToPortfolio(joeStock);
	}
}

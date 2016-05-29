package com.jpmorgan.stockmarket.model.impl;

import org.joda.time.DateTime;

import com.jpmorgan.stockmarket.model.StockTrade;

/**
 * Implementation of a Stock Trade
 * 
 * @author Chris Gibson
 *
 */
public class StockTradeImpl implements StockTrade {

	private long quantityOfShares;
	private double tradedPrice;
	private DateTime tradeTimeStamp;
	private BuyOrSellEnum buyOrSell;

	public StockTradeImpl(long quantityOfShares, double tradedPrice, DateTime tradeTimeStamp, BuyOrSellEnum buyOrSell) {
		this.quantityOfShares = quantityOfShares;
		this.tradedPrice = tradedPrice;
		this.tradeTimeStamp = tradeTimeStamp;
		this.buyOrSell = buyOrSell;
	}

	/**
	 * @return the quantityOfShares
	 */
	@Override
	public long getQuantityOfShares() {
		return quantityOfShares;
	}

	/**
	 * @param quantityOfShares
	 *            the quantityOfShares to set
	 */
	@Override
	public void setQuantityOfShares(long quantityOfShares) {
		this.quantityOfShares = quantityOfShares;
	}

	/**
	 * @return the tradedPrice
	 */
	@Override
	public double getTradedPrice() {
		return tradedPrice;
	}

	/**
	 * @param tradedPrice
	 *            the tradedPrice to set
	 */
	@Override
	public void setTradedPrice(double tradedPrice) {
		this.tradedPrice = tradedPrice;
	}

	/**
	 * @return the tradeTimeStamp
	 */
	@Override
	public DateTime getTradeTimeStamp() {
		return tradeTimeStamp;
	}

	/**
	 * @param tradeTimeStamp
	 *            the tradeTimeStamp to set
	 */
	@Override
	public void setTradeTimeStamp(DateTime tradeTimeStamp) {
		this.tradeTimeStamp = tradeTimeStamp;
	}

	/**
	 * @return the buyOrSell
	 */
	@Override
	public BuyOrSellEnum getBuyOrSell() {
		return buyOrSell;
	}

	/**
	 * @param buyOrSell
	 *            the buyOrSell to set
	 */
	@Override
	public void setBuyOrSell(BuyOrSellEnum buyOrSell) {
		this.buyOrSell = buyOrSell;
	}

	@Override
	public String toString() {
		return "StockTradeImpl [quantityOfShares=" + quantityOfShares + ", tradedPrice=" + tradedPrice
				+ ", tradeTimeStamp=" + tradeTimeStamp + ", buyOrSell=" + buyOrSell + "]";
	}
}

package com.jpmorgan.stockmarket.model;

import org.joda.time.DateTime;

import com.jpmorgan.stockmarket.model.impl.BuyOrSellEnum;

public interface StockTrade {

	public long getQuantityOfShares();

	public void setQuantityOfShares(long quantityOfShares);

	public double getTradedPrice();

	public void setTradedPrice(double tradedPrice);

	public DateTime getTradeTimeStamp();

	public void setTradeTimeStamp(DateTime tradeTimeStamp);

	public BuyOrSellEnum getBuyOrSell();

	public void setBuyOrSell(BuyOrSellEnum buyOrSell);
}

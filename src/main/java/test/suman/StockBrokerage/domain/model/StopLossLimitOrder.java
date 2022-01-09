package test.suman.StockBrokerage.domain.model;

import java.util.concurrent.Future;

import javax.persistence.Entity;

import lombok.Getter;
import test.suman.StockBrokerage.domain.service.OrderProcessor;

@Getter
@Entity
public class StopLossLimitOrder extends Order {
	
	private final Double limitPrice;

	public StopLossLimitOrder(String orderReqId, String acctId, String stockCode, Integer quantity,
			Double triggerPrice, Double limitPrice, Boolean isBuy) {
		super(orderReqId, acctId, stockCode, quantity, triggerPrice, isBuy);
		this.limitPrice = limitPrice;
	}
	
	
	@Override
	public StockExchangeReq createStockExchangeReq() {
		return StockExchangeReq.builder()
				.code(getStockCode())
				.isBuyOrder(getIsBuy())
				.orderId(getOrderId().toString())
				.qty(getQuantity())
				.price(limitPrice)
				.build()
				;
	}


	@Override
	public OrderProcessor createOrderProcessor() {
		// TODO Auto-generated method stub
		return (svc, inv)->{
			
			inv.registerPriceWatcher( new StockInventoryObserver() {

				@Override
				public void onPriceUpdate(StockInventory inv1) {
					StockQuoteVO quote = inv1.getLTP(getStockCode());
					if(!quote.getStockCode().equals(getStockCode())) {
						return ;
					}
					
					if((!getIsBuy() && quote.getPrice()<getQuotedPricePerUnit()) 
						|| (getIsBuy() && quote.getPrice()>getQuotedPricePerUnit())) {
						// place a market order
						// deregister from inventory
						
						inv1.deRegisterPriceWatcher(this);
						
						svc.processOrderTransactional(StopLossLimitOrder.this)	;				
						
					}
				}
				
			});
			
		};
	}
}

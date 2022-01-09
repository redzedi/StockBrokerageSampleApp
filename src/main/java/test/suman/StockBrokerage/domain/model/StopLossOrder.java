package test.suman.StockBrokerage.domain.model;

import javax.persistence.Entity;

import test.suman.StockBrokerage.domain.service.OrderProcessor;

@Entity
public class StopLossOrder extends Order  {

	public StopLossOrder(String orderReqId, String acctId, String stockCode, Integer quantity,
			Double triggerPrice, Boolean isBuy) {
		super(orderReqId, acctId, stockCode, quantity, triggerPrice, isBuy);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public StockExchangeReq createStockExchangeReq() {
		return StockExchangeReq.builder()
				.code(getStockCode())
				.isBuyOrder(getIsBuy())
				.orderId(getOrderId().toString())
				.qty(getQuantity())
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
								
								svc.processOrderTransactional(StopLossOrder.this)	;				
								
							}
						}
						
					});
					
				};
	}
}

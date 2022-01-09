package test.suman.StockBrokerage.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import test.suman.StockBrokerage.domain.model.BrokerageAccount;
import test.suman.StockBrokerage.domain.model.Order;
import test.suman.StockBrokerage.domain.model.Order.OrderStatus;
import test.suman.StockBrokerage.domain.model.StockExchange;
import test.suman.StockBrokerage.domain.model.StockExchangeTxnSlip;
import test.suman.StockBrokerage.domain.model.StockInventory;
import test.suman.StockBrokerage.domain.repo.BrokerageAccountRepo;
import test.suman.StockBrokerage.domain.repo.OrderRepo;

@Service
public class OrderService {

	
	
	@Autowired
	private BrokerageAccountRepo acctRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	
	@Autowired
	private OrderAsyncExecutor asyncExecutor;
	
	@Autowired
	private StockExchangeFactory sFac;
	
	@Autowired
	private StockInventory inv;
	
	
	// idempotency check here 
	@Transactional
	public String  submitOrder(Order ord) {
		
		//validate order for trade
		
		// persist in db
         		
		Order savedOrder = orderRepo.save(ord);
		
		processOrderAsync(savedOrder); 
		
		return ord.getOrderId().toString();
	}
	
	@Async
	public void processOrderAsync( Order ord) {
		
		ord.createOrderProcessor().processOrder(asyncExecutor, inv);
		
	}
	
//	@Transactional
//	public void processOrderTransactional(Order ord) {
//
//		// if there is a failure in any of the steps - how do we update order to failure status
//		
//		// process order
//				
//				//update order in db
//				// update account stock holding
//				// debit//credit cash balance
//		BrokerageAccount acct = acctRepo.findById(ord.getAcctId()).orElseThrow();
//		StockExchange exch =  sFac.getExchangeForCode(ord.getStockCode()) ;
//		
//		//TODO: validate order 1
//		//TODO: block money/stocks in brokerage account
//		StockExchangeTxnSlip txnSlp = 	exch.doTrade(ord.createStockExchangeReq());
//		ord.updateOrder(txnSlp);
//		
//		if(ord.getStatus().equals(OrderStatus.SUCCESS)) {
//			acct.updateHoldingsAfterSuccessfullTxn(ord); 
//		}
//		
//		//orderRepo.save(ord);
//		//acctRepo.save(acct);
//	}

	
}

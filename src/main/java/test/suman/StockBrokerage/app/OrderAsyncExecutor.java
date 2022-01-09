package test.suman.StockBrokerage.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import test.suman.StockBrokerage.domain.model.BrokerageAccount;
import test.suman.StockBrokerage.domain.model.Order;
import test.suman.StockBrokerage.domain.model.StockExchange;
import test.suman.StockBrokerage.domain.model.StockExchangeTxnSlip;
import test.suman.StockBrokerage.domain.model.StockInventory;
import test.suman.StockBrokerage.domain.model.Order.OrderStatus;
import test.suman.StockBrokerage.domain.repo.BrokerageAccountRepo;
import test.suman.StockBrokerage.domain.repo.OrderRepo;


@Service
public class OrderAsyncExecutor {
	

	@Autowired
	private BrokerageAccountRepo acctRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	
	
	@Autowired
	private StockExchangeFactory sFac;
	
	@Autowired
	private StockInventory inv;
	
//	@Async
//	public void processOrderAsync(OrderService ordSvc, Order ord, StockInventory inv) {
//		
//		ord.createOrderProcessor().processOrder(ordSvc, null);
//		
//	}
//	
	@Transactional
	public void processOrderTransactional(Order ord) {

		// if there is a failure in any of the steps - how do we update order to failure status
		
		// process order
				
				//update order in db
				// update account stock holding
				// debit//credit cash balance
		BrokerageAccount acct = acctRepo.findById(ord.getAcctId()).orElseThrow();
		StockExchange exch =  sFac.getExchangeForCode(ord.getStockCode()) ;
		
		//TODO: validate order 1
		//TODO: block money/stocks in brokerage account
		StockExchangeTxnSlip txnSlp = 	exch.doTrade(ord.createStockExchangeReq());
		ord.updateOrder(txnSlp);
		
		if(ord.getStatus().equals(OrderStatus.SUCCESS)) {
			acct.updateHoldingsAfterSuccessfullTxn(ord); 
		}
		
		//orderRepo.save(ord);
		//acctRepo.save(acct);
	}




}

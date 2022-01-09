package test.suman.StockBrokerage.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BrokerageAccount implements Persistable<String> {
	@Id
	private  String userName;
	private  Double cashBalance=0.0D;
	private String accountStatus;
	private long createdat = System.currentTimeMillis();
	private String fullName;
	
	@Transient
	private boolean forUpdate;
	
	// what's the correct data structure ??
	
	@ElementCollection
    private Set<StockHolding> holdings  = new HashSet<>();
    
    
    //should we use a parameterObject ??
    public void updateHoldingsAfterSuccessfullTxn(Order ord) {
    	
    	if(!ord.getStatus().equals(Order.OrderStatus.SUCCESS)) {
    		return;
    	}
    	
    	if(ord.getIsBuy()) {
    		cashBalance -= ord.getSettlementPrice();
    	}else {
    		cashBalance += ord.getSettlementPrice();
    	}
    	
    	//update holdings - what if we do not want re
    	
    }
    
    //could return a confirmation object
    public void depositMoney(PayInOrder pi) {
    	
    }
    
    public PayoutOrder withdrawMoney(Integer amt) {
    	return null;
    }
    
    public Set<StockHolding> getStockHoldings(){
    	return holdings;
    }

	@Override
	public String getId() {
		return this.userName;
	}

	@Override
	public boolean isNew() {
		return !forUpdate;
	}
	
	@PostLoad
	public void markIsUpdated() {
		forUpdate = true;
	}
    

}

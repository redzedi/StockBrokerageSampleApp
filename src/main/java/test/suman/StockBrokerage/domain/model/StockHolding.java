package test.suman.StockBrokerage.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Embeddable
@EqualsAndHashCode(of = {"stockCode"})
@AllArgsConstructor
@Getter
public class StockHolding {
	
	@Column(unique = true)
	private final String stockCode;
	private  Integer qty;
	
	private  Double avgPricePerUnit;
	
	public void updateForPurchase(Integer buyQty , Double totalPrice) {
		avgPricePerUnit  = (avgPricePerUnit*qty + totalPrice)/(qty+buyQty);
		this.qty += buyQty;
	}
	
	public void updateForSell(Integer sellQty) {
		this.qty -= sellQty;
	}

}

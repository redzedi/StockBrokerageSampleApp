package test.suman.StockBrokerage.domain.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import test.suman.StockBrokerage.domain.service.OrderProcessor;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
include=JsonTypeInfo.As.PROPERTY,
property="orderType")
@JsonSubTypes({
@JsonSubTypes.Type(value=MarketOrder.class, name="market-order"),
@JsonSubTypes.Type(value=LimitOrder.class, name="limit-order")
})
@EqualsAndHashCode(of= {"acctId","stockCode","quantity","quotedPricePerUnit","isBuy","createTime"})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "OrderParent")
@Getter
public abstract class Order {
	
	
	
	public static  enum OrderStatus{ CREATED, SUBMITTED_TO_EXCHANGE, SUCCESS, FAILURE };
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column( name="ord_id",length = 36)
	@org.hibernate.annotations.Type(type="uuid-char")
	private UUID orderId;
	
	@Column(unique = true)
	@NotBlank
	private  String ordReqId;
	
	@NotBlank
	private  String acctId;
	
	@NotBlank
	private  String stockCode;
	
	//@Positive
	private  Integer quantity;
	
	//custom validation around LTP 
	// could be null
	private  Double quotedPricePerUnit;
	@NotNull
	private  Boolean isBuy;

	private  Long createTime = System.currentTimeMillis();
	private Long modifiedTime;
	
	private OrderStatus status=OrderStatus.CREATED;
	
	//executed
		private  Double settlementPrice;
		
	
	
	
	
	//could be a flux of order events
	
	// or the contract here could be - the returned Event depicts the validaton and initial state of the order
	// i.e the initial response from the exchange
	
	//process order in exhange
	//public abstract void processOrder(StockExchange exch) ;
 	
	public abstract StockExchangeReq createStockExchangeReq() ;
	
	public abstract OrderProcessor createOrderProcessor();




	protected Order(String orderReqId, String acctId, String stockCode, Integer quantity, Double quotedPricePerUnit,
			Boolean isBuy) {
		super();
		this.ordReqId = orderReqId;
		this.acctId = acctId;
		this.stockCode = stockCode;
		this.quantity = quantity;
		this.quotedPricePerUnit = quotedPricePerUnit;
		this.isBuy = isBuy;
		this.createTime = System.currentTimeMillis();
	}
	
	protected Order() {
		
	}
	
	
	//who calls this method? -  1. webhook or callback from exchange ?
	// there could be events from here 
	public void updateOrder(StockExchangeTxnSlip txnSlip) {
		// update state
		if(this.getOrderId().equals(getOrderId())) {
			
			System.out.println("***** UPDATING ORDER STATE ***");
			this.modifiedTime = System.currentTimeMillis();
			switch(txnSlip.getTxnStatus()) {
			
			case ACCEPTED:
				this.status = OrderStatus.SUBMITTED_TO_EXCHANGE;
				break;
			case EXECUTED:
				this.status = OrderStatus.SUCCESS;
				this.settlementPrice = txnSlip.getSettlementPrice();
				break;
			case EXPIRED:
			case REJECTED:
				this.status = OrderStatus.FAILURE;
				break;
			}
		}
	}

}

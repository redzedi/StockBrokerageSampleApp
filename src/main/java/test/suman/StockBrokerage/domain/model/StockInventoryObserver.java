package test.suman.StockBrokerage.domain.model;

@FunctionalInterface
public interface StockInventoryObserver {
	
	public void onPriceUpdate(StockInventory inv);

}

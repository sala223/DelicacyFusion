package com.df.order.price;

public interface PriceCalculator {

    void calculateOrder(PriceContext priceContext);

    void calculateItem(PriceContext priceContext);
}

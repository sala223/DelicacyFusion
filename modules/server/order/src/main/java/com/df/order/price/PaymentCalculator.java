package com.df.order.price;

import java.math.BigDecimal;

public interface PaymentCalculator {

    PaymentContext createContext();
    
    void calculateOrderPayment(PaymentContext paymentContext);

    BigDecimal calculateItemPayment(PaymentContext paymentContext);
}

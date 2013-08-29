package com.df.order.entity;

import java.io.Serializable;

import javax.persistence.Column;

public class PaymentLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private long lineNumber;

    @Column(nullable = false)
    private long itemId;

    @Column
    private float totalPrice;

    @Column
    private int quantity;

}

package com.df.order.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

@Entity
public class Delivery extends TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_id_sequence")
    @SequenceGenerator(name = "delivery_id_sequence", sequenceName = "delivery_id_sequence")
    private long deliveryId;

    @Column(name = "ORDER_ID", nullable = false)
    private long orderId;

    @ElementCollection
    @CollectionTable(name = "DELIVERY_LINE", joinColumns = @JoinColumn(name = "order_id"))
    @JoinFetch(JoinFetchType.OUTER)
    private List<DeliveryLine> lines;

    @Override
    public long getTransactionId() {
	return deliveryId;
    }

    @Override
    public void setTransactionId(long transactionId) {
	this.deliveryId = transactionId;
    }

    public long getOrderId() {
	return orderId;
    }

    public void setOrderId(long orderId) {
	this.orderId = orderId;
    }

}

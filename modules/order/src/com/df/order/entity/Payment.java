package com.df.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.df.core.persist.eclipselink.MultiTenantSupport;

public class Payment extends MultiTenantSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_sequence")
    @SequenceGenerator(name = "payment_id_sequence", sequenceName = "payment_id_sequence")
    private long orderId;

    @Temporal(value = TemporalType.TIME)
    @Column(nullable = false)
    private Date createTime;

    @Temporal(value = TemporalType.TIME)
    @Column(nullable = true)
    private Date payTime;

}

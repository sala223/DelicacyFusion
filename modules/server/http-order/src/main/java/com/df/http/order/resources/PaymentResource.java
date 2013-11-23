package com.df.http.order.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.df.core.rs.TenantResource;
import com.df.order.entity.Payment;
import com.df.order.entity.PaymentLine.PaymentMethod;
import com.df.order.exception.PaymentException;
import com.df.order.service.contract.PaymentService;

@Path("/tenant/{tenantCode}/store/{storeCode}/payment")
@Produces("application/json;charset=UTF-8")
@Component
public class PaymentResource extends TenantResource {

	@Autowired
	private PaymentService paymentService;

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@GET
	@Path("/open/today")
	public List<Payment> getTodayOpenPayments(@PathParam("tenantCode") String tenantCode,
	        @PathParam("storeCode") String storeCode) {
		this.injectTenantContext(tenantCode);
		return paymentService.listTodayOpenPayments(storeCode, LocaleContextHolder.getLocale());
	}

	@POST
	@Path("/order/{orderId}")
	public Payment createPaymentByOrder(@PathParam("tenantCode") String tenantCode,
	        @PathParam("storeCode") String storeCode, @PathParam("orderId") long orderId) {
		this.injectTenantContext(tenantCode);
		return paymentService.createPayment(storeCode, orderId);
	}

	@GET
	@Path("/order/{orderId}")
	public Payment getPaymentByOrder(@PathParam("tenantCode") String tenantCode,
	        @PathParam("storeCode") String storeCode, @PathParam("orderId") long orderId) {
		this.injectTenantContext(tenantCode);
		Payment payment = paymentService.getPaymentByOrder(storeCode, orderId);
		if (payment == null) {
			throw PaymentException.paymentNotCreated(orderId);
		}
		return payment;
	}

	@GET
	@Path("/{paymentId}")
	public Payment getPaymentById(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	        @PathParam("paymentId") long paymentId) {
		this.injectTenantContext(tenantCode);
		Payment payment = paymentService.getPaymentById(storeCode, paymentId);
		if (payment == null) {
			throw PaymentException.paymentNotFound("Payment " + paymentId + " is not found");
		}
		return payment;
	}

	@POST
	@Path("/bill/cash/{orderId}")
	public Payment billOrderWithCash(@PathParam("tenantCode") String tenantCode,
	        @PathParam("storeCode") String storeCode, @PathParam("orderId") long orderId) {
		this.injectTenantContext(tenantCode);
		return paymentService.bill(storeCode, orderId, PaymentMethod.CASH);
	}
}

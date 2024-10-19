
package com.example.demo.service.impl;

import com.example.demo.dto.PaymentRequest;
import com.example.demo.dto.PaymentResponse;
import com.example.demo.dto.PaymentDetail;
import com.example.demo.dto.AccountDetail;
import com.example.demo.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final JdbcTemplate jdbcTemplate;
    private PaymentResponse paymentResponse;

    @Override
    public void executePaymentProcess(PaymentRequest request) {
        String sql = "{CALL process_payments(?, ?, ?)}";
        jdbcTemplate.update(sql, request.getFechaActual(), request.getTasaInteres(), request.getDiasAnioComercial());

        paymentResponse = new PaymentResponse();
        paymentResponse.setPagos(getPaymentDetails());
        paymentResponse.setCuentas(getAccountDetails());
    }

    @Override
    public PaymentResponse getProcessedPayments() {
        return paymentResponse;
    }

    private List<PaymentDetail> getPaymentDetails() {
        String sql = "SELECT cliente, plazo, monto, interes, iva, pago FROM payments";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PaymentDetail paymentDetail = new PaymentDetail();
            paymentDetail.setCliente(rs.getString("cliente"));
            paymentDetail.setPlazo(rs.getInt("plazo"));
            paymentDetail.setMonto(rs.getBigDecimal("monto"));
            paymentDetail.setInteres(rs.getBigDecimal("interes"));
            paymentDetail.setIva(rs.getBigDecimal("iva"));
            paymentDetail.setPago(rs.getBigDecimal("pago"));
            return paymentDetail;
        });
    }

    private List<AccountDetail> getAccountDetails() {
        String sql = "SELECT cliente, monto FROM Accounts WHERE Status = 'Activa'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AccountDetail accountDetail = new AccountDetail();
            accountDetail.setCliente(rs.getString("cliente"));
            accountDetail.setMonto(rs.getBigDecimal("monto"));
            return accountDetail;
        });
    }
}

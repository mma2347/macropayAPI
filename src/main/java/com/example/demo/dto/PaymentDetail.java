package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDetail {
    private String cliente;
    private int plazo;
    private BigDecimal monto;
    private BigDecimal interes;
    private BigDecimal iva;
    private BigDecimal pago;


}

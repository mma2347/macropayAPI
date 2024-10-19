
package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private String fechaActual;
    private BigDecimal tasaInteres;
    private int diasAnioComercial;
}

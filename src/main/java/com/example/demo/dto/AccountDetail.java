package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDetail {
    private String cliente;
    private BigDecimal monto;
}

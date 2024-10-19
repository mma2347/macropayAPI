
package com.example.demo.service;

import com.example.demo.dto.PaymentRequest;
import com.example.demo.dto.PaymentResponse;

public interface PaymentService {
    void executePaymentProcess(PaymentRequest request);
    PaymentResponse getProcessedPayments();
}

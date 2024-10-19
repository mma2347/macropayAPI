
package com.example.demo.controller;

import com.example.demo.dto.PaymentRequest;
import com.example.demo.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER')")
@CrossOrigin
@AllArgsConstructor
public class PaymentController {
    
    private final PaymentService paymentService;


    @PostMapping("/process-payments")
    public ResponseEntity<?> processPayments(@RequestBody PaymentRequest request) {
        paymentService.executePaymentProcess(request);
        return ResponseEntity.ok(paymentService.getProcessedPayments());
    }
}

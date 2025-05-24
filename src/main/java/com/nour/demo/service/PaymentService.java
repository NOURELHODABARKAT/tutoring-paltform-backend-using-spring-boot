package com.nour.demo.service;

import com.nour.demo.dto.ChargilyPaymentRequestDTO;
import com.nour.demo.dto.ChargilyPaymentResponseDTO;

public interface PaymentService {
    ChargilyPaymentResponseDTO initiatePayment(ChargilyPaymentRequestDTO request);
}

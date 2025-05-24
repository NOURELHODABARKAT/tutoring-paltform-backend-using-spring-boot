
package com.nour.demo.service;

import com.nour.demo.dto.ChargilyPaymentRequestDTO;
import com.nour.demo.dto.ChargilyPaymentResponseDTO;
import com.nour.demo.model.Cours;
import com.nour.demo.model.Payment;
import com.nour.demo.model.User.User;
import com.nour.demo.repository.CourseRepository;
import com.nour.demo.repository.PaymentRepository;
import com.nour.demo.repository.UserRepository;
import com.nour.demo.service.PaymentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public ChargilyPaymentResponseDTO initiatePayment(ChargilyPaymentRequestDTO request) {
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("الطالب غير موجود"));
        Cours course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("الدورة غير موجودة"));

        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setCourse(course);
        payment.setAmount(request.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus("PENDING");
        payment.setChargilyInvoiceId(UUID.randomUUID().toString());
        payment.setCheckoutUrl("https://sandbox.chargily.com/checkout/" + payment.getChargilyInvoiceId());

        paymentRepository.save(payment);

        return new ChargilyPaymentResponseDTO(payment.getCheckoutUrl(), payment.getChargilyInvoiceId());
    }
}

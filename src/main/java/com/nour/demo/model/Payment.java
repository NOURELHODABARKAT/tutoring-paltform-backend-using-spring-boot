package com.nour.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.nour.demo.model.User.User;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "payment_status")
    private String paymentStatus = "PENDING";

    @Column(name = "chargily_invoice_id")
    private String chargilyInvoiceId;

    @Column(name = "checkout_url")
    private String checkoutUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Cours course;

    // Getters and Setters with lombok (•‿•)
}

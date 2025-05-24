package com.nour.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargilyPaymentResponseDTO {
    private String checkoutUrl;
    private String invoiceId;

    // // Getters and Setters
    // public String getCheckoutUrl() {
    //     return checkoutUrl;
    // }

    // public void setCheckoutUrl(String checkoutUrl) {
    //     this.checkoutUrl = checkoutUrl;
    // }

    // public String getInvoiceId() {
    //     return invoiceId;
    // }

    // public void setInvoiceId(String invoiceId) {
    //     this.invoiceId = invoiceId;
    // }
}
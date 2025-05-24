package com.nour.demo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.apache.commons.codec.digest.HmacUtils;

public class WebhookController {

    private static final String apiSecretKey = "your-secret-key"; // Replace with your actual secret key

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("signature") String signature) {
        String computedSignature = HmacUtils.hmacSha256Hex(apiSecretKey, payload);
        if (!computedSignature.equals(signature)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid signature");
        }

        // معالجة الحدث هنا

        return ResponseEntity.ok("Webhook received");
    }

}
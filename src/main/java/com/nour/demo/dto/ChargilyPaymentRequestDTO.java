package com.nour.demo.dto;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargilyPaymentRequestDTO {
    private Long studentId;
    private Long courseId;
    private BigDecimal amount;
    private String studentEmail;
    private String studentName;

    // // ----- Getters -----
    // public Long getStudentId() {
    //     return studentId;
    // }

    // public Long getCourseId() {
    //     return courseId;
    // }

    // public BigDecimal getAmount() {
    //     return amount;
    // }

    // public String getStudentEmail() {
    //     return studentEmail;
    // }

    // public String getStudentName() {
    //     return studentName;
    // }

    // // ----- Setters -----
    // public void setStudentId(Long studentId) {
    //     this.studentId = studentId;
    // }

    // public void setCourseId(Long courseId) {
    //     this.courseId = courseId;
    // }

    // public void setAmount(BigDecimal amount) {
    //     this.amount = amount;
    // }

    // public void setStudentEmail(String studentEmail) {
    //     this.studentEmail = studentEmail;
    // }

    // public void setStudentName(String studentName) {
    //     this.studentName = studentName;
    // }
}
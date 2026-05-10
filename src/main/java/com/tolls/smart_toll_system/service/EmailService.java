package com.tolls.smart_toll_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendTollDeductionAlert(String toEmail, String name,
                                       double amount, double balance, String booth) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(toEmail);
            msg.setSubject("FASTag Toll Deduction - Smart Toll System");
            msg.setText(
                    "Dear " + name + ",\n\n" +
                            "₹" + amount + " has been deducted from your FASTag wallet at " + booth + ".\n" +
                            "Remaining Balance: ₹" + balance + "\n\n" +
                            (balance < 100 ? "⚠️ WARNING: Your balance is low. Please recharge soon!\n\n" : "") +
                            "Thank you for using Smart Toll System."
            );
            mailSender.send(msg);
        } catch (Exception e) {
            System.out.println("Email sending failed: " + e.getMessage());
        }
    }

    public void sendLowBalanceAlert(String toEmail, String name, double balance) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(toEmail);
            msg.setSubject("⚠️ Low FASTag Balance Alert");
            msg.setText(
                    "Dear " + name + ",\n\n" +
                            "Your FASTag wallet balance is low: ₹" + balance + "\n" +
                            "Please recharge to avoid payment failures at toll booths.\n\n" +
                            "Recharge now at: http://localhost:8080/wallet\n\n" +
                            "Smart Toll System"
            );
            mailSender.send(msg);
        } catch (Exception e) {
            System.out.println("Low balance email failed: " + e.getMessage());
        }
    }
}
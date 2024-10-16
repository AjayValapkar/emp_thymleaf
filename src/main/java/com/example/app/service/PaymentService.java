package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.dto.PayLeave;
import com.example.app.models.Payment;

@Service
public interface PaymentService {

	String doPayment(Integer empId);

	List<PayLeave> getAllPay(Integer empId);

}

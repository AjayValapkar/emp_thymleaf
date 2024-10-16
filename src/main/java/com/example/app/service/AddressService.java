package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.models.Address;
@Service
public interface AddressService {
	String updateAddress(Integer empId, Address addree);

	String addEmpAddress(Address address, Integer empId);

	String deleteAddress(Integer addId);
	
	List<Address> empAdd(Integer empId);

	Address getAddress(Integer id);
	
	Integer getNextAvailableId();
}

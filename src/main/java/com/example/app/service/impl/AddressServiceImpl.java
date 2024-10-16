package com.example.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.models.Address;
import com.example.app.models.Employee;
import com.example.app.repository.AddressRepository;
import com.example.app.repository.EmployeeRepository;
import com.example.app.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	EmployeeRepository empRepo;

	@Autowired
	AddressRepository addRepo;

	@Override
	public String updateAddress(Integer id, Address addree) {
		// TODO Auto-generated method stub
		Address addre = addRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee Not found"));
		try {
			addre.setId(id);
			addre.setCity(addree.getCity());
			addre.setState(addree.getState());
			addre.setPincode(addree.getPincode());
			addRepo.save(addre);
			return addre.getEmployee().getEmpName();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@Override
	public String addEmpAddress(Address address, Integer empId) {
		// TODO Auto-generated method stub
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));

		address.setEmployee(emp);
		emp.getAddress().add(address);
		addRepo.save(address);
		empRepo.save(emp);
		return emp.getEmpName();
	}

	@Override
	public String deleteAddress(Integer addId) {
		Address add = addRepo.findById(addId).orElseThrow(() -> new RuntimeException("Address Not Found"));
		Employee emp = add.getEmployee();
		emp.getAddress().remove(add);
		empRepo.save(emp);
		addRepo.delete(add);

		return emp.getEmpName();
	}

	@Override
	public List<Address> empAdd(Integer empId) {
		return addRepo.findByEmployeeEmpId(empId);
	}

	@Override
	public Address getAddress(Integer id) {
		return addRepo.findById(id).orElseThrow(() -> new RuntimeException("Address Not Found"));
	}

	@Override
	public Integer getNextAvailableId() {
		List<Integer> existingIds = addRepo.findAllIds();
		Integer nextId = 1;

		while (existingIds.contains(nextId)) {
			nextId++;
		}

		return nextId;
	}

}

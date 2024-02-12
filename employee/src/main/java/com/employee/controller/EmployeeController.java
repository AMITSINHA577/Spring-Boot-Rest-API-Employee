package com.employee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@PostMapping("/emp")
	public String createNewEmployee(@RequestBody Employee employee) {
		employeeRepository.save(employee);
		return "Employee created";

	}

	@GetMapping("/emp")
	public ResponseEntity<List<Employee>> getAllEmployeeData() {
		List<Employee> empList = new ArrayList<>();
		employeeRepository.findAll().forEach(empList::add);
		return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);

	}

	@GetMapping("/emp/{empid}")
	public ResponseEntity<Employee> getDataById(@PathVariable long empid) {

		Optional<Employee> empOP = employeeRepository.findById(empid);
		if (empOP.isPresent()) {
			return new ResponseEntity<Employee>(empOP.get(), HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/emp/{empid}")

	public String updateDataOfEmp(@PathVariable long empid, @RequestBody Employee employee) {

		Optional<Employee> empOp = employeeRepository.findById(empid);
		if (empOp.isPresent()) {

			Employee existEmp = empOp.get();
			existEmp.setEmp_age(employee.getEmp_age());
			existEmp.setEmp_city(employee.getEmp_city());
			existEmp.setEmp_name(employee.getEmp_name());
			existEmp.setEmp_salary(employee.getEmp_salary());
			employeeRepository.save(existEmp);
			return "Employee update successfullay";
		} else {
			return "Employee dost not exist";
		}

	}

	@DeleteMapping("emp/{empid}")
	public String deleteEmpById(@PathVariable long empid) {
		employeeRepository.deleteById(empid);
		return "Employee delete successfully";
	}

	@DeleteMapping("/emp")
	public String deleteAllEmpData() {
		employeeRepository.deleteAll();
		return "Employee Deleted Successfully";
	}

//	@GetMapping("/emp/emp_city")
//	public ResponseEntity<Employee> getEmployeeByempcity(@RequestParam("emp_city") String emp_city) {
//		Employee emp = employeeRepository.findByEmpcity(emp_city);
//		return new ResponseEntity<Employee>(emp, HttpStatus.FOUND);
//	}

//	@GetMapping("/emp/employeeGreaterThan")
//	public ResponseEntity<List<Employee>> getEmployeeGreaterThan(@RequestParam("emp_age") int emp_age){
//		Optional<List<Employee>> empList = employeeRepository.findByEmpageGreaterThan(emp_age);
//		return new ResponseEntity<List<Employee>>(empList.get(), HttpStatus.FOUND);
//	}

}

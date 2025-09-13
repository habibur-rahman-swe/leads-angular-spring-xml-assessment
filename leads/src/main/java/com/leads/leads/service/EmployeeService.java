package com.leads.leads.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.leads.leads.entity.Employee;
import com.leads.leads.repository.EmployeeRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	// List all employees
	public List<Employee> listEmployees() {
		return employeeRepository.findAll();
	}

	// Find employee by ID
	public Optional<Employee> findEmployeeById(Long id) {
		return employeeRepository.findById(id);
	}

	// Create or Update employee
	public Employee saveOrUpdateEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	// Delete employee
	public void deleteEmployee(Long id) {
		employeeRepository.deleteById(id);
	}

	public List<Employee> importEmployeesFromXML(MultipartFile file) throws Exception {
	    List<Employee> employees = new ArrayList<>();

	    try (InputStream inputStream = file.getInputStream()) {
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setIgnoringElementContentWhitespace(true);
	        DocumentBuilder builder = factory.newDocumentBuilder();

	        // Pass raw InputStream; DOM parser handles UTF-16 BOM automatically
	        Document doc = builder.parse(inputStream);

	        NodeList employeeNodes = doc.getElementsByTagName("employee");
	        for (int i = 0; i < employeeNodes.getLength(); i++) {
	            Element e = (Element) employeeNodes.item(i);
				Long employeeId = e.getElementsByTagName("id").item(0) != null
						? Long.parseLong(e.getElementsByTagName("id").item(0).getTextContent())
						: null;
	            String firstName = e.getElementsByTagName("firstname").item(0).getTextContent();
	            String lastName = e.getElementsByTagName("lastname").item(0).getTextContent();
	            String title = e.getElementsByTagName("title").item(0).getTextContent();
	            String division = e.getElementsByTagName("division").item(0).getTextContent();
	            String building = e.getElementsByTagName("building").item(0).getTextContent();
	            String room = e.getElementsByTagName("room").item(0).getTextContent();

	            Employee employee = new Employee(firstName, lastName, division, building, title, room);
	            if (employeeId != null) {
	            	employee.setEmployeeId(employeeId);
	            }
	            employees.add(employee);
	        }
	    }

	    return employeeRepository.saveAll(employees);
	}

}

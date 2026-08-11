package com.mdzdmr.ema.service;

import com.mdzdmr.ema.entity.Employee;
import com.mdzdmr.ema.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> getEmployeeById(Long id){
        return employeeRepository.findById(id);
    }

    public Optional<Employee> createEmployee(Employee employee){
        if(employeeRepository.findByEmail(employee.getEmail()).isPresent()){
            return Optional.empty();
        }
        return Optional.of(employeeRepository.save(employee));
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Optional<Employee> updateEmployee(Long id, Employee employeeRequest) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();

            employee.setFirstName(employeeRequest.getFirstName());
            employee.setLastName(employeeRequest.getLastName());
            employee.setEmail(employeeRequest.getEmail());
            employee.setJobTitle(employeeRequest.getJobTitle());

            return Optional.of(employeeRepository.save(employee));
        }
        return Optional.empty();
    }

    public boolean deleteEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty())
            return false;

        employeeRepository.delete(optionalEmployee.get());
        return true;
    }
}

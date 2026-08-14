package com.mdzdmr.ema.service;

import com.mdzdmr.ema.entity.Employee;
import com.mdzdmr.ema.exception.DuplicateEmployeeException;
import com.mdzdmr.ema.exception.EmployeeNotFoundException;
import com.mdzdmr.ema.exception.EmptyRequestBodyException;
import com.mdzdmr.ema.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployeeById(Long id){

        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee createEmployee(Employee employee){
        employee.setEmail(employee.getEmail().toLowerCase(Locale.ROOT));
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new DuplicateEmployeeException(employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(Long id, Employee employeeRequest) {

        if(     (employeeRequest.getFirstName() == null || employeeRequest.getFirstName().isBlank()) &&
                (employeeRequest.getLastName() == null || employeeRequest.getLastName().isBlank()) &&
                (employeeRequest.getEmail() == null || employeeRequest.getEmail().isBlank()) &&
                (employeeRequest.getJobTitle() == null || employeeRequest.getJobTitle().isBlank())
        )
            throw new EmptyRequestBodyException();

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        if(employeeRequest.getEmail() != null && !employeeRequest.getEmail().isBlank()){
            String standardizedEmail = employeeRequest.getEmail().toLowerCase(Locale.ROOT);

            Optional<Employee> employeeByEmail =
                    employeeRepository.findByEmail(standardizedEmail);

            if (employeeByEmail.isPresent() && !employeeByEmail.get().getId().equals(id))
                throw new DuplicateEmployeeException(standardizedEmail);
            employee.setEmail(standardizedEmail);
        }

        if(!(employeeRequest.getFirstName() == null || employeeRequest.getFirstName().isBlank()))
            employee.setFirstName(employeeRequest.getFirstName());
        if(!(employeeRequest.getLastName() == null || employeeRequest.getLastName().isBlank()))
            employee.setLastName(employeeRequest.getLastName());
        if(!(employeeRequest.getJobTitle() == null || employeeRequest.getJobTitle().isBlank()))
            employee.setJobTitle(employeeRequest.getJobTitle());

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty())
            throw new EmployeeNotFoundException(id);

        employeeRepository.delete(optionalEmployee.get());
    }
}

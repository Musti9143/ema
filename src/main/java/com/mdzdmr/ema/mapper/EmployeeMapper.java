package com.mdzdmr.ema.mapper;

import com.mdzdmr.ema.dto.CreateEmployeeRequest;
import com.mdzdmr.ema.dto.EmployeeResponse;
import com.mdzdmr.ema.dto.UpdateEmployeeRequest;
import com.mdzdmr.ema.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEmployee(UpdateEmployeeRequest updateEmployeeRequest){
        return new Employee(updateEmployeeRequest.firstName(), updateEmployeeRequest.lastName(), updateEmployeeRequest.email(), updateEmployeeRequest.jobTitle());
    }
    public Employee toEmployee(CreateEmployeeRequest createEmployeeRequest){
        return new Employee(createEmployeeRequest.firstName(), createEmployeeRequest.lastName(), createEmployeeRequest.email(), createEmployeeRequest.jobTitle());
    }
    public EmployeeResponse toResponse(Employee employee){
        return new EmployeeResponse(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getJobTitle());
    }
}

package com.mdzdmr.ema.controller;

import com.mdzdmr.ema.dto.CreateEmployeeRequest;
import com.mdzdmr.ema.dto.UpdateEmployeeRequest;
import com.mdzdmr.ema.entity.Employee;
import com.mdzdmr.ema.service.EmployeeService;
import jakarta.validation.Valid;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id){
        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(id);

        return optionalEmployee
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees(){
        List<Employee> employeeList = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeList);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid CreateEmployeeRequest createEmployeeRequest){
        Optional<Employee> optionalEmployee = employeeService.createEmployee(mapToEmployee(createEmployeeRequest));
        if (optionalEmployee.isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        Employee employee = optionalEmployee.get();
        URI location = URI.create("/api/v1/employees/" + employee.getId());
        return ResponseEntity.created(location).body(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody UpdateEmployeeRequest updateEmployeeRequest){
        Optional<Employee> optionalEmployee = employeeService.updateEmployee(id, mapToEmployee(updateEmployeeRequest));
        return optionalEmployee
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        if (employeeService.deleteEmployee(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private Employee mapToEmployee(UpdateEmployeeRequest updateEmployeeRequest){
        return new Employee(updateEmployeeRequest.firstName(), updateEmployeeRequest.lastName(), updateEmployeeRequest.email(), updateEmployeeRequest.jobTitle());
    }

    private Employee mapToEmployee(CreateEmployeeRequest createEmployeeRequest) {
        return new Employee(createEmployeeRequest.firstName(), createEmployeeRequest.lastName(), createEmployeeRequest.email(), createEmployeeRequest.jobTitle());
    }
}

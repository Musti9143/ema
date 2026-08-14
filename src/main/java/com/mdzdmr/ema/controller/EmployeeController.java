package com.mdzdmr.ema.controller;

import com.mdzdmr.ema.dto.CreateEmployeeRequest;
import com.mdzdmr.ema.dto.EmployeeResponse;
import com.mdzdmr.ema.dto.UpdateEmployeeRequest;
import com.mdzdmr.ema.mapper.EmployeeMapper;
import com.mdzdmr.ema.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper mapper;

    public EmployeeController(EmployeeService employeeService,  EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.mapper = employeeMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id){

        EmployeeResponse employeeResponse = mapper.toResponse(employeeService.getEmployeeById(id));
        return ResponseEntity.ok(employeeResponse);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees(){
        List<EmployeeResponse> employeeResponseList = employeeService.getAllEmployees()
                .stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(employeeResponseList);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Valid CreateEmployeeRequest createEmployeeRequest){

        EmployeeResponse employeeResponse = mapper.toResponse(employeeService.createEmployee(mapper.toEmployee(createEmployeeRequest)));
        return ResponseEntity.created(URI.create("/api/v1/employees/" + employeeResponse.id())).body(employeeResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @Valid @RequestBody UpdateEmployeeRequest updateEmployeeRequest){

        EmployeeResponse employeeResponse = mapper.toResponse(
                employeeService.updateEmployee(id, mapper.toEmployee(updateEmployeeRequest)));

        return ResponseEntity.ok(employeeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){

        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}

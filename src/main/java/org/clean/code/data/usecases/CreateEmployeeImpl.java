package org.clean.code.data.usecases;

import org.clean.code.data.interfaces.EmployeeRepository;
import org.clean.code.data.interfaces.Encrypter;
import org.clean.code.domain.dto.CreateEmployeeDTO;
import org.clean.code.domain.models.Employee;
import org.clean.code.domain.usecases.CreateEmployee;

import javax.transaction.Transactional;

public class CreateEmployeeImpl implements CreateEmployee {

    private EmployeeRepository employeeRepository;
    private Encrypter encrypter;

    public CreateEmployeeImpl(EmployeeRepository employeeRepository, Encrypter encrypter) {
        this.employeeRepository = employeeRepository;
        this.encrypter = encrypter;
    }

    @Override
    @Transactional
    public Employee create(CreateEmployeeDTO employeeDTO) {
        Employee employee = this.employeeRepository.findByEmail(employeeDTO.email);
        if(employee != null) {
            throw new RuntimeException("Já existe um funcionário com este email");
        }
        employeeDTO.password = this.encrypter.hash(employeeDTO.password);
        return this.employeeRepository.create(employeeDTO);
    }
}
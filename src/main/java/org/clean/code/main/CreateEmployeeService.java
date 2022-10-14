package org.clean.code.main;

import org.clean.code.data.interfaces.EmployeeRepository;
import org.clean.code.data.interfaces.Encrypter;
import org.clean.code.data.usecases.CreateEmployeeImpl;
import org.clean.code.domain.dto.CreateEmployeeDTO;
import org.clean.code.domain.models.Employee;
import org.clean.code.domain.usecases.CreateEmployee;
import org.clean.code.infrastructure.panache.repositories.PanacheEmployeeRepository;
import org.clean.code.infrastructure.utils.BCryptAdapter;

import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
public class CreateEmployeeService {

    CreateEmployee createEmployee;

    public CreateEmployeeService() {
        Encrypter encrypter = new BCryptAdapter();
        EmployeeRepository employeeRepository = new PanacheEmployeeRepository();
        this.createEmployee = new CreateEmployeeImpl(employeeRepository, encrypter);
    }

    @Transactional
    public Employee handle(CreateEmployeeDTO createEmployeeDTO) {
        return this.createEmployee.create(createEmployeeDTO);
    }
}
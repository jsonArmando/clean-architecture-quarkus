package org.clean.code.data.interfaces;

import org.clean.code.domain.dto.CreateEmployeeDTO;
import org.clean.code.domain.models.Employee;

public interface EmployeeRepository {
    Employee findByEmail(String email);
    Employee create(CreateEmployeeDTO employee);
}

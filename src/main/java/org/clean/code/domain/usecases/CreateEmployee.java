package org.clean.code.domain.usecases;

import org.clean.code.domain.dto.CreateEmployeeDTO;
import org.clean.code.domain.models.Employee;

public interface CreateEmployee {
    Employee create(CreateEmployeeDTO employee);
}

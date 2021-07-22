package by.bsu.zuevvlad.thirdlab.entityrepository.employee;

import by.bsu.zuevvlad.thirdlab.databaseconnection.DataBaseConnection;
import by.bsu.zuevvlad.thirdlab.entity.employee.Employee;
import by.bsu.zuevvlad.thirdlab.entityrepository.EntityRepository;

public final class EmployeeRepository extends EntityRepository<Employee>
{
    private EmployeeRepository(final DataBaseConnection<Employee> dataBaseConnection)
    {
        super(dataBaseConnection);
    }

    public static EmployeeRepository getEmployeeRepository(
            final DataBaseConnection<Employee> dataBaseConnection)
    {
        if(EmployeeRepository.employeeRepository == null)
        {
            synchronized(EmployeeRepository.class)
            {
                if(EmployeeRepository.employeeRepository == null)
                {
                    EmployeeRepository.employeeRepository = new EmployeeRepository(dataBaseConnection);
                }
            }
        }
        return EmployeeRepository.employeeRepository;
    }

    private static EmployeeRepository employeeRepository = null;
}

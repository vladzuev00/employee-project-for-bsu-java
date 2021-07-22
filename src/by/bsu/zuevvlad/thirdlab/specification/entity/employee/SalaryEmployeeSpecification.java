package by.bsu.zuevvlad.thirdlab.specification.entity.employee;

import by.bsu.zuevvlad.thirdlab.entity.employee.Employee;
import by.bsu.zuevvlad.thirdlab.specification.entity.EntitySpecification;
import by.bsu.zuevvlad.thirdlab.specification.exception.SpecificationCreatingException;
import by.bsu.zuevvlad.thirdlab.validator.entity.employee.ValidatorForPropertiesOfEmployee;

import java.math.BigDecimal;
import java.util.Objects;

public final class SalaryEmployeeSpecification implements EntitySpecification<Employee>
{
    private BigDecimal requiredSalary;

    public SalaryEmployeeSpecification()
    {
        this.requiredSalary = SalaryEmployeeSpecification.VALUE_OF_NOT_DEFINED_REQUIRED_SALARY;
    }

    private static final BigDecimal VALUE_OF_NOT_DEFINED_REQUIRED_SALARY = Employee.VALUE_OF_NOT_DEFINED_SALARY;

    public SalaryEmployeeSpecification(final BigDecimal requiredSalary)
    {
        if(!SalaryEmployeeSpecification.VALIDATOR_FOR_PROPERTIES_OF_EMPLOYEE.isValidSalary(requiredSalary))
        {
            throw new SpecificationCreatingException("Impossible to create object-specification of class '"
                    + this.getClass().getName() + "' with given not valid required salary. Given required "
                    + "salary: " + requiredSalary + ".");
        }
    }

    private static final ValidatorForPropertiesOfEmployee VALIDATOR_FOR_PROPERTIES_OF_EMPLOYEE
            = new ValidatorForPropertiesOfEmployee();

    public final void setRequiredSalary(final BigDecimal requiredSalary)
    {
        if(!SalaryEmployeeSpecification.VALIDATOR_FOR_PROPERTIES_OF_EMPLOYEE.isValidSalary(requiredSalary))
        {
            throw new SpecificationCreatingException("Setting new not valid required salary " +
                    "of object-specification of class '" + this.getClass().getName() + "' is not possible. "
                    + " Given required salary: " + requiredSalary + ".");
        }
        this.requiredSalary = requiredSalary;
    }

    public final BigDecimal getRequiredSalary()
    {
        return this.requiredSalary;
    }

    @Override
    public final boolean isMatch(final Employee researchEmployee)
    {
        final BigDecimal salaryOfResearchEmployee = researchEmployee.getSalary();
        return Objects.equals(this.requiredSalary, salaryOfResearchEmployee);
    }
}

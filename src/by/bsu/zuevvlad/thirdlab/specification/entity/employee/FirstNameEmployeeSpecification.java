package by.bsu.zuevvlad.thirdlab.specification.entity.employee;

import by.bsu.zuevvlad.thirdlab.entity.employee.Employee;
import by.bsu.zuevvlad.thirdlab.specification.entity.EntitySpecification;
import by.bsu.zuevvlad.thirdlab.specification.exception.SpecificationCreatingException;
import by.bsu.zuevvlad.thirdlab.validator.entity.employee.ValidatorForPropertiesOfEmployee;

import java.util.Objects;

public final class FirstNameEmployeeSpecification implements EntitySpecification<Employee>
{
    private String requiredFirstName;

    public FirstNameEmployeeSpecification()
    {
        this.requiredFirstName = FirstNameEmployeeSpecification.VALUE_OF_NOT_DEFINED_REQUIRED_FIRST_NAME;
    }

    private static final String VALUE_OF_NOT_DEFINED_REQUIRED_FIRST_NAME = Employee.VALUE_OF_NOT_DEFINED_FIRST_NAME;

    public FirstNameEmployeeSpecification(final String requiredFirstName)
    {
        if(!FirstNameEmployeeSpecification.VALIDATOR_FOR_PROPERTIES_OF_EMPLOYEE.isValidFirstName(requiredFirstName))
        {
            throw new SpecificationCreatingException("Impossible to create object-specification of class '"
                    + this.getClass().getName() + "' with given not valid required first name. Given required "
                    + "first name: " + requiredFirstName + ".");
        }
        this.requiredFirstName = requiredFirstName;
    }

    private static final ValidatorForPropertiesOfEmployee VALIDATOR_FOR_PROPERTIES_OF_EMPLOYEE
            = new ValidatorForPropertiesOfEmployee();

    public final void setRequiredFirstName(final String requiredFirstName)
    {
        if(!FirstNameEmployeeSpecification.VALIDATOR_FOR_PROPERTIES_OF_EMPLOYEE.isValidFirstName(requiredFirstName))
        {
            throw new SpecificationCreatingException("Setting new not valid required first name " +
                    "of object-specification of class '" + this.getClass().getName() + "' is not possible. "
                    + " Given required first name: " + requiredFirstName + ".");
        }
        this.requiredFirstName = requiredFirstName;
    }

    public final String getRequiredFirstName()
    {
        return this.requiredFirstName;
    }

    @Override
    public final boolean isMatch(final Employee researchEmployee)
    {
        final String firstNameOfResearchEmployee = researchEmployee.getFirstName();
        return Objects.equals(this.requiredFirstName, firstNameOfResearchEmployee);
    }
}

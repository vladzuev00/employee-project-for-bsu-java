package by.bsu.zuevvlad.thirdlab.specification.entity.employee;

import by.bsu.zuevvlad.thirdlab.entity.employee.Employee;
import by.bsu.zuevvlad.thirdlab.specification.entity.EntitySpecification;
import by.bsu.zuevvlad.thirdlab.specification.exception.SpecificationCreatingException;
import by.bsu.zuevvlad.thirdlab.validator.entity.employee.ValidatorForPropertiesOfEmployee;

import java.util.Objects;

public final class LastNameEmployeeSpecification implements EntitySpecification<Employee>
{
    private String requiredLastName;

    public LastNameEmployeeSpecification()
    {
        this.requiredLastName = LastNameEmployeeSpecification.VALUE_OF_NOT_DEFINED_REQUIRED_LAST_NAME;
    }

    private static final String VALUE_OF_NOT_DEFINED_REQUIRED_LAST_NAME = Employee.VALUE_OF_NOT_DEFINED_LAST_NAME;

    public LastNameEmployeeSpecification(final String requiredLastName)
    {
        if(!LastNameEmployeeSpecification.VALIDATOR_FOR_PROPERTIES_OF_EMPLOYEE.isValidLastName(requiredLastName))
        {
            throw new SpecificationCreatingException("Impossible to create object-specification of class '"
                    + this.getClass().getName() + "' with given not valid required last name. Given required "
                    + "last name: " + requiredLastName + ".");
        }
        this.requiredLastName = requiredLastName;
    }

    private static final ValidatorForPropertiesOfEmployee VALIDATOR_FOR_PROPERTIES_OF_EMPLOYEE
            = new ValidatorForPropertiesOfEmployee();

    public final void setRequiredLastName(final String requiredLastName)
    {
        if(!LastNameEmployeeSpecification.VALIDATOR_FOR_PROPERTIES_OF_EMPLOYEE.isValidLastName(requiredLastName))
        {
            throw new SpecificationCreatingException("Setting new not valid required last name " +
                    "of object-specification of class '" + this.getClass().getName() + "' is not possible. "
                    + " Given required last name: " + requiredLastName + ".");
        }
        this.requiredLastName = requiredLastName;
    }

    public final String getRequiredLastName()
    {
        return this.requiredLastName;
    }

    @Override
    public final boolean isMatch(final Employee researchEmployee)
    {
        final String lastNameOfResearchEmployee = researchEmployee.getLastName();
        return Objects.equals(this.requiredLastName, lastNameOfResearchEmployee);
    }
}

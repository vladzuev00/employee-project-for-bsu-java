package by.bsu.zuevvlad.thirdlab.specification.entity.employee;

import by.bsu.zuevvlad.thirdlab.entity.employee.Employee;
import by.bsu.zuevvlad.thirdlab.specification.entity.EntitySpecification;
import by.bsu.zuevvlad.thirdlab.specification.exception.SpecificationCreatingException;
import by.bsu.zuevvlad.thirdlab.validator.entity.employee.ValidatorForPropertiesOfEmployee;

import java.util.Objects;

public final class PatronymicEmployeeSpecification implements EntitySpecification<Employee>
{
    private String requiredPatronymic;

    public PatronymicEmployeeSpecification()
    {
        this.requiredPatronymic = PatronymicEmployeeSpecification.VALUE_OF_NOT_DEFINED_REQUIRED_PATRONYMIC;
    }

    private static final String VALUE_OF_NOT_DEFINED_REQUIRED_PATRONYMIC = Employee.VALUE_OF_NOT_DEFINED_PATRONYMIC;

    public PatronymicEmployeeSpecification(final String requiredPatronymic)
    {
        if(!PatronymicEmployeeSpecification.VALIDATOR_FOR_PROPERTIES_OF_EMPLOYEE.isValidPatronymic(requiredPatronymic))
        {
            throw new SpecificationCreatingException("Impossible to create object-specification of class '"
                    + this.getClass().getName() + "' with given not valid required patronymic. Given required "
                    + "patronymic: " + requiredPatronymic + ".");
        }
        this.requiredPatronymic = requiredPatronymic;
    }

    private static final ValidatorForPropertiesOfEmployee VALIDATOR_FOR_PROPERTIES_OF_EMPLOYEE
            = new ValidatorForPropertiesOfEmployee();

    public final void setRequiredPatronymic(final String requiredPatronymic)
    {
        if(!PatronymicEmployeeSpecification.VALIDATOR_FOR_PROPERTIES_OF_EMPLOYEE.isValidPatronymic(requiredPatronymic))
        {
            throw new SpecificationCreatingException("Setting new not valid required patronymic " +
                    "of object-specification of class '" + this.getClass().getName() + "' is not possible. "
                    + " Given required patronymic: " + requiredPatronymic + ".");
        }
        this.requiredPatronymic = requiredPatronymic;
    }

    public final String getRequiredPatronymic()
    {
        return this.requiredPatronymic;
    }

    @Override
    public final boolean isMatch(final Employee researchEmployee)
    {
        final String patronymicOfResearchEmployee = researchEmployee.getPatronymic();
        return Objects.equals(this.requiredPatronymic, patronymicOfResearchEmployee);
    }
}

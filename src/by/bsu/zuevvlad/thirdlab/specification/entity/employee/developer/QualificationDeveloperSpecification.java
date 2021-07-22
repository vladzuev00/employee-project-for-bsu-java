package by.bsu.zuevvlad.thirdlab.specification.entity.employee.developer;

import by.bsu.zuevvlad.thirdlab.entity.employee.Developer;
import by.bsu.zuevvlad.thirdlab.specification.entity.EntitySpecification;
import by.bsu.zuevvlad.thirdlab.specification.exception.SpecificationCreatingException;
import by.bsu.zuevvlad.thirdlab.validator.entity.employee.ValidatorForPropertiesOfDeveloper;

public final class QualificationDeveloperSpecification implements EntitySpecification<Developer>
{
    private Developer.DeveloperQualification requiredQualification;

    public QualificationDeveloperSpecification()
    {
        this.requiredQualification = QualificationDeveloperSpecification.VALUE_OF_NOT_DEFINED_QUALIFICATION;
    }

    private static final Developer.DeveloperQualification VALUE_OF_NOT_DEFINED_QUALIFICATION
            = Developer.VALUE_OF_NOT_DEFINED_QUALIFICATION;

    public QualificationDeveloperSpecification(final Developer.DeveloperQualification requiredQualification)
    {
        if(!QualificationDeveloperSpecification.VALIDATOR_FOR_PROPERTIES_OF_DEVELOPER
                .isValidQualification(requiredQualification))
        {
            throw new SpecificationCreatingException("Impossible to create object-specification of class '"
                    + this.getClass().getName() + "' with given not valid required qualification. Given required "
                    + "qualification: " + requiredQualification + ".");
        }
        this.requiredQualification = requiredQualification;
    }

    private static final ValidatorForPropertiesOfDeveloper VALIDATOR_FOR_PROPERTIES_OF_DEVELOPER
            = new ValidatorForPropertiesOfDeveloper();

    public final void setRequiredQualification(final Developer.DeveloperQualification requiredQualification)
    {
        if(!QualificationDeveloperSpecification.VALIDATOR_FOR_PROPERTIES_OF_DEVELOPER
                .isValidQualification(requiredQualification))
        {
            throw new SpecificationCreatingException("Setting new not valid required qualification " +
                    "of object-specification of class '" + this.getClass().getName() + "' is not possible. "
                    + " Given required qualification: " + requiredQualification + ".");
        }
        this.requiredQualification = requiredQualification;
    }

    public final Developer.DeveloperQualification getRequiredQualification()
    {
        return this.requiredQualification;
    }

    @Override
    public final boolean isMatch(final Developer researchDeveloper)
    {
        final Developer.DeveloperQualification qualificationOfResearchDeveloper = researchDeveloper.getQualification();
        return this.requiredQualification == qualificationOfResearchDeveloper;
    }
}

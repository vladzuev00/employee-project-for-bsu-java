package by.bsu.zuevvlad.thirdlab.specification.entity.employee.analyst;

import by.bsu.zuevvlad.thirdlab.entity.employee.Analyst;
import by.bsu.zuevvlad.thirdlab.specification.entity.EntitySpecification;
import by.bsu.zuevvlad.thirdlab.specification.exception.SpecificationCreatingException;
import by.bsu.zuevvlad.thirdlab.validator.entity.employee.ValidatorForPropertiesOfAnalyst;

import java.util.Objects;

public final class SubjectAreaAnalystSpecification implements EntitySpecification<Analyst>
{
    private String requiredSubjectArea;

    public SubjectAreaAnalystSpecification()
    {
        this.requiredSubjectArea = SubjectAreaAnalystSpecification.VALUE_OF_NOT_DEFINED_SUBJECT_AREA;
    }

    private static final String VALUE_OF_NOT_DEFINED_SUBJECT_AREA = Analyst.VALUE_OF_NOT_DEFINED_SUBJECT_AREA;

    public SubjectAreaAnalystSpecification(final String requiredSubjectArea)
    {
        if(!SubjectAreaAnalystSpecification.VALIDATOR_FOR_PROPERTIES_OF_ANALYST.isValidSubjectArea(requiredSubjectArea))
        {
            throw new SpecificationCreatingException("Impossible to create object-specification of class '"
                    + this.getClass().getName() + "' with given not valid required subject area. Given required "
                    + "subject area: " + requiredSubjectArea + ".");
        }
        this.requiredSubjectArea = requiredSubjectArea;
    }

    private static final ValidatorForPropertiesOfAnalyst VALIDATOR_FOR_PROPERTIES_OF_ANALYST
            = new ValidatorForPropertiesOfAnalyst();

    public final void setRequiredSubjectArea(final String requiredSubjectArea)
    {
        if(!SubjectAreaAnalystSpecification.VALIDATOR_FOR_PROPERTIES_OF_ANALYST.isValidSubjectArea(requiredSubjectArea))
        {
            throw new SpecificationCreatingException("Setting new not valid required subject area " +
                    "of object-specification of class '" + this.getClass().getName() + "' is not possible. "
                    + " Given required subject area: " + requiredSubjectArea + ".");
        }
        this.requiredSubjectArea = requiredSubjectArea;
    }

    public final String getRequiredSubjectArea()
    {
        return this.requiredSubjectArea;
    }

    @Override
    public final boolean isMatch(final Analyst researchAnalyst)
    {
        final String subjectAreaOfResearchAnalyst = researchAnalyst.getSubjectArea();
        return Objects.equals(this.requiredSubjectArea, subjectAreaOfResearchAnalyst);
    }
}

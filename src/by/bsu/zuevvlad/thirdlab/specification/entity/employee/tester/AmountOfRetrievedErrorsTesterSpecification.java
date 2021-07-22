package by.bsu.zuevvlad.thirdlab.specification.entity.employee.tester;

import by.bsu.zuevvlad.thirdlab.entity.employee.Tester;
import by.bsu.zuevvlad.thirdlab.specification.entity.EntitySpecification;
import by.bsu.zuevvlad.thirdlab.specification.exception.SpecificationCreatingException;
import by.bsu.zuevvlad.thirdlab.validator.entity.employee.ValidatorForPropertiesOfTester;

public final class AmountOfRetrievedErrorsTesterSpecification implements EntitySpecification<Tester>
{
    private int requiredAmountOfRetrievedErrors;

    public AmountOfRetrievedErrorsTesterSpecification()
    {
        this.requiredAmountOfRetrievedErrors
                = AmountOfRetrievedErrorsTesterSpecification.VALUE_OF_NOT_DEFINED_REQUIRED_AMOUNT_OF_RETRIEVED_ERRORS;
    }

    private static final int VALUE_OF_NOT_DEFINED_REQUIRED_AMOUNT_OF_RETRIEVED_ERRORS
            = Tester.VALUE_OF_NOT_DEFINED_AMOUNT_OF_NOT_RETRIEVED_ERRORS;

    public AmountOfRetrievedErrorsTesterSpecification(final int requiredAmountOfRetrievedErrors)
    {
        if(!AmountOfRetrievedErrorsTesterSpecification.VALIDATOR_FOR_PROPERTIES_OF_TESTER
                .isValidAmountOfRetrievedErrors(requiredAmountOfRetrievedErrors))
        {
            throw new SpecificationCreatingException("Impossible to create object-specification of class '"
                    + this.getClass().getName() + "' with given not valid required amount of retrieved errors. "
                    + "Given required amount of retrieved errors: " + requiredAmountOfRetrievedErrors + ".");
        }
        this.requiredAmountOfRetrievedErrors = requiredAmountOfRetrievedErrors;
    }

    private static final ValidatorForPropertiesOfTester VALIDATOR_FOR_PROPERTIES_OF_TESTER
            = new ValidatorForPropertiesOfTester();

    public final void setRequiredAmountOfRetrievedErrors(final int requiredAmountOfRetrievedErrors)
    {
        if(!AmountOfRetrievedErrorsTesterSpecification.VALIDATOR_FOR_PROPERTIES_OF_TESTER
                .isValidAmountOfRetrievedErrors(requiredAmountOfRetrievedErrors))
        {
            throw new SpecificationCreatingException("Impossible to create object-specification of class '"
                    + this.getClass().getName() + "' with given not valid required amount of retrieved errors. "
                    + " Given required amount of retrieved errors: " + requiredAmountOfRetrievedErrors + ".");
        }
        this.requiredAmountOfRetrievedErrors = requiredAmountOfRetrievedErrors;
    }

    public final int getRequiredAmountOfRetrievedErrors()
    {
        return this.requiredAmountOfRetrievedErrors;
    }

    @Override
    public final boolean isMatch(final Tester researchTester)
    {
        final int amountOfRetrievedErrorsOfResearchTester = researchTester.getAmountOfRetrievedErrors();
        return this.requiredAmountOfRetrievedErrors == amountOfRetrievedErrorsOfResearchTester;
    }
}

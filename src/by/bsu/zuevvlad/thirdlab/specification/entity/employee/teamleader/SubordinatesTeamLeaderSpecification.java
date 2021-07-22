package by.bsu.zuevvlad.thirdlab.specification.entity.employee.teamleader;

import by.bsu.zuevvlad.thirdlab.entity.employee.Employee;
import by.bsu.zuevvlad.thirdlab.entity.employee.TeamLeader;
import by.bsu.zuevvlad.thirdlab.specification.entity.EntitySpecification;
import by.bsu.zuevvlad.thirdlab.validator.entity.employee.ValidatorForPropertiesOfDeveloper;
import by.bsu.zuevvlad.thirdlab.validator.entity.employee.ValidatorForPropertiesOfTeamLeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubordinatesTeamLeaderSpecification implements EntitySpecification<TeamLeader>
{
    private List<Employee> requiredSubordinates;

    public SubordinatesTeamLeaderSpecification()
    {
        this.requiredSubordinates = new ArrayList<Employee>();
    }

    public SubordinatesTeamLeaderSpecification(final List<Employee> requiredSubordinates)
    {
        this.requiredSubordinates = requiredSubordinates;
    }

    private static final ValidatorForPropertiesOfTeamLeader VALIDATOR_FOR_PROPERTIES_OF_TEAM_LEADER
            = new ValidatorForPropertiesOfTeamLeader();

    public final void setRequiredSubordinates(final List<Employee> requiredSubordinates)
    {
        this.requiredSubordinates = requiredSubordinates;
    }

    public final List<Employee> getRequiredSubordinates()
    {
        return this.requiredSubordinates;
    }

    @Override
    public final boolean isMatch(final TeamLeader researchTeamLeader)
    {
        final List<Employee> subordinatesOfResearchTeamLeader = researchTeamLeader.getSubordinates();
        return Objects.equals(this.requiredSubordinates, subordinatesOfResearchTeamLeader);
    }
}

package by.bsu.zuevvlad.thirdlab.specification.entity;

import by.bsu.zuevvlad.thirdlab.entity.Entity;

@FunctionalInterface
public interface EntitySpecification<TypeOfEntity extends Entity>
{
    public abstract boolean isMatch(final TypeOfEntity researchEntity);
}

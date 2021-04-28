package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Should be a builder for {@link BaseEntity} fields
 */
public abstract class Criteria<T extends BaseEntity> {

    private final Collection<Predicate<T>> predicates = new ArrayList<>();

    private void addPredicate(Predicate<T> newPredicate) {
        predicates.add(newPredicate);
    }

    public Collection<Predicate<T>> getPredicates() {
        return predicates;
    }

}

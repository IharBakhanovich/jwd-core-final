package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {

    private final Collection<Predicate<CrewMember>> predicates = new ArrayList<>();

    private void addPredicate(Predicate<CrewMember> newPredicate) {
        predicates.add(newPredicate);
    }

    public Collection<Predicate<CrewMember>> getPredicates() {
        return predicates;
    }

    public static class Builder {
        private final CrewMemberCriteria crewMemberCriteria;

        public Builder() {
            crewMemberCriteria = new CrewMemberCriteria();
        }

        public Builder whereIdEquals(long id) {
            crewMemberCriteria.addPredicate(crewMember -> crewMember.getId() == id);
            return this;
        }

        public Builder whereRankEquals(Rank rank) {
            crewMemberCriteria.addPredicate(crewMember -> crewMember.getRank() == rank);
            return this;
        }

        public Builder whereRoleEquals(Role role) {
            crewMemberCriteria.addPredicate(crewMember -> crewMember.getRole() == role);
            return this;
        }

        public Builder whereNameStartsWith(String beginOfName) {
            crewMemberCriteria.addPredicate(crewMember -> crewMember.getName().startsWith(beginOfName));
            return this;
        }

        public Builder whereNameEndsWith(String endOfName) {
            crewMemberCriteria.addPredicate(crewMember -> crewMember.getName().endsWith(endOfName));
            return this;
        }

        public Builder whereCrewMemberIsReadyForNextMission() {
            crewMemberCriteria.addPredicate(CrewMember::isReadyForNextMission);
            return this;
        }

        public CrewMemberCriteria build() {
            return crewMemberCriteria;
        }
    }
}

package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.EntityFactory;

// do the same for other entities
public enum CrewMemberFactory implements EntityFactory<CrewMember> {
    INSTANCE;

    @Override
    public CrewMember create(Object... args) {
        return new CrewMember((Long) args[0], (String) args[1], (Role) args[2], (Rank) args[3], (Boolean) args[4]);
    }
}

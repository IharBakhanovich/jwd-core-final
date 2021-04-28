package com.epam.jwd.core_final.InputReaderUtil;

import com.epam.jwd.core_final.domain.BaseEntity;

import java.util.Collection;

public interface InputReader<T extends BaseEntity> {

    public Collection<T> fetchMembers();
}

package com.df.core.persist.jpa;

import java.util.List;

public interface NativeSQLCallback<T> {

    public T received(List<?> resultList);
}

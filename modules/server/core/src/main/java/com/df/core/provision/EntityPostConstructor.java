package com.df.core.provision;

public interface EntityPostConstructor{

    void processEntity(Object obj);

    boolean supportEntity(Object obj);
}

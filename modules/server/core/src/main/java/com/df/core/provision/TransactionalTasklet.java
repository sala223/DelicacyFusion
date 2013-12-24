package com.df.core.provision;

import org.springframework.transaction.support.TransactionCallback;

public interface TransactionalTasklet extends TransactionCallback<Object>{

    void afterTransaction();
}

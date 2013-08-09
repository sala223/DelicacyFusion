package com.df.core.persist.eclipselink;

import java.io.IOException;

import javax.persistence.PersistenceException;

import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.sessions.SessionEvent;
import org.eclipse.persistence.sessions.SessionEventAdapter;
import org.springframework.util.StringUtils;

import com.df.core.common.context.TenantContext;
import com.df.core.common.context.TenantContextHolder;
import com.df.core.common.entity.MultiTenantSupport; 

public class TenantInjectionSessionEventListener extends SessionEventAdapter {

    @Override
    public void postAcquireClientSession(SessionEvent event) {
	try {
	    injectTenantProperty(event);
	} catch (IOException ex) {
	    throw new PersistenceException(ex);
	}
    }

    @Override
    public void preBeginTransaction(SessionEvent event) {
	try {
	    injectTenantProperty(event);
	} catch (IOException ex) {
	    throw new PersistenceException(ex);
	}
    }

    protected void injectTenantProperty(SessionEvent event) throws IOException {
	Session session = event.getSession();
	TenantContext tenant = TenantContextHolder.getTenant();
	if (tenant == null) {
	    session.getLog().write("No tenant context is bounded to current session");
	    return;
	}
	Object value = session.getProperties().get(MultiTenantSupport.MULTITENANT_CONTEXT_PROPERTY);
	if (value != null) {
	    String currentTenantId = value.toString();
	    if (!StringUtils.isEmpty(currentTenantId) && !currentTenantId.equals(tenant.getTenantId())) {
		String fmt = "TenantId %s is bounded to current session, you cannot change it to %s ";
		throw new TenantAlreadyBoundException(String.format(fmt, currentTenantId, tenant.getTenantId()));
	    }
	} else {
	    session.getLog().write("Inject TenantId=" + tenant.getTenantId() + " to session");
	    session.setProperty(MultiTenantSupport.MULTITENANT_CONTEXT_PROPERTY, tenant.getTenantId());
	}
    }
}

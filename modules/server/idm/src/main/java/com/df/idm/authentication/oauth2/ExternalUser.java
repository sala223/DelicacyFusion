package com.df.idm.authentication.oauth2;

import com.df.idm.entity.ExternalUserReference.Provider;
import com.df.idm.entity.User;

public interface ExternalUser {

	String getId();

	User consolidate(User user);

	String getAccessToken();

	Provider getProvider();

}

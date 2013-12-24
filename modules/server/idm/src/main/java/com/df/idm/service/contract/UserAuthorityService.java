package com.df.idm.service.contract;

import com.df.idm.entity.RoleId;
import com.df.idm.entity.User;

public interface UserAuthorityService {

	public void assign(User user, RoleId role);
	
	public void revoke(User user, RoleId role);
}

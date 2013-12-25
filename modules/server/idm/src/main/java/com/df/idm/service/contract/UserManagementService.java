package com.df.idm.service.contract;

import com.df.idm.entity.User;

public interface UserManagementService {

	User createUser(User user);

	User updateUser(User user);

	boolean verifyUserEmail(String token);

	void updatePassword(String code, String oldPassword, String newPassword);

	User getUserByWeiboAccount(String weiboAccount);

	User getUserByEmail(String mail);

	User getUserById(long userId);

	User getUserByCellPhone(String cellPhone);

	void deleteUser(long userId);

	User addUserToTenant(long userId, String tenantCode);

	User removeUserFromTenant(long userId, String tenantCode);
}

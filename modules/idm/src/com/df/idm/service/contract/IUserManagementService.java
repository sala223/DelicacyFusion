package com.df.idm.service.contract;

import com.df.idm.entity.User;

public interface IUserManagementService {

	void createUser(User user);

	void updateUser(User user);

	void updatePassword(String code, String newPassword);

	User getUserByWeiboAccount(String weiboAccount);

	User getUserByEmail(String mail);

	User getUserByTelephone(String telephone);

	void deleteUser(long userId);
}

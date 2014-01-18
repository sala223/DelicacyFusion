package com.df.idm.authentication.oauth2.sina;

import com.df.idm.authentication.oauth2.ExternalUser;
import com.df.idm.entity.ExternalUserReference;
import com.df.idm.entity.ExternalUserReference.Provider;
import com.df.idm.entity.User;

public class SinalUser implements ExternalUser {

	private String uid;

	private String screenName;

	private String name;

	private String profileImageUrl;

	private String accessToken;

	public SinalUser(String uid, String accessToken) {
		this.uid = uid;
		this.accessToken = accessToken;
	}

	@Override
	public String getId() {
		return this.uid;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public User consolidate(User user) {
		if (user == null) {
			user = new User();
			ExternalUserReference reference = new ExternalUserReference(Provider.SINA, this.uid);
			user.addOrUpdateExternalReference(reference);
		}
		user.setNickName(this.getScreenName());
		return user;
	}

	public String getScreenName() {
		return screenName;
	}

	@Override
	public Provider getProvider() {
		return Provider.SINA;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
}

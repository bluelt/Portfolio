package MockModule.domain.entity.account;

import java.util.Locale;


public class User {

	public User( String userId ) {
	}

	public boolean isUseCustomFriendName() {
		return false;
	}

	public String getFullName( Locale locale ) {
		return null;
	}

	public String getUserId() {
		return null;
	}

	public String getEmail() {
		return null;
	}

	public String getLocale() {
		return null;
	}
}

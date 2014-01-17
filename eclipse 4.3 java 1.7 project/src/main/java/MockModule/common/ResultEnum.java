package MockModule.common;

import java.util.Locale;

public enum ResultEnum {
	//success
	OK( "ok", 0 ),
	FRIENDNEEDSYNC( "friend_need_sync", 403 );

	int code;
	String resourceKey;

	private ResultEnum( String resourceKey, int code ) {
	}

	public int getCode() {
		return 0;
	}

	public String getMessage() {
		return null;
	}

	public String getMessage( Locale locale ) {
		return null;
	}
}

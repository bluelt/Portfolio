package PortfolioModule.service.friend.value;

import MockModule.exception.InvalidParamException;
import MockModule.util.validator.StringValidator;

public class FriendServiceRequest {

	int revision;
	String userId;
	String deviceId;
	
	public FriendServiceRequest() {
	}
	
	public FriendServiceRequest( int revision, String userId, String deviceId ) {
		this.revision = revision;
		this.userId = userId;
		this.deviceId = deviceId;
	}

	public void validate() {
		if ( revision < 0 || StringValidator.isValidString( userId ) == false || StringValidator.isValidString( deviceId ) == false )
			throw new InvalidParamException();
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision( int revision ) {
		this.revision = revision;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId( String userId ) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId( String deviceId ) {
		this.deviceId = deviceId;
	}

}

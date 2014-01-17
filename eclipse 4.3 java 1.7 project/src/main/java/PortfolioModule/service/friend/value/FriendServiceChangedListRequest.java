package PortfolioModule.service.friend.value;

import MockModule.domain.AuthedSessionInfo;


public class FriendServiceChangedListRequest extends FriendServiceRequest {

	public FriendServiceChangedListRequest() {
	}

	public FriendServiceChangedListRequest( int revision, AuthedSessionInfo info ) {
		super( revision, info.getUserId(), info.getDeviceId() );
	}
}

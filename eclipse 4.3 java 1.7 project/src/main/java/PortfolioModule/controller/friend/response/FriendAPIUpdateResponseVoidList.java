package PortfolioModule.controller.friend.response;

import PortfolioModule.service.friend.value.FriendServiceUpdateResult;

public class FriendAPIUpdateResponseVoidList extends FriendAPIUpdateResponse<Void> {

	public FriendAPIUpdateResponseVoidList() {
	}
	
	public FriendAPIUpdateResponseVoidList( FriendServiceUpdateResult<Void> result ) {
		this.revision = result.getRevision();
	}
}

package PortfolioModule.controller.friend.response;

import PortfolioModule.domain.dto.FriendDTO;
import PortfolioModule.service.friend.value.FriendServiceUpdateResult;

public class FriendAPIUpdateResponseFriendList extends FriendAPIUpdateResponse<FriendDTO> {

	public FriendAPIUpdateResponseFriendList() {
	}
	
	public FriendAPIUpdateResponseFriendList( FriendServiceUpdateResult<FriendDTO> result ) {
		this.revision = result.getRevision();
		this.list = result.getList();
	}

}

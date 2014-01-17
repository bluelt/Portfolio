package PortfolioModule.service.friend.value;

import MockModule.domain.AuthedSessionInfo;
import MockModule.exception.InvalidParamException;
import PortfolioModule.controller.friend.request.FriendListRequest;

public class FriendServiceListRequest extends FriendServiceRequest {

	/**
	 * 1~N
	 */
	int page;
	/**
	 * 한 page 당 친구 수
	 */
	int count;
	
	public FriendServiceListRequest() {
	}
	
	public FriendServiceListRequest( FriendListRequest request, AuthedSessionInfo session ) {
		super( request.getRevision(), session.getUserId(), session.getDeviceId() );
		this.page = request.getPage();
		this.count = request.getCount();
	}
	
	public void validate() {
		
		super.validate();
		if ( page < 1 || count < 1 )
			throw new InvalidParamException();
	}

	public int getPage() {
		return page;
	}

	public void setPage( int page ) {
		this.page = page;
	}

	public int getCount() {
		return count;
	}

	public void setCount( int count ) {
		this.count = count;
	}

}

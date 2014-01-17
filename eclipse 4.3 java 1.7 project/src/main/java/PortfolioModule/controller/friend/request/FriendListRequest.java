package PortfolioModule.controller.friend.request;

import MockModule.controller.APIRequest;
import MockModule.exception.InvalidParamException;
import PortfolioModule.exception.friend.FriendAPIAttributeViolationException;
import PortfolioModule.service.friend.value.FriendServiceAttribute;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FriendListRequest implements APIRequest {

	@JsonProperty("r")
	int revision;
	/**
	 * page, 1~N
	 */
	@JsonProperty("p")
	int page;
	/**
	 * count per page
	 */
	@JsonProperty("c")
	int count;
	
	public FriendListRequest() {
	}
	
	@Override
	public void validate() {
		
		if ( revision < 0 || page < 1 || count < 1 )
			throw new InvalidParamException();
		
		if ( count > FriendServiceAttribute.MAX_FRIEND_COUNT_PER_PAGE )
			throw new FriendAPIAttributeViolationException();
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision( int revision ) {
		this.revision = revision;
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

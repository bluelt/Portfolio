package PortfolioModule.controller.friend.request;

import MockModule.controller.APIRequest;
import MockModule.exception.InvalidParamException;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FriendChangedListRequest implements APIRequest {

	@JsonProperty("r")
	int revision;
	
	public FriendChangedListRequest() {
	}
	
	@Override
	public void validate() {
		if ( revision < 0 )
			throw new InvalidParamException();
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision( int revision ) {
		this.revision = revision;
	}

}

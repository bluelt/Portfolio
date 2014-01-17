package PortfolioModule.controller.friend.response;

import MockModule.controller.APIResponse;
import PortfolioModule.service.friend.value.FriendRevisionInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FriendRevisionResponse extends APIResponse {

	@JsonProperty("r")
	int revision = 0;
	@JsonProperty("c")
	int count = 0;
	
	public FriendRevisionResponse() {
	}

	public FriendRevisionResponse( FriendRevisionInfo info ) {
		this.revision = info.getRevision();
		this.count = info.getCount();
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision( int revision ) {
		this.revision = revision;
	}

	public int getCount() {
		return count;
	}

	public void setCount( int count ) {
		this.count = count;
	}

}

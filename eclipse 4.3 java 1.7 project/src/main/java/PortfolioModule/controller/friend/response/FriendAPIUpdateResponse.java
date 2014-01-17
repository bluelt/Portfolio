package PortfolioModule.controller.friend.response;

import java.util.List;

import MockModule.controller.APIResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_DEFAULT)
public class FriendAPIUpdateResponse<T> extends APIResponse {

	@JsonProperty("r")
	int revision;
	@JsonProperty("l")
	List<T> list;
	
	public FriendAPIUpdateResponse() {
	}
	
	public int getRevision() {
		return revision;
	}

	public void setRevision( int revision ) {
		this.revision = revision;
	}

	public List<T> getList() {
		return list;
	}

	public void setList( List<T> list ) {
		this.list = list;
	}

}

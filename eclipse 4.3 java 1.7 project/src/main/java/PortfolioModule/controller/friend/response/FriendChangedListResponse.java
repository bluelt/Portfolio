package PortfolioModule.controller.friend.response;

import java.util.List;

import MockModule.controller.APIResponse;
import PortfolioModule.domain.dto.FriendDTO;
import PortfolioModule.service.friend.value.FriendServiceChangedListResult;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FriendChangedListResponse extends APIResponse {

	@JsonProperty("r")
	int revision;
	@JsonProperty("l")
	List<FriendDTO> list;
	
	public FriendChangedListResponse() {
	}
	
	public FriendChangedListResponse( FriendServiceChangedListResult result ) {
		this.revision = result.getRevision();
		this.list = result.getList();
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision( int revision ) {
		this.revision = revision;
	}

	public List<FriendDTO> getList() {
		return list;
	}

	public void setList( List<FriendDTO> list ) {
		this.list = list;
	}

}

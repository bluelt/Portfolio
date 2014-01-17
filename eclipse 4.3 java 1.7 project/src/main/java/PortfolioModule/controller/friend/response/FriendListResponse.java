package PortfolioModule.controller.friend.response;

import java.util.List;

import MockModule.controller.APIResponse;
import PortfolioModule.domain.dto.FriendDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_DEFAULT)
public class FriendListResponse extends APIResponse {

	@JsonProperty("l")
	List<FriendDTO> list;
	
	public FriendListResponse() {
	}
	
	public FriendListResponse( List<FriendDTO> list ) {
		this.list = list;
	}

	public List<FriendDTO> getList() {
		return list;
	}

	public void setList( List<FriendDTO> list ) {
		this.list = list;
	}

}

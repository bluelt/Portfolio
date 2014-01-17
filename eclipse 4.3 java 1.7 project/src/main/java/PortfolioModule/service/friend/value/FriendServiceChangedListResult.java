package PortfolioModule.service.friend.value;

import java.util.List;

import PortfolioModule.domain.dto.FriendDTO;

public class FriendServiceChangedListResult {

	int revision;
	List<FriendDTO> list;
	
	public FriendServiceChangedListResult() {
	}
	
	public FriendServiceChangedListResult( int revision, List<FriendDTO> list ) {
		this.revision = revision;
		this.list = list;
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

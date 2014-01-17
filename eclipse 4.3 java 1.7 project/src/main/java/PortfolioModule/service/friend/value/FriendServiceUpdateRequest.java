package PortfolioModule.service.friend.value;

import java.util.Set;

import MockModule.exception.InvalidParamException;
import PortfolioModule.domain.enumtype.friend.FriendUpdateType;

public class FriendServiceUpdateRequest<T> extends FriendServiceRequest {

	FriendUpdateType type;
	Set<T> list;
	
	public FriendServiceUpdateRequest() {
	}
	
	public void validate() {
		if ( type == null || list == null || list.size() < 1 )
			throw new InvalidParamException();
	}

	public FriendUpdateType getType() {
		return type;
	}

	public void setType( FriendUpdateType type ) {
		this.type = type;
	}

	public Set<T> getList() {
		return list;
	}

	public void setList( Set<T> list ) {
		this.list = list;
	}

}

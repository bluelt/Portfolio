package PortfolioModule.controller.friend.request;

import java.util.Set;

import MockModule.controller.APIRequest;
import MockModule.exception.InvalidParamException;
import PortfolioModule.exception.friend.FriendAPIAttributeViolationException;
import PortfolioModule.service.friend.value.FriendServiceAttribute;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FriendAPIUpdateRequest<T> implements APIRequest {

	@JsonProperty("r")
	int revision = -1;
	@JsonProperty("l")
	Set<T> list;
	
	public FriendAPIUpdateRequest() {
	}
	
	@Override
	public void validate() {
		if ( revision < 0 || list == null)
			throw new InvalidParamException();
		
		if ( list.size() > FriendServiceAttribute.MAX_FRIEND_COUNT_PER_UPDATE )
			throw new FriendAPIAttributeViolationException();
	}

	public Set<T> getList() {
		return list;
	}

	public void setList( Set<T> list ) {
		this.list = list;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision( int revision ) {
		this.revision = revision;
	}

}

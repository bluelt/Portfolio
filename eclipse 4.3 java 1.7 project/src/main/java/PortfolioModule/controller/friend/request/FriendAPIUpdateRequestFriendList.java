package PortfolioModule.controller.friend.request;

import java.util.Iterator;

import MockModule.util.validator.StringValidator;
import PortfolioModule.domain.dto.FriendDTO;

public class FriendAPIUpdateRequestFriendList extends FriendAPIUpdateRequest<FriendDTO> {

	public FriendAPIUpdateRequestFriendList() {
	}
	
	@Override
	public void validate() {
		
		super.validate();

		Iterator<FriendDTO> iterator = list.iterator();
		while ( iterator.hasNext() ) {
			FriendDTO friend = iterator.next();
			if ( StringValidator.isEmailString( friend.getEmail() ) == false )
				iterator.remove();
		}
	}

}

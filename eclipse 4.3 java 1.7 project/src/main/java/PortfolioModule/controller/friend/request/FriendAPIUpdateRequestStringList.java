package PortfolioModule.controller.friend.request;

import java.util.Iterator;

import MockModule.util.validator.StringValidator;

public class FriendAPIUpdateRequestStringList extends FriendAPIUpdateRequest<String> {

	public FriendAPIUpdateRequestStringList() {
	}
	
	@Override
	public void validate() {

		super.validate();
		
		Iterator<String> iterator = list.iterator();
		while ( iterator.hasNext() ) {
			String phoneNumber = iterator.next();
			if ( StringValidator.isPhoneNumberString( phoneNumber ) == false )
				iterator.remove();
		}
	}

}

package PortfolioModule.controller.friend.request;

import java.util.Iterator;

public class FriendAPIUpdateRequestLongList extends FriendAPIUpdateRequest<Long> {

	@Override
	public void validate() {

		super.validate();
		
		Iterator<Long> iterator = list.iterator();
		while ( iterator.hasNext() ) {
			long id = iterator.next();
			if ( id <= 0 )
				iterator.remove();
		}
	}

}

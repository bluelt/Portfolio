package PortfolioModule.service.friend.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import MockModule.domain.entity.account.User;
import PortfolioModule.domain.entity.friend.Friend;
import PortfolioModule.domain.enumtype.friend.FriendUpdateType;
import PortfolioModule.service.friend.value.FriendServiceUpdateResult;

@Component
public class FriendDeleteLastSendTimeHandler extends AbstractFriendUpdateHandler<Void, Long> {

	@Override
	FriendUpdateType getRevisionType() {
		return FriendUpdateType.DELETE_LASTSENDTIME;
	}

	@Override
	Collection<Friend> handleInternal( User owner, Set<Long> requests ) {
		//요청에 해당하는 friend를 가져온다.
		List<Friend> list = friendDao.getFriendsByID( owner.getUserId(), requests );
		
		//설정한다. 실제로 변경된 것만 리스트에 담는다.
		List<Friend> modifieds = new ArrayList<Friend>();
		for ( Friend friend : list ) {
			if ( friend.getLastSendTime() > 0 ) { //기존 값과 현재 설정하려는 값이 다른 경우
				friend.setLastSendTime( 0 );
				modifieds.add( friend );
			}
		}
		
		return modifieds;
	}

	@Override
	void buildResultInternal( User owner, FriendServiceUpdateResult<Void> result, Collection<Friend> modifieds ) {
	}

}

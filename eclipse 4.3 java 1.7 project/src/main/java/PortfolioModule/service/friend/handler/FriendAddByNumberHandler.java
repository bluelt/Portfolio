package PortfolioModule.service.friend.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.LocaleUtils;
import org.springframework.stereotype.Component;

import MockModule.domain.entity.account.User;
import PortfolioModule.domain.dto.FriendDTO;
import PortfolioModule.domain.entity.friend.Friend;
import PortfolioModule.domain.enumtype.friend.FriendUpdateType;
import PortfolioModule.service.friend.value.FriendServiceUpdateResult;

@Component
public class FriendAddByNumberHandler extends AbstractFriendUpdateHandler<FriendDTO, String>{

	@Override
	FriendUpdateType getRevisionType() {
		return FriendUpdateType.ADD;
	}

	@Override
	Collection<Friend> handleInternal( User owner, Set<String> requests ) {
		
		//전화번호에 해당하는 사용자 정보를 가져온다.
		Map<String, List<User>> existUsers = accountService.findUsersByPhoneNo( new ArrayList<String>( requests ) );

		//사용자 목록을 만들고,사용자들에게서 email을 추출한다.
		Set<User> users = new HashSet<User>();
		Set<String> emails = new HashSet<String>(); 
		for ( List<User> userList : existUsers.values() ) {
			for ( User user : userList ) {
				emails.add( user.getEmail() );
				users.add( user );
			}
		}

		//전화번호에 해당하는 사용자 중 이미 친구로 추가되어 있는 정보를 가져온다.
		Map<String, Friend> existFriends = friendDao.getFriendMapByEmail( owner.getUserId(), emails );
		
		//요청된 전화번호가 사용자에 해당하는 경우 중, 이미 친구로 추가되어 있는 항목은 제거하고, 아닐 경우 새로운 요청으로 추가한다.
		List<Friend> addedList = new ArrayList<Friend>();
		Iterator<User> iterator = users.iterator();
		Locale locale = LocaleUtils.toLocale( owner.getLocale() );
		while ( iterator.hasNext() ) {
			User user = iterator.next();
			Friend exist = existFriends.get( user.getEmail() );
			if ( exist != null )
				continue;
			
			Friend friend = new Friend( owner, user.getFullName( locale ) );
			friend.setFriendUser( user );
			addedList.add( friend );
		}
		
		//추가할 요청이 존재하는 경우 새 친구로 추가한다.
		for ( Friend friend : addedList )
			friendDao.add( friend );
		
		return addedList;
	}

	@Override
	void buildResultInternal( User owner, FriendServiceUpdateResult<FriendDTO> result, Collection<Friend> modifieds ) {
		
		boolean useCustomName = owner.isUseCustomFriendName();
		Locale locale = LocaleUtils.toLocale( owner.getLocale() );

		for ( Friend friend : modifieds ) {
			result.addResult( new FriendDTO( friend, useCustomName, locale ) );
		}
		
	}
	
}

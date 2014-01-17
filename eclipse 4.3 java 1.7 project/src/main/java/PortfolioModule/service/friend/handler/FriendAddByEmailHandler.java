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
public class FriendAddByEmailHandler extends AbstractFriendUpdateHandler<FriendDTO, FriendDTO> {

	@Override
	FriendUpdateType getRevisionType() {
		return FriendUpdateType.ADD;
	}

	@Override
	Collection<Friend> handleInternal( User owner, Set<FriendDTO> requests ) {
		
		//요청 항목에서 email 목록만 추출한다.
		Set<String> requestEmails = getEmails( requests );
		
		//요청된 email 중 PO 사용자에 해당하는 정보를 가져온다.
		Map<String, User> existUsers = accountService.findUsersByEmail( new ArrayList<String>( requestEmails) );
		
		//요청된 email 중 이미 친구로 추가되어 있는 정보를 가져온다.
		Map<String, Friend> existFriends = friendDao.getFriendMapByEmail( owner.getUserId(), requestEmails );
		
		//요청된 email 중 이미 친구로 추가되어 있는 항목은 제거한다.
		//또한, 요청된 email이 PO 사용자 email일 경우 해당 정보를 설정해 둔다.
		Iterator<FriendDTO> iterator = requests.iterator();
		while ( iterator.hasNext() ) {
			FriendDTO dto = iterator.next();
			if ( dto.getEmail().equals( owner.getEmail() ) ) //자기 자신을 친구로 추가하지 못하도록 처리
				continue;
			dto.setFriendUser( existUsers.get( dto.getEmail() ) );
			Friend exist = existFriends.get( dto.getEmail() );
			if ( exist != null )
				iterator.remove();
		}
		
		//이미 친구로 추가되어 있지 않은 요청만 새 친구로 추가한다. 
		List<Friend> addedList = new ArrayList<>();
		for ( FriendDTO dto : requests ) {
			Friend friend = new Friend( owner, dto.getName() );
			if ( dto.getFriendUser() != null )
				friend.setFriendUser( dto.getFriendUser() );
			else
				friend.setNonuserEmail( dto.getEmail() );
			friendDao.add( friend );
			addedList.add( friend );
		}
		
		return addedList;
	}

	@Override
	void buildResultInternal( User owner, FriendServiceUpdateResult<FriendDTO> result, Collection<Friend> modifieds ) {
		
		boolean useCustomName = owner.isUseCustomFriendName();
		Locale locale = LocaleUtils.toLocale( owner.getLocale() );
			
		for ( Friend friend : modifieds )
			result.addResult( new FriendDTO( friend, useCustomName, locale ) );
	}
	
	/**
	 * 요청 Set에서 email String만 List로 추출한다.
	 */
	public Set<String> getEmails( Set<FriendDTO> set ) {
		
		Set<String> emails = new HashSet<String>();
		for ( FriendDTO friend : set ) {
			emails.add( friend.getEmail() );
		}
		
		return emails;
	}
}

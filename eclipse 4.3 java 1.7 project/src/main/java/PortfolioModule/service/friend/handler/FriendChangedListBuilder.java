package PortfolioModule.service.friend.handler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import PortfolioModule.domain.entity.friend.Friend;
import PortfolioModule.domain.entity.friend.FriendRevision;
import PortfolioModule.domain.enumtype.friend.FriendUpdateType;
import PortfolioModule.service.friend.value.FriendServiceAttribute;

/**
 * 주소록의 변경 사항을 최적화
 * persistent layer는 접근하지 않고 로직만 처리한다.
 */
public class FriendChangedListBuilder {

	/**
	 * service로 부터 FriendRevision이 전달될 때 마다 증가하며, 한 응답의 최대 개수를 초과하는지 검사하는데 사용되는 임시 저장소이다. 
	 * 최적화 이전의 개수이기 때문에 실제 개수와는 달라질 수 있다.
	 */
	int count = 0;
	
	/**
	 * 응답으로 전달 될 변경사항 중 가장 마지막 revision.
	 */
	int revision = 0;
	
	Map<FriendUpdateType, Set<Long>> idRepository = new HashMap<FriendUpdateType, Set<Long>>();;
	
	public FriendChangedListBuilder() {
	}
	
	/**
	 * 최적화 이전에, 응답으로 전달될 것으로 예상되는 모든 id를 반환한다.
	 * @return
	 */
	public Set<Long> getCandidateIDs() {
		
		Set<Long> result = new HashSet<Long>();
		for ( Set<Long> ids : idRepository.values()  ) {
			result.addAll( ids );
		}
		
		return result;
	}
	
	/**
	 * 처리할 revision을 추가한다. 
	 * @param revision
	 * @return boolean false가 전달 될 경우 허용량이 초과되어 더 이상 추가할 수 없는 상태이므로 put을 중단한다.
	 */
	public boolean put( FriendRevision revision ) {
		
		if ( ( count + revision.getIdSet().size() ) > FriendServiceAttribute.MAX_FRIEND_COUNT_PER_CHANGEDLIST )
			return false;
		
		addToRepository( revision );
		return true;
	}
	
	/**
	 * FriendUpdateType에 해당하는 set에 id들을 추가한다.
	 * @param revision
	 */
	private void addToRepository( FriendRevision revision ) {
		
		this.revision = revision.getPk().getRevision();
		
		Set<Long> idSet = getIDSet( revision.getType() );
		for ( Long id : revision.getIdSet() ) {
			if ( idSet.add( id ) )
				count++;
		}
	}

	private Set<Long> getIDSet( FriendUpdateType type ) {
		
		Set<Long> set = idRepository.get( type );
		if ( set == null ) {
			set = new HashSet<Long>();
			idRepository.put( type, set );
		}
		
		return set;
	}

	/**
	 * 변경 사항을 최적화 한다.  즉 응답할 Friend 개수를 최소화한다. 
	 * 
	 * show,hide 등 add가 아닌 경우,  id와 변경된 property만을 내려주는 것이 network비용을 최소화 할 수 있지만,
	 * 일반 사용자의 경우 주소록의 변경이 빈번하지 않으므로, 큰 효과를 볼 수 없다고 판단되어 구현을 단순화 하였다.  
	 * @param friends
	 * @return
	 */
	public Set<Friend> build( List<Friend> friends ) {
		
		//service로 부터 전달된 list를 탐색하기 좋도록 map으로 변경한다.
		Map<Long, Friend> friendMap = new HashMap<Long, Friend>();
		for ( Friend friend : friends )
			friendMap.put( friend.getId(), friend );
		
		Set<Friend> result = new HashSet<Friend>();
		
		//ADD에 해당하는 항목은 무조건 전달되어야 하므로, 우선적으로 result에 추가한다.
		for ( Long id : getIDSet( FriendUpdateType.ADD ) ) {
			Friend friend = friendMap.get( id );
			if ( friend != null )
				result.add( friend );
		}
		
		for ( FriendUpdateType type : idRepository.keySet() ) {
			if ( type == FriendUpdateType.ADD ) //ADD는 이미 처리되었으므로 넘어간다.
				continue;
			
			for ( Long id : idRepository.get( type ) ) {
				Friend friend = friendMap.get( id );
				if ( result.contains( friend ) ) //이미 결과에 포함된 것은 넘어간다.
					continue;
				
				if ( isValid( type, friend ) ) //결과에 포함시킬 필요가 있는 것만 포함한다.
					result.add( friend );
			}
		}
		
		return result;
	}
	
	/**
	 * 적용된 변경 사항 중 가장 큰 리비전을 반환한다. 
	 * @return
	 */
	public int getRevision() {
		return revision;
	}
	
	/**
	 * type에 대해 friend의 변경사항이 유효하여 결과에 추가해야 할지 검사한다.<br/>
	 * ex ) type이 SHOW인데 Friend의 show값이 false라면, 이후 HIDE의 revision이 추가되어 SHOW는 무효화 된 것과 같으므로 결과에는 포함시킬 필요가 없다.<br/>
	 * ex) type이 delete last send time인데, Friend의 last send time이 0 이상이라면, 이후 last send time이 갱신된 것이므로 결과에는 포함할 필요가 없다.<br/> 
	 * @param type
	 * @param friend
	 * @return
	 */
	private boolean isValid( FriendUpdateType type, Friend friend ) {
		
		switch ( type ) {
		case DELETE_LASTSENDTIME:
			return ! ( friend.getLastSendTime() > 0 );

		case HIDE:
			return ! ( friend.isShow() );
			
		case SHOW:
			return friend.isShow();

		case UPDATE_LASTSENDTIME:
			return friend.getLastSendTime() > 0;

		case ADD: //ADD는 이미 처리되어 전달되지 않는다. 
		case ADDBYNUMBER: //ADDBYNUMBER는 요청에만 사용되고 revision에는 ADD로 기록된다.
		default : 
			break;
		}
		
		return true;
	}
}


package PortfolioModule.persistent.friend;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import PortfolioModule.domain.entity.friend.Friend;
import PortfolioModule.domain.entity.friend.FriendRevision;

@Repository
public interface FriendDao {

	public void add( Friend friend );
	
	//v1.1 method
	/**
	 * 해당 사용자의 최신 revision을 전달한다.
	 * @param userId
	 * @return
	 */
	public int getLastRevision( String userId );
	
	/**
	 * 해당 사용자의 총 친구 수를 전달한다.
	 * @param userId
	 * @return
	 */
	public int getTotalFriendCount( String userId ) ;
	
	/**
	 * emails에 해당하는 친구 map을 전달한다.
	 * 비사용자(Friend.email이 일치), 사용자(Friend.friendUser.email이 일치) 모두 전달한다. 
	 * @param userId
	 * @param emails
	 * @return map(email/Friend) 
	 */
	public Map<String, Friend> getFriendMapByEmail( String userId, Set<String> emails );
	
	/**
	 * persist FriendRevision
	 * @param revision
	 */
	public void add( FriendRevision revision );

	/**
	 * ID에 해당하는 친구 목록을 전달한다.
	 * @param userId
	 * @param ids
	 * @return
	 */
	public List<Friend> getFriendsByID( String userId, Set<Long> ids );
	
	/**
	 * 해당 사용자의 N page에서 N count 만큼의 친구 목록을 전달한다.
	 * @param userId
	 * @param page
	 * @param count
	 * @return
	 */
	public List<Friend> getFriendList( String userId, int page, int count );
	
	/**
	 * 해당 사용자의 min revision 이후 revision을 오름차순으로 얻어옴
	 * @param userId
	 * @param minRevision
	 * @return
	 */
	public List<FriendRevision> getRevisionList( String userId, int minRevision );
	
	/**
	 * 해당 사용자의 친구 중, id set에 해당하는 친구 목록을 반환한다.
	 * @param userId
	 * @param ids
	 * @return
	 */
	public List<Friend> getFriendList( String userId, Set<Long> ids );
}

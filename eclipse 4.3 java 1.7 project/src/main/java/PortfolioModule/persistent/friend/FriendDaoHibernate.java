package PortfolioModule.persistent.friend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import MockModule.exception.InvalidParamException;
import PortfolioModule.domain.entity.friend.Friend;
import PortfolioModule.domain.entity.friend.FriendRevision;

@Repository
public class FriendDaoHibernate implements FriendDao {

	@Autowired
	SessionFactory sessionFactory;

	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void add( Friend friend ) {
		session().saveOrUpdate(friend);
	}
	
	public void addIfDetached( Friend friend ) {
		if ( session().contains( friend ) == false )
			session().saveOrUpdate( friend );
	}

	public void setPolarisUserFriendAsNonUserFriend( String userId, String email ) {
		ScrollableResults result = null;
		Session session = session();
		try {
			// 1000개 단위로 로우를 db에서 가져와서 포워딩 온리로 속도를 최적화한 스크롤 결과 처리
			result = session.createCriteria( Friend.class )
					.add( Restrictions.eq( "friendUser.userId", userId ) )
					.setFetchSize( 1000 )
					.setCacheable( false )
					.scroll( ScrollMode.FORWARD_ONLY );
			
			int count = 0;
			// 50명씩 batch insert 처리
			int batchSize = 50;
			while( result.next() ) {
				Friend friend = (Friend) result.get()[0];
				friend.setNonuserEmail( email );
			
				if( ++count % batchSize == 0 ) {
					if( session.isDirty() ) {
						session.flush();
						session.clear();
					}
				}
			}
		} finally {
			if( result != null ) {
				result.close();
			}
		}
	}

	@Override
	public int getLastRevision( final String userId ) {
		
		final String qry = "from FriendRevision where pk.user.id = :userId order by pk.revision desc";
		Query query = session().createQuery( qry );
		query.setParameter( "userId", userId );
		query.setMaxResults( 1 );
		FriendRevision revision = (FriendRevision) query.uniqueResult();
		return revision == null ? 0 : revision.getPk().getRevision();
	}

	@Override
	public int getTotalFriendCount( String userId ) {
		
		final String qry = "select count(id) from Friend where user.id = :userId";
		Query query = session().createQuery( qry );
		query.setParameter( "userId", userId );
		return ((Number)query.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Friend> getFriendMapByEmail( String userId, Set<String> emails ) {
		
		final String qry = "select f from Friend f left outer join f.friendUser fu where f.user.id = :userId and (f.nonuserEmail in :emails or fu.email in (:emails))";
		Query query = session().createQuery( qry );
		query.setParameter( "userId", userId );
		query.setParameterList( "emails", emails );
		
		Map<String, Friend> map = new HashMap<String, Friend>();
		for ( Friend friend : (List<Friend>)query.list() ) {
			map.put( friend.getEmail(), friend );
		}
		
		return map;
	}

	@Override
	public void add( FriendRevision revision ) {
		session().saveOrUpdate( revision );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Friend> getFriendsByID( String userId, Set<Long> ids ) {
		
		final String qry = "from Friend where user.id = :userId and id in :ids";
		Query query = session().createQuery( qry );
		query.setParameter( "userId", userId );
		query.setParameterList( "ids", ids );
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Friend> getFriendList( String userId, int page, int count ) {
		
		if ( page < 1 || count < 1 )
			throw new InvalidParamException();
		
		final String qry = "from Friend where user.id = :userId order by id asc";
		Query query = session().createQuery( qry );
		query.setParameter( "userId", userId );
		query.setFirstResult( ( page - 1 ) * count );
		query.setMaxResults( count );
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FriendRevision> getRevisionList( String userId, int minRevision ) {
		
		final String qry = "from FriendRevision where pk.user.id = :userId and pk.revision > :minRevision order by pk.revision asc";
		Query query = session().createQuery( qry );
		query.setParameter( "userId", userId );
		query.setParameter( "minRevision", minRevision );
		query.setFetchSize( 10 );
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Friend> getFriendList( String userId, Set<Long> ids ) {
		
		final String qry = "from Friend where user.id = :userId and id in :ids";
		Query query = session().createQuery( qry );
		query.setParameter( "userId", userId );
		query.setParameterList( "ids", ids );
		return query.list();
	}

}

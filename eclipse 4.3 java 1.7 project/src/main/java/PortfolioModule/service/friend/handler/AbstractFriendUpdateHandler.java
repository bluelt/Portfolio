package PortfolioModule.service.friend.handler;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import MockModule.domain.entity.account.User;
import MockModule.service.account.AccountService;
import PortfolioModule.domain.entity.friend.Friend;
import PortfolioModule.domain.entity.friend.FriendRevision;
import PortfolioModule.domain.enumtype.friend.FriendUpdateType;
import PortfolioModule.persistent.friend.FriendDao;
import PortfolioModule.service.friend.value.FriendServiceUpdateRequest;
import PortfolioModule.service.friend.value.FriendServiceUpdateResult;

/**
 * @param <E> 응답 항목
 * @param <T> 요청 항목
 */
@Component
public abstract class AbstractFriendUpdateHandler<E,T> implements FriendUpdateHandler<E,T> {

	@Autowired AccountService accountService;
	@Autowired FriendDao friendDao;
	
	@Override
	public FriendServiceUpdateResult<E> handle( FriendServiceUpdateRequest<T> request ) {
		
		//이 주소록을 소유하는 사용자의 정보를 가져온다.
		User owner = accountService.getSafeUser( request.getUserId() );
		
		Collection<Friend> modifieds = handleInternal( owner, request.getList() );
		
		int lastRevision = increaseRevision( owner, request.getRevision(), modifieds );
		
		return buildResult( owner, lastRevision, modifieds );
	}
	
	public int increaseRevision( User owner, int revision, Collection<Friend> modifieds ) {
		
		int lastRevision = revision;
		if ( modifieds.size() > 0 ) { 
			FriendRevision friendRevision = new FriendRevision( owner, ++lastRevision );
			friendRevision.setIdList( modifieds );
			friendRevision.setType( getRevisionType() );
			friendDao.add( friendRevision );
		}
		
		return lastRevision;
	}
	
	public FriendServiceUpdateResult<E> buildResult( User owner, int revision, Collection<Friend> modifieds ) {

		FriendServiceUpdateResult<E> result = new FriendServiceUpdateResult<E>();
		result.setRevision( revision );
		
		buildResultInternal( owner, result, modifieds );
		
		return result;
	}
	
	/**
	 * FriendRevision에 기록될 type을 반환한다. 요청으로 들어온 FriendUpdateType과 다를 수 있다. <br/>
	 * ex) ADDBYNUMBER는 결국 ADD이기 때문에 getRevisionType에서는 ADD로 기록된다. <br/>
	 * @return
	 */
	abstract FriendUpdateType getRevisionType();
	abstract Collection<Friend> handleInternal( User owner, Set<T> requests );
	abstract void buildResultInternal( User owner, FriendServiceUpdateResult<E> result, Collection<Friend> modifieds );

}

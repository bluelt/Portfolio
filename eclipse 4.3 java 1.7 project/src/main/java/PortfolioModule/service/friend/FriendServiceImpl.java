package PortfolioModule.service.friend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import MockModule.domain.entity.account.User;
import MockModule.exception.InvalidOperationException;
import MockModule.exception.InvalidParamException;
import MockModule.service.account.AccountService;
import MockModule.util.validator.StringValidator;
import PortfolioModule.domain.dto.FriendDTO;
import PortfolioModule.domain.entity.friend.Friend;
import PortfolioModule.domain.entity.friend.FriendRevision;
import PortfolioModule.exception.friend.FriendAPIAttributeViolationException;
import PortfolioModule.exception.friend.FriendNeedSyncException;
import PortfolioModule.persistent.friend.FriendDao;
import PortfolioModule.service.friend.handler.FriendChangedListBuilder;
import PortfolioModule.service.friend.handler.FriendUpdateHandler;
import PortfolioModule.service.friend.handler.resolver.FriendUpdateHandlerResolver;
import PortfolioModule.service.friend.value.FriendRevisionInfo;
import PortfolioModule.service.friend.value.FriendServiceAttribute;
import PortfolioModule.service.friend.value.FriendServiceChangedListRequest;
import PortfolioModule.service.friend.value.FriendServiceChangedListResult;
import PortfolioModule.service.friend.value.FriendServiceListRequest;
import PortfolioModule.service.friend.value.FriendServiceUpdateRequest;
import PortfolioModule.service.friend.value.FriendServiceUpdateResult;

@Service
public class FriendServiceImpl implements FriendService {

	@Autowired FriendDao friendDao;
	@Autowired AccountService accountService;
	@Autowired FriendUpdateHandlerResolver friendUpdateHandlerResolver;

	/**
	 * 최신 리비전인지 확인한다.
	 * @param userId 기능 요청한 사용자 id
	 * @param revision 클라이언트의 현재 revision
	 * @throws FriendNeedSyncException 최신 리비전이 아닐 경우
	 */
	public void validateLatestRevision( final String userId, final int revision ) {
		if ( revision != friendDao.getLastRevision( userId ) )
				throw new FriendNeedSyncException();
	}
	
	@Override
	@Transactional(readOnly=true)
	public FriendRevisionInfo getRevisionInfo( String userId ) {
		
		if ( StringValidator.isValidString( userId ) == false )
			throw new InvalidParamException();
		
		return new FriendRevisionInfo( friendDao.getLastRevision( userId ), friendDao.getTotalFriendCount( userId ) );
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<FriendDTO> getList( FriendServiceListRequest request ) {
		
		request.validate();
		validateLatestRevision( request.getUserId(), request.getRevision() );
		
		List<Friend> friendList = friendDao.getFriendList( request.getUserId(), request.getPage(), request.getCount() );

		User owner = accountService.getSafeUser( request.getUserId() );
		return buildDtoList( owner.isUseCustomFriendName(), LocaleUtils.toLocale( owner.getLocale() ), friendList );
	}
	
	/**
	 * Friend 리스트를 FriendDTO 리스트로 변환한다.
	 * @param useCustomName 소유자가 설정한 이름을 사용할 지 여부 fasel일 경우, 친구(PO사용자)가 설정한 이름이 전달된다. 
	 * @param locale
	 * @param friendList
	 * @return
	 */
	public List<FriendDTO> buildDtoList( boolean useCustomName, Locale locale, Collection<Friend> friendList ) {
		
		List<FriendDTO> dtoList = new ArrayList<FriendDTO>();
		for ( Friend friend : friendList )
			dtoList.add( new FriendDTO( friend, useCustomName, locale ) );
		
		return dtoList;
	}

	@Override
	@Transactional
	public <E,T> FriendServiceUpdateResult<E> update( FriendServiceUpdateRequest<T> request ) {
		
		request.validate();
		validateLatestRevision( request.getUserId(), request.getRevision() );
		
		FriendUpdateHandler<E, T> handler = friendUpdateHandlerResolver.resolve( request.getType() );
		if ( handler == null )
			throw new InvalidOperationException();
		
		return handler.handle( request );
	}

	@Override
	@Transactional(readOnly=true)
	public FriendServiceChangedListResult getChangedList( FriendServiceChangedListRequest request ) {
		
		//클라이언트 revision이 최신 revision보다 큰 경우 오류
		int lastRevision = friendDao.getLastRevision( request.getUserId() );
		if ( request.getRevision() > lastRevision )
			throw new InvalidParamException();
		
		//클라이언트 revision과 최신 revision의 차이가 허용된 것 보다 큰 경우 오류
		if ( lastRevision > request.getRevision() + FriendServiceAttribute.MAX_REVISION_DIFFERENCE_FOR_GETCHANGED )
			throw new FriendAPIAttributeViolationException();
		
		//클라이언트 revision 이후 변경 사항을 최대 전달 가능 개수가 될 때까지 builder로 전달한다.
		FriendChangedListBuilder builder = new FriendChangedListBuilder();
		List<FriendRevision> revisionList = friendDao.getRevisionList( request.getUserId(), request.getRevision() );
		for ( FriendRevision revision : revisionList ) {
			if ( builder.put( revision ) == false )
				break;
		}
		
		//응답 목록에 추가될 것으로 예상되는 id들을 builder에서 전달받는다.
		Set<Long> ids = builder.getCandidateIDs();

		//예상 id들에 해당하는 Friend를 얻어와 ChangetListBuilder로 전달하여 최적화를 수행한 후 결과를 얻는다.
		Set<Friend> resultList = builder.build( friendDao.getFriendList( request.getUserId(), ids ) );
		int revision = builder.getRevision();

		//dto로 변환하여 결과를 전달한다.
		User owner = accountService.getSafeUser( request.getUserId() );
		List<FriendDTO> dtoList = buildDtoList( owner.isUseCustomFriendName(), LocaleUtils.toLocale( owner.getLocale() ), resultList );
		return new FriendServiceChangedListResult( revision, dtoList );
	}

}

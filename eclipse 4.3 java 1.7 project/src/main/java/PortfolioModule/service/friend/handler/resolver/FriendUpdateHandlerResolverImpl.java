package PortfolioModule.service.friend.handler.resolver;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import PortfolioModule.domain.enumtype.friend.FriendUpdateType;
import PortfolioModule.service.friend.handler.FriendAddByEmailHandler;
import PortfolioModule.service.friend.handler.FriendAddByNumberHandler;
import PortfolioModule.service.friend.handler.FriendDeleteLastSendTimeHandler;
import PortfolioModule.service.friend.handler.FriendHideHandler;
import PortfolioModule.service.friend.handler.FriendShowHandler;
import PortfolioModule.service.friend.handler.FriendUpdateHandler;

@Component
public class FriendUpdateHandlerResolverImpl implements
		FriendUpdateHandlerResolver {
	
	@Autowired private FriendAddByEmailHandler friendAddByEmailHandler;
	@Autowired private FriendAddByNumberHandler friendAddByNumberHandler;
	@Autowired private FriendShowHandler friendShowHandler;
	@Autowired private FriendHideHandler friendHideHandler;
	@Autowired private FriendDeleteLastSendTimeHandler friendDeleteLastSendTimeHandler;
	
	private final Map<FriendUpdateType, FriendUpdateHandler<?, ?>> map = new HashMap<>();
	
	@PostConstruct
	public void initialize() {
		map.put( FriendUpdateType.ADD, friendAddByEmailHandler );
		map.put( FriendUpdateType.ADDBYNUMBER, friendAddByNumberHandler );
		map.put( FriendUpdateType.SHOW, friendShowHandler );
		map.put( FriendUpdateType.HIDE, friendHideHandler );
		map.put( FriendUpdateType.DELETE_LASTSENDTIME, friendDeleteLastSendTimeHandler );
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E,T> FriendUpdateHandler<E,T> resolve( FriendUpdateType type ) {
		return (FriendUpdateHandler<E,T>)map.get( type );
	}

}

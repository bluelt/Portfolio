package PortfolioModule.service.friend.handler.resolver;

import org.springframework.stereotype.Component;

import PortfolioModule.domain.enumtype.friend.FriendUpdateType;
import PortfolioModule.service.friend.handler.FriendUpdateHandler;

@Component
public interface FriendUpdateHandlerResolver {
	
	public <E,T> FriendUpdateHandler<E,T> resolve( FriendUpdateType type );
}

package PortfolioModule.service.friend.handler;

import org.springframework.stereotype.Component;

import PortfolioModule.service.friend.value.FriendServiceUpdateRequest;
import PortfolioModule.service.friend.value.FriendServiceUpdateResult;

@Component
public interface FriendUpdateHandler<E,T> {

	FriendServiceUpdateResult<E> handle( FriendServiceUpdateRequest<T> request );
}

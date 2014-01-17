package PortfolioModule.service.friend.handler;

import org.springframework.stereotype.Component;

import PortfolioModule.domain.enumtype.friend.FriendUpdateType;

@Component
public class FriendShowHandler extends FriendShowHideHandler {

	@Override
	FriendUpdateType getRevisionType() {
		return FriendUpdateType.SHOW;
	}
}

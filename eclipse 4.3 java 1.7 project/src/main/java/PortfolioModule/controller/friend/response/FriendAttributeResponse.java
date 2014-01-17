package PortfolioModule.controller.friend.response;

import MockModule.controller.APIResponse;
import PortfolioModule.service.friend.value.FriendServiceAttribute;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FriendAttributeResponse extends APIResponse {

	@JsonProperty("mc")
	int maxCountPerUpdate = FriendServiceAttribute.MAX_FRIEND_COUNT_PER_UPDATE;
	@JsonProperty("mrd")
	int maxRevisionDifferenceForGetChanged = FriendServiceAttribute.MAX_REVISION_DIFFERENCE_FOR_GETCHANGED;
	@JsonProperty("mcpp")
	int maxFriendCountPerPage = FriendServiceAttribute.MAX_FRIEND_COUNT_PER_PAGE;
	
	public FriendAttributeResponse() {
	}
	
	public int getMaxCountPerUpdate() {
		return maxCountPerUpdate;
	}

	public int getMaxRevisionDifferenceForGetChanged() {
		return maxRevisionDifferenceForGetChanged;
	}

	public int getMaxFriendCountPerPage() {
		return maxFriendCountPerPage;
	}

}

package PortfolioModule.exception.friend;

import MockModule.common.ResultEnum;
import MockModule.exception.PolarisException;

public class FriendNeedSyncException extends PolarisException {

	private static final long serialVersionUID = -8983439647736808953L;

	public FriendNeedSyncException() {
		super( ResultEnum.FRIENDNEEDSYNC );
	}
	
	public FriendNeedSyncException( Throwable cause ) {
		super( cause, ResultEnum.FRIENDNEEDSYNC );
	}
}

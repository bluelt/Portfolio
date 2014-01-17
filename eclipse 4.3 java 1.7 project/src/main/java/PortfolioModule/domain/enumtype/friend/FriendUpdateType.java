package PortfolioModule.domain.enumtype.friend;

/**
 * <code>ADD</code> 주소록에 친구를 추가한다.<br/>
 * <code>ADDBYNUMBER</code> 주소록에 전화번호에 해당하는 유저가 있을 경우 해당 유저를 친구로 추가한다. 변경사항을 내려줄 때는, 전화번호로 추가되었는지 알 필요가 없으므로, ADD로 내려준다. 즉 내부적으로만 기능 실행시만 사용되며, FriendRevision에는 ADD로 기록된다.<br/>
 * <code>SHOW</code> 숨김 되어 있는 친구를 보이기 설정한다.<br/>
 * <code>HIDE</code> 보이기 되어 있는 친구를 숨김 설정한다.<br/>
 * <code>DELETE_LASTSENDTIME</code> 최근 발신 시간을 삭제하여, 최근 발신 목록에서 보이지 않도록 한다.<br/>
 * <code>UPDATE_LASTSENDTIME</code> 최근 발신 시간을 갱신한다.<br/>
 */
public enum FriendUpdateType {

	ADD,
	ADDBYNUMBER,
	SHOW,
	HIDE,
	DELETE_LASTSENDTIME,
	UPDATE_LASTSENDTIME,
}

package PortfolioModule.service.friend.value;

public class FriendServiceAttribute {

	/**
	 * Update 요청 시, 리스트에 포함될 수 있는 최대 요청 수
	 */
	public static final int MAX_FRIEND_COUNT_PER_UPDATE = 100;
	/**
	 * getChangedList를 통해 얻어갈 수 있는 허용 revision 차이.
	 * ex) 클라이언트 revision이 1이고, 서버 revision이 102일 경우, 차이가 101이므로 오류가 응답되므로, 클라이언트는 전체 조회를 해야 한다.
	 */
	public static final int MAX_REVISION_DIFFERENCE_FOR_GETCHANGED = 100;
	/**
	 * list 요청 시 한 페이지에 설정할 수 있는 최대 개수
	 */
	public static final int MAX_FRIEND_COUNT_PER_PAGE = 100;
	
	/**
	 * Service 전용.
	 * 한 번의 getChangedList 요청 당 클라이언트로 전달할 수 있는 최대 친구 개수
	 */
	public static final int MAX_FRIEND_COUNT_PER_CHANGEDLIST = 150;
}

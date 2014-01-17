package PortfolioModule.service.friend;

import java.util.List;

import org.springframework.stereotype.Service;

import PortfolioModule.domain.dto.FriendDTO;
import PortfolioModule.service.friend.value.FriendRevisionInfo;
import PortfolioModule.service.friend.value.FriendServiceChangedListRequest;
import PortfolioModule.service.friend.value.FriendServiceChangedListResult;
import PortfolioModule.service.friend.value.FriendServiceListRequest;
import PortfolioModule.service.friend.value.FriendServiceUpdateRequest;
import PortfolioModule.service.friend.value.FriendServiceUpdateResult;

@Service
public interface FriendService {

	/**
	 * Add, AddByNumber, Show, Hide, DeleteLastSendTime의 실행
	 * request : revision, List<T>
	 * response : revision, List<T>
	 * @param request
	 * @return
	 */
	public <E,T> FriendServiceUpdateResult<E> update( FriendServiceUpdateRequest<T> request );

	/**
	 * 해당 사용자의 최신 revision과 total friend count 정보를 전달한다.
	 * @param userId
	 * @return
	 */
	public FriendRevisionInfo getRevisionInfo( String userId );

	/**
	 * 요청에 설정된 정보에 따라 friend 목록을 전달한다.
	 * @param friendServiceListRequest
	 * @return
	 */
	public List<FriendDTO> getList( FriendServiceListRequest request );

	/**
	 * 클라이언트 revision 기준으로 변경된 항목들만 전달한다.
	 * @param request
	 * @return
	 */
	public FriendServiceChangedListResult getChangedList( FriendServiceChangedListRequest request );
	
}

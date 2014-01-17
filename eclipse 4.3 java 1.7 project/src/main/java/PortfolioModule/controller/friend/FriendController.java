package PortfolioModule.controller.friend;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import MockModule.controller.APIRequest;
import MockModule.controller.APIResponse;
import MockModule.controller.AuthedAPIController;
import MockModule.controller.WebDefine;
import MockModule.domain.AuthedSessionInfo;
import PortfolioModule.controller.friend.request.FriendAPIUpdateRequest;
import PortfolioModule.controller.friend.request.FriendAPIUpdateRequestFriendList;
import PortfolioModule.controller.friend.request.FriendAPIUpdateRequestLongList;
import PortfolioModule.controller.friend.request.FriendAPIUpdateRequestStringList;
import PortfolioModule.controller.friend.request.FriendChangedListRequest;
import PortfolioModule.controller.friend.request.FriendListRequest;
import PortfolioModule.controller.friend.response.FriendAPIUpdateResponseFriendList;
import PortfolioModule.controller.friend.response.FriendAPIUpdateResponseVoidList;
import PortfolioModule.controller.friend.response.FriendAttributeResponse;
import PortfolioModule.controller.friend.response.FriendChangedListResponse;
import PortfolioModule.controller.friend.response.FriendListResponse;
import PortfolioModule.controller.friend.response.FriendRevisionResponse;
import PortfolioModule.domain.dto.FriendDTO;
import PortfolioModule.domain.enumtype.friend.FriendUpdateType;
import PortfolioModule.exception.friend.FriendNeedSyncException;
import PortfolioModule.service.friend.FriendService;
import PortfolioModule.service.friend.value.FriendRevisionInfo;
import PortfolioModule.service.friend.value.FriendServiceChangedListRequest;
import PortfolioModule.service.friend.value.FriendServiceChangedListResult;
import PortfolioModule.service.friend.value.FriendServiceListRequest;
import PortfolioModule.service.friend.value.FriendServiceUpdateRequest;
import PortfolioModule.service.friend.value.FriendServiceUpdateResult;

@Controller
@RequestMapping("/api/1.1/friend")
public class FriendController extends AuthedAPIController {

	FriendService friendService;
	
	@Autowired 
	void init( FriendService friendService ) {
		this.friendService = friendService;
	}
	
	@InitBinder
	protected void initBinder( WebDataBinder binder ) {
		if ( binder.getTarget() instanceof APIRequest )
			binder.setValidator( apiRequestValidator );
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public APIResponse add( @Valid @RequestBody FriendAPIUpdateRequestFriendList request, @ModelAttribute(WebDefine.N_SESSION_INFO) AuthedSessionInfo session ) {
		
		FriendServiceUpdateRequest<FriendDTO> req = buildUpdateRequest( request, FriendUpdateType.ADD, session );
		
		FriendServiceUpdateResult<FriendDTO> result = handleUpdate( req );
		return new FriendAPIUpdateResponseFriendList( result );
	}
	
	@RequestMapping(value="/addbynumber", method=RequestMethod.POST)
	@ResponseBody
	public APIResponse addByNumber( @Valid @RequestBody FriendAPIUpdateRequestStringList request, @ModelAttribute(WebDefine.N_SESSION_INFO) AuthedSessionInfo session ) {
		
		FriendServiceUpdateRequest<String> req = buildUpdateRequest( request, FriendUpdateType.ADDBYNUMBER, session );
		
		FriendServiceUpdateResult<FriendDTO> result = handleUpdate( req );
		return new FriendAPIUpdateResponseFriendList( result );
	}
	
	@RequestMapping(value="/show", method=RequestMethod.POST)
	@ResponseBody
	public APIResponse show( @Valid @RequestBody FriendAPIUpdateRequestLongList request, @ModelAttribute(WebDefine.N_SESSION_INFO) AuthedSessionInfo session ) {
		
		FriendServiceUpdateRequest<Long> req = buildUpdateRequest( request, FriendUpdateType.SHOW, session );
		
		FriendServiceUpdateResult<Void> result = handleUpdate( req );
		return new FriendAPIUpdateResponseVoidList( result );
	}
	
	@RequestMapping(value="/hide", method=RequestMethod.POST)
	@ResponseBody
	public APIResponse hide( @Valid @RequestBody FriendAPIUpdateRequestLongList request, @ModelAttribute(WebDefine.N_SESSION_INFO) AuthedSessionInfo session ) {
		
		FriendServiceUpdateRequest<Long> req = buildUpdateRequest( request, FriendUpdateType.HIDE, session );
		
		FriendServiceUpdateResult<Void> result = handleUpdate( req );
		return new FriendAPIUpdateResponseVoidList( result );
	}
	
	@RequestMapping(value="/deletelastsendtime", method=RequestMethod.POST)
	@ResponseBody
	public APIResponse deleteLastSendTime( @Valid @RequestBody FriendAPIUpdateRequestLongList request, @ModelAttribute(WebDefine.N_SESSION_INFO) AuthedSessionInfo session ) {
		
		FriendServiceUpdateRequest<Long> req = buildUpdateRequest( request, FriendUpdateType.DELETE_LASTSENDTIME, session );
		
		FriendServiceUpdateResult<Void> result = handleUpdate( req );
		return new FriendAPIUpdateResponseVoidList( result );
	}
	
	public <T> FriendServiceUpdateRequest<T> buildUpdateRequest( FriendAPIUpdateRequest<T> request, FriendUpdateType type, AuthedSessionInfo session ) {
		
		FriendServiceUpdateRequest<T> param = new FriendServiceUpdateRequest<T>();
		param.setRevision( request.getRevision() );
		param.setType( type );
		param.setList( request.getList() );
		param.setUserId( session.getUserId() );
		param.setDeviceId( session.getDeviceId() );
		return param;
	}
	
	public <E,T> FriendServiceUpdateResult<E> handleUpdate( FriendServiceUpdateRequest<T> request ) {

		try {
			return friendService.update( request );
		} catch ( CannotAcquireLockException e ) {
			throw new FriendNeedSyncException( e );
		} catch ( DataIntegrityViolationException e ) {
			if ( e.getCause() instanceof ConstraintViolationException || e.getCause() instanceof javax.validation.ConstraintViolationException )
				throw new FriendNeedSyncException( e );
			
			throw e;
		}
	}
	
	@RequestMapping(value="/attribute", method=RequestMethod.POST)
	@ResponseBody
	public APIResponse getAttribute() {
		
		return new FriendAttributeResponse();
	}
	
	@RequestMapping(value="/revision", method=RequestMethod.POST)
	@ResponseBody
	public APIResponse getRevision( @ModelAttribute(WebDefine.N_SESSION_INFO) AuthedSessionInfo info ) {
		
		FriendRevisionInfo revision = friendService.getRevisionInfo( info.getUserId() );
		return new FriendRevisionResponse( revision );
	}
	
	@RequestMapping(value="/list", method=RequestMethod.POST)
	@ResponseBody
	public APIResponse getList( @Valid @RequestBody FriendListRequest request, @ModelAttribute(WebDefine.N_SESSION_INFO) AuthedSessionInfo session ) {
		
		List<FriendDTO> list = friendService.getList( new FriendServiceListRequest( request, session ) );
		return new FriendListResponse( list );
	}
	
	@RequestMapping(value="/changedlist", method=RequestMethod.POST)
	@ResponseBody
	public APIResponse getChangedList( @Valid @RequestBody FriendChangedListRequest request, @ModelAttribute(WebDefine.N_SESSION_INFO) AuthedSessionInfo session ) {
		
		FriendServiceChangedListResult result = friendService.getChangedList( new FriendServiceChangedListRequest( request.getRevision(), session ) );
		return new FriendChangedListResponse( result );
	}

}

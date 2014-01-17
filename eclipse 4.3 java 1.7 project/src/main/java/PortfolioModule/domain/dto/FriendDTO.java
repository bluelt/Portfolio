package PortfolioModule.domain.dto;

import java.util.Locale;

import MockModule.domain.entity.account.User;
import PortfolioModule.domain.entity.friend.Friend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Email만으로 equality를 판별한다.
  */
@JsonInclude(Include.NON_DEFAULT)
public class FriendDTO {

	long id;
	@JsonProperty("n")
	String name;
	@JsonProperty("e")
	String email;
	@JsonProperty("uid")
	String userId;
	@JsonProperty("sh")
	boolean show = true;
	@JsonProperty("lst")
	int lastSendTime = 0;
	@JsonProperty("pn")
	String phoneNumber;
	
	@JsonIgnore
	User friendUser;
	
	public FriendDTO() {
	}
	
	public FriendDTO( String name, String email ) {
		this.name = name;
		this.email = email;
	}
	
	public FriendDTO( Friend friend, boolean useCustomName, Locale locale ) {
		
		this.id = friend.getId();
		this.name = friend.getName();
		if ( useCustomName == false && friend.getFriendUser() != null )
			this.name = friend.getFriendUser().getFullName( locale );
		
		this.email = friend.getEmail();
		
		if ( friend.getFriendUser() != null )
			this.userId = friend.getFriendUser().getUserId();
		
		this.show = friend.isShow();
		this.lastSendTime = friend.getLastSendTime();
	}

	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId( String userId ) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow( boolean show ) {
		this.show = show;
	}

	public int getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime( int lastSendTime ) {
		this.lastSendTime = lastSendTime;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber( String phoneNumber ) {
		this.phoneNumber = phoneNumber;
	}
	
	public User getFriendUser() {
		return friendUser;
	}

	public void setFriendUser( User friendUser ) {
		this.friendUser = friendUser;
	}

	@Override
	public int hashCode() {
		final int prime = 19;
		int result = 3;
		
		result = prime * result + ( email == null ? 0 : email.hashCode() );
		return result;
	}
	
	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) return true;
		if ( obj == null ) return false;
		if ( getClass() != obj.getClass() ) return false;
		
		FriendDTO that = (FriendDTO)obj;
		if ( email == null )
			return that.getEmail() == null;
		
		return email.equals( that.getEmail() );
	}

}

package PortfolioModule.domain.entity.friend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

import MockModule.domain.entity.account.User;
import MockModule.util.validator.StringValidator;

@Entity
@Table(name="tbFriend"
	, uniqueConstraints={@UniqueConstraint(columnNames={"idUser","idFriendUser"}, name="ix_1"),
		@UniqueConstraint(columnNames={"idUser","email"}, name="ix_2")})
public class Friend {

	long id;
	User user;
	User friendUser;
	String nonuserEmail;
	String name;
	boolean show = true;
	int lastSendTime = 0;
	
	public Friend() {
	}
	
	public Friend( String userId, String name ) {
		this.user = new User( userId );
		setName( name );
	}
	
	public Friend( User user, String name ) {
		this.user = user;
		setName( name );
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="idUser", nullable=false)
	@ForeignKey(name = "none") 
	@Index(name = "ix_2")
	public User getUser() {
		return user;
	}

	public void setUser( User user ) {
		this.user = user;
	}

	@ManyToOne(optional=true)
	@JoinColumn(name="idFriendUser", nullable=true)
	@ForeignKey(name = "none") 
	@Index(name = "ix_3")
	public User getFriendUser() {
		return friendUser;
	}

	public void setFriendUser( User friendUser ) {
		this.friendUser = friendUser;
		this.nonuserEmail = null;
	}
	
	@Type(type="kr.co.infraware.polaris.domain.usertype.EmailEncryptionUserType")
	@Column(name="email", nullable=true, length=767)
	@Index(name="ix_4")
	public String getNonuserEmail() {
		return nonuserEmail;
	}

	public void setNonuserEmail( String nonuserEmail ) {
		this.nonuserEmail = nonuserEmail;
		this.friendUser = null;
	}

	@Type(type="kr.co.infraware.polaris.domain.usertype.NameEncryptionUserType")
	@Column(name = "name", nullable = true, length=4000)
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 * @return boolean 이름이 변경된 경우 true, 유효하지 않은 이름, 기존과 같은 이름일 경우 false
	 */
	public boolean setName( String name ) {
		
		if ( StringValidator.isValidString( name ) == false )
			return false;
		
		if ( name.equals( getName() ) )
			return false;
		
		this.name = name;
		return true;
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

	/**
	 * 비사용자,사용자 관계 없이 email을 전달한다.<br/>
	 * 비사용자  메일만 가져가기 위해서는 getNonuserEmail을 사용한다.
	 * @return
	 */
	@Transient
	public String getEmail() {
		return getNonuserEmail() != null ? getNonuserEmail() : getFriendUser().getEmail();
	}
	
}

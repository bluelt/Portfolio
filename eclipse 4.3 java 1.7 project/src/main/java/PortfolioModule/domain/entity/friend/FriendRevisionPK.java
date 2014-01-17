package PortfolioModule.domain.entity.friend;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

import MockModule.domain.entity.account.User;

@Embeddable
public class FriendRevisionPK implements Serializable {

	private static final long serialVersionUID = -26942304036942900L;

	private User user;
	private int revision;

	public FriendRevisionPK() {
	}
	
	public FriendRevisionPK( User user, int revision ) {
		this.user = user;
		this.revision = revision;
	}
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="idUser", nullable=false, insertable=true, updatable=false)
	@ForeignKey(name = "none") 
	public User getUser() {
		return user;
	}

	public void setUser( User user ) {
		this.user = user;
	}
	
	@Column(name = "revision", nullable = false)
	public int getRevision() {
		return revision;
	}

	public void setRevision( int revision ) {
		this.revision = revision;
	}

	@Override
	public int hashCode() {
		final int prime = 19;
		int result = 3;
		
		result = prime * result + ( getUser() == null ? 0 : getUser().hashCode() );
		result = prime * result + getRevision();
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if (this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( ( obj instanceof FriendRevisionPK ) == false )
			return false;
		
		FriendRevisionPK that = (FriendRevisionPK) obj;
		if ( getRevision() != that.getRevision() )
			return false;
		if ( getUser() == null && that.getUser() != null )
			return false;
		
		return getUser().equals( that.getUser() );
	}
	
}

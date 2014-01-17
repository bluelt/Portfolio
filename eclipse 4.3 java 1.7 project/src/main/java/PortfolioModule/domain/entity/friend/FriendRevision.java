package PortfolioModule.domain.entity.friend;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import MockModule.domain.entity.account.User;
import PortfolioModule.domain.enumtype.friend.FriendUpdateType;

@Entity
@Table(name="tbFriendRevision")
public class FriendRevision {

	private static final String ID_SEPARATOR = ",";
	
	FriendRevisionPK pk;
	FriendUpdateType type;
	String idList;
	
	Set<Long> idSet = null;
	
	public FriendRevision() {
	}
	
	public FriendRevision( User user, int revision ) {
		pk = new FriendRevisionPK( user, revision );
	}

	@EmbeddedId
	public FriendRevisionPK getPk() {
		return pk;
	}

	public void setPk( FriendRevisionPK pk ) {
		this.pk = pk;
	}

	@Column(name="type", nullable = false, columnDefinition="tinyint")
	@Enumerated(EnumType.ORDINAL)
	public FriendUpdateType getType() {
		return type;
	}

	public void setType( FriendUpdateType type ) {
		this.type = type;
	}

	@Column(name="list", nullable = true, length=4000)
	public String getIdList() {
		return idList;
	}

	public void setIdList( String idList ) {
		this.idList = idList;
	}

	@Transient
	public Set<Long> getIdSet() {
		
		if ( idSet == null )
			buildIDSet();
		
		return idSet;
	}

	public void setIdSet( Set<Long> idSet ) {
		this.idSet = idSet;
	}

	public <T> void setIdList( Collection<T> list ) {
		StringBuilder ids = new StringBuilder();
		for ( T item : list ) {
			if ( item instanceof Long ) 
				ids.append( item );
			else if ( item instanceof Friend )
				ids.append( ( (Friend) item ).getId() );
			else
				continue;
			
			ids.append( ID_SEPARATOR );
		}
		idList = ids.toString();
	}
	
	public void buildIDSet() {
		idSet = new HashSet<Long>();
		if ( getIdList() == null )
			return;
		
		for ( String id : getIdList().split( ID_SEPARATOR ) )
			idSet.add( Long.valueOf( id ) );
	}

}

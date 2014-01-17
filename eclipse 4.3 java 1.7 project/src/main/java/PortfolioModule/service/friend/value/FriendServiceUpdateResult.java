package PortfolioModule.service.friend.value;

import java.util.ArrayList;
import java.util.List;

public class FriendServiceUpdateResult<T> {

	int revision;
	List<T> list;
	
	public FriendServiceUpdateResult() {
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision( int revision ) {
		this.revision = revision;
	}

	public List<T> getList() {
		return list;
	}

	public void setList( List<T> list ) {
		this.list = list;
	}
	
	public void addResult( T t ) {
		
		if ( list == null )
			list = new ArrayList<>();
			
		list.add( t );
	}

}

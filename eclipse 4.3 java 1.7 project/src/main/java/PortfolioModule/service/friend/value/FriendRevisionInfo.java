package PortfolioModule.service.friend.value;

public class FriendRevisionInfo {

	int revision = -1;
	int count = -1;
	
	public FriendRevisionInfo() {
	}
	
	public FriendRevisionInfo( int revision, int count ) {
		this.revision = revision;
		this.count = count;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision( int revision ) {
		this.revision = revision;
	}

	public int getCount() {
		return count;
	}

	public void setCount( int count ) {
		this.count = count;
	}

}

package MockModule.service.account;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import MockModule.domain.entity.account.User;

@Service
public interface AccountService {

	User getSafeUser( String userId );
	
	User getUser( String userid );
	Map<String, User> findUsersByEmail( List<String> emails );
	Map<String, List<User>> findUsersByPhoneNo( List<String> phoneNumbers );

}

package appdev.com.techmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import appdev.com.techmatch.model.UserAccount;

public interface AccountRepo extends JpaRepository<UserAccount, Long> {
    // Additional query methods (if needed) can be defined here
}

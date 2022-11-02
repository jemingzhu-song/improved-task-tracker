package traction.backend.datasource

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import traction.backend.model.UserAccount
import java.util.Optional

@Repository
public interface UserAccountRepository: JpaRepository<UserAccount, Long> {

    @Query(value = "SELECT * FROM user_account u WHERE u.email = ?1", nativeQuery = true)
    fun findUserAccountByEmail(email: String): Optional<UserAccount>
}
package ist.challenge.satrio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ist.challenge.satrio.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
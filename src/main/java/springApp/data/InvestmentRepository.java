package springApp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import springApp.model.Investment;
import springApp.model.User;

import java.util.List;
import java.util.Optional;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByUser(Optional<User> user);
}

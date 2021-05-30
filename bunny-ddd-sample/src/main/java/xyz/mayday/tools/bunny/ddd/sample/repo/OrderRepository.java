package xyz.mayday.tools.bunny.ddd.sample.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.mayday.tools.bunny.ddd.sample.domain.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
}

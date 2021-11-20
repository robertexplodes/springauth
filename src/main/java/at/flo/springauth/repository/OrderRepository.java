package at.flo.springauth.repository;

import at.flo.springauth.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Integer> {


}

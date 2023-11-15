package springMVC.Repo;

import springMVC.Client.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepo {
    List<Order> findAll();
    Optional<Order> findById(Long id);
    Order save(Order order);
    Order update(Order order);
    void delete(Long id);
    void deleteById(List<Long> ids);
}

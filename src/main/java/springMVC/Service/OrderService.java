package springMVC.Service;

import springMVC.Client.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(Long id);
    Order save(Order order);
    void delete(Long id);
    void deleteById(List<Long> ids);
    Order update(Order order);
}

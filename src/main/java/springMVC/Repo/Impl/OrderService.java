package springMVC.Repo.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springMVC.Client.Order;
import springMVC.Exceptions.UpdateStateException;
import springMVC.Repo.OrderRepo;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements springMVC.Service.OrderService {
    private final OrderRepo orderRepo;

    @Override
    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepo.findById(id).orElseThrow(()->new EntityNotFoundException(MessageFormat.format("",id)));

    }

    @Override
    public Order save(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepo.delete(id);

    }

    @Override
    public void deleteById(List<Long> ids) {
        orderRepo.deleteById(ids);
    }

    @Override
    public Order update(Order order) {
        checkForUpdate(order.getId());
        return orderRepo.update(order);
    }

    private void checkForUpdate(Long orderId){
        Order currentOrder = findById(orderId);
        Instant now = Instant.now();
        Duration duration = Duration.between(currentOrder.getUpdate(),now);
        if (duration.getSeconds()>5){
            throw new UpdateStateException("");
        }
    }
}


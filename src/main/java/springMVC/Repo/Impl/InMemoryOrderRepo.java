package springMVC.Repo.Impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import springMVC.Client.Client;
import springMVC.Client.Order;
import springMVC.Repo.OrderRepo;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryOrderRepo implements OrderRepo {
    private ClientRepo clientRepo;
    private final Map<Long, Order> repo = new ConcurrentHashMap<>();
    private final AtomicLong currentid = new AtomicLong(1);

    @Autowired
    public void setClientRepo(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(repo.values());
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(repo.get(id));
    }

    @Override
    public Order save(Order order) {
        Long orderId = currentid.getAndIncrement();
        Long clientId = order.getClient().getId();
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        order.setClient(client);
        order.setId(orderId);
        Instant now = Instant.now();
        order.setCreate(now);
        order.setUpdate(now);
        repo.put(orderId, order);
        client.addOrder(order);
        clientRepo.update(client);
        return order;
    }

    @Override
    public Order update(Order order) {
        Long orderId = order.getId();
        Instant now = Instant.now();
        Order currentOrder = repo.get(orderId);
        if (currentOrder == null) {
            throw new EntityNotFoundException(MessageFormat.format("", orderId));
        }
        BeanUtils.copyProperties(order, currentOrder);
        currentOrder.setUpdate(now);
        currentOrder.setId(orderId);
        repo.put(orderId, currentOrder);
        return currentOrder;
    }

    @Override
    public void delete(Long id) {
        repo.remove(id);
    }

    @Override
    public void deleteById(List<Long> ids) {
        ids.forEach(repo::remove);

    }
}

package springMVC.Repo.Impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import springMVC.Client.Client;
import springMVC.Client.Order;
import springMVC.Repo.OrderRepo;
import springMVC.Utils.BeanUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ClientRepo implements springMVC.Repo.ClientRepo {
    private OrderRepo orderRepo;
    private final Map<Long, Client> repo = new ConcurrentHashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(repo.values());
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Client save(Client client) {
        Long clientId = currentId.getAndIncrement();
        client.setId(clientId);
        repo.put(clientId, client);
        return client;
    }

    @Override
    public Client update(Client client) {
        Long clientId = client.getId();
        Client currentClient = repo.get(clientId);
        if (currentClient == null) {
            throw new EntityNotFoundException(MessageFormat.format("Клиент не найден", clientId));
        }
        BeanUtils.copyNonNullProperties(client, clientId);
        currentClient.setId(clientId);
        repo.put(clientId, currentClient);
        return currentClient;
    }

    @Override
    public void deleteById(Long id) {
        Client client = repo.get(id);
        if (client == null) {
            throw new EntityNotFoundException(MessageFormat.format("Клиент не найден", id));
        }
            orderRepo.deleteById(client.getOrders().stream().map(Order::getId).collect(Collectors.toList()));
            repo.remove(id);

        }

    @Autowired
    public void setOrderRepo(OrderRepo orderRepo){
        this.orderRepo = orderRepo;
    }
}

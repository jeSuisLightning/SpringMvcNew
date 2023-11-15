package springMVC.Repo;

import springMVC.Client.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepo {
    List<Client> findAll();
    Optional<Client> findById(Long id);
    Client save(Client client);
    Client update(Client client);
    void deleteById(Long id);


}

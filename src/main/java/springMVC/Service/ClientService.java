package springMVC.Service;

import springMVC.Client.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();


    Client findById(Long id);

    Client save(Client client);
    Client update(Client client);

    void delete(Long id);
    void deleteById(List<Client> ids);
}

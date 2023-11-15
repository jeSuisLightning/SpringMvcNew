package springMVC.Repo.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springMVC.Client.Client;
import springMVC.Service.ClientService;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepo clientRepo;
    @Override
    public List<Client> findAll() {
        return clientRepo.findAll();
    }


    @Override
    public Client findById(Long id) {
        return clientRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("",id)));

    }

    @Override
    public Client save(Client client) {
        return clientRepo.save(client);
    }

    @Override
    public Client update(Client client) {
        return clientRepo.update(client);
    }

    @Override
    public void delete(Long id) {
        clientRepo.deleteById(id);

    }

    @Override
    public void deleteById(List<Client> ids) {

    }
}

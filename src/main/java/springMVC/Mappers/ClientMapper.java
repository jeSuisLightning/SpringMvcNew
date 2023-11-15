package springMVC.Mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import springMVC.Client.Client;
import springMVC.web.Models.models.ClientListResponse;
import springMVC.web.Models.models.ClientResponse;
import springMVC.web.Models.models.UpsertClientRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientMapper {
    private final OrderMapper orderMapper;
    public Client requestToClient(UpsertClientRequest request){
        Client client = new Client();
        client.setName(request.getName());
        return client;

    }
    public Client requestToClient(Long clientId,UpsertClientRequest request){
        Client client = requestToClient(request);
        client.setId(clientId);
        return client;

    }
    public ClientResponse clientResponse(Client client){
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(client.getId());
        clientResponse.setName(client.getName());
        clientResponse.setOrders(orderMapper.orderListToResponse(client.getOrders()));
        return clientResponse;
    }
    public ClientListResponse clientListResponse(List<Client> clients){
        ClientListResponse response = new ClientListResponse();
        response.setClients(clients.stream().map(this::clientResponse).collect(Collectors.toList()));
        return response;
    }
}

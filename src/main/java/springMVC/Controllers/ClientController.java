package springMVC.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springMVC.Client.Client;
import springMVC.Mappers.ClientMapper;
import springMVC.Service.ClientService;
import springMVC.web.Models.models.ClientListResponse;
import springMVC.web.Models.models.ClientResponse;
import springMVC.web.Models.models.UpsertClientRequest;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;
    @GetMapping
    public ResponseEntity<ClientListResponse> findAll(){
        return ResponseEntity.ok(clientMapper.clientListResponse(clientService.findAll()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(clientMapper.clientResponse(clientService.findById(id)));
    }
    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody UpsertClientRequest request){
        Client newClient = clientService.save(clientMapper.requestToClient(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapper.clientResponse(newClient));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable("id")Long clientId,@RequestBody UpsertClientRequest request){
        Client updatedClient = clientService.update(clientMapper.requestToClient(clientId,request));
        return ResponseEntity.ok(clientMapper.clientResponse(updatedClient));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clientService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}


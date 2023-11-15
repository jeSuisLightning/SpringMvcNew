package springMVC.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springMVC.Client.Order;
import springMVC.Mappers.OrderMapper;
import springMVC.Repo.Impl.OrderService;
import springMVC.web.Models.models.OrderListResponse;
import springMVC.web.Models.models.OrderResponse;
import springMVC.web.Models.models.UpsertOrderRequest;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    @GetMapping
    public ResponseEntity<OrderListResponse> findAll(){
        return ResponseEntity.ok(orderMapper.orderListResponse(orderService.findAll()));

    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(orderMapper.orderToResponse(orderService.findById(id)));

    }
    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody UpsertOrderRequest request){
        Order newOrder = orderService.save(orderMapper.requestToOrder(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.orderToResponse(newOrder));
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable("id") Long orderId,@RequestBody UpsertOrderRequest request){
        Order updatedOrder = orderService.update(orderMapper.requestToOrder(orderId,request));
        return ResponseEntity.ok(orderMapper.orderToResponse(updatedOrder));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

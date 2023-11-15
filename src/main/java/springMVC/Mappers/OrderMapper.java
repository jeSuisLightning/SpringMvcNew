package springMVC.Mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import springMVC.Client.Order;
import springMVC.Repo.Impl.ClientServiceImpl;
import springMVC.web.Models.models.ClientResponse;
import springMVC.web.Models.models.OrderListResponse;
import springMVC.web.Models.models.OrderResponse;
import springMVC.web.Models.models.UpsertOrderRequest;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final ClientServiceImpl clientService;

    public Order requestToOrder(UpsertOrderRequest request){
        Order order = new Order();
        order.setProduct(request.getProduct());
        order.setClient(clientService.findById(request.getClientId()));
        return order;
    }
    public Order requestToOrder(Long orderId,UpsertOrderRequest request){
        Order order = requestToOrder(request);
        order.setId(orderId);
        return order;
    }
    public OrderResponse orderToResponse(Order order){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setProduct(order.getProduct());
        return orderResponse;

    }
    public List<OrderResponse> orderListToResponse(List<Order> orders){
        return orders.stream().map(this::orderToResponse).collect(Collectors.toList());
    }
    public OrderListResponse orderListResponse(List<Order> orders){
        OrderListResponse response= new OrderListResponse();
        response.setOrders(orderListToResponse(orders));
        return response;

    }
}

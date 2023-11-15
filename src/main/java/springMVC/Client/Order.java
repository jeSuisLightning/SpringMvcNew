package springMVC.Client;

import lombok.Data;

import java.time.Instant;

@Data
public class Order {
    private Long id;
    private String product;
    private String price;
    private Client client;
    private Instant create;
    private Instant update;
}

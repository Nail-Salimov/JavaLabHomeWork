package server.shop.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import server.entities.user.dto.UserDataDto;
import server.shop.entities.model.OrderState;
import server.shop.entities.model.ProductModel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "product")

public class OrderDto {

    private Long id;

    private Long sellerId;

    private Long buyerId;

    private ProductDto product;
    private OrderState orderState;

    private UserDataDto buyer;
    private Integer count;
    private Integer cost;

}

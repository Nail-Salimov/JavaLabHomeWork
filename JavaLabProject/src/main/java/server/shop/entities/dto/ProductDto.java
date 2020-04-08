package server.shop.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.shop.entities.model.ImageProductModel;
import server.shop.entities.model.ProductModel;
import server.shop.entities.model.ProductState;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Integer minCost;
    private Integer maxCost;
    private Integer decrease;
    private Integer count;
    private ProductState productState;

    private Long sellerId;
    private Long time;

    private List<ImageProductDto> images;

    public static ProductDto getProductDto(ProductModel productModel){
        return ProductDto.builder()
                .id(productModel.getId())
                .name(productModel.getName())
                .description(productModel.getDescription())
                .minCost(productModel.getMinCost())
                .maxCost(productModel.getMaxCost())
                .decrease(productModel.getDecrease())
                .count(productModel.getCount())
                .sellerId(productModel.getSellerId())
                .time(productModel.getTime())
                .productState(productModel.getProductState())
                .build();
    }

}

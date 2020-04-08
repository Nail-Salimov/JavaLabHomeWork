package server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import server.entities.ancillary.responce.ResponseDto;
import server.entities.user.dto.UserDataDto;
import server.entities.user.model.UserDataModel;
import server.security.details.UserDetailImpl;
import server.security.jwt.details.UserDetailsJwtImpl;
import server.shop.bl.services.DataProductService;
import server.shop.bl.services.StoreService;
import server.shop.entities.dto.ProductDto;

import java.util.Optional;

@RestController
public class BuyProductRestController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private DataProductService dataProductService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/api/buy", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto> buyProduct(@RequestBody ProductDto requestProductDto){


        Optional<ProductDto> optionalProductDto = storeService.findProductById(requestProductDto.getId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsJwtImpl userDetailsJwt = (UserDetailsJwtImpl) authentication.getDetails();


        if(optionalProductDto.isPresent()){

            if(userDetailsJwt.getAddress() != null) {
                ProductDto productDto = optionalProductDto.get();

                if (storeService.buyProduct(productDto, dataProductService.calculateCost(productDto),
                        requestProductDto.getCount(), getUserDataDto(userDetailsJwt))) {
                    return ResponseEntity.ok(new ResponseDto("ok", "куплено"));
                } else {
                    return ResponseEntity.ok(new ResponseDto("no", "нет в продаже"));
                }
            }else {
                return ResponseEntity.ok(new ResponseDto("no", "чтобы купить, укажите адрес"));
            }
        }
        throw new IllegalArgumentException("product has not founded");
    }

    private UserDataDto getUserDataDto(UserDetailsJwtImpl userDetailsJwt){
        return UserDataDto.builder()
                .id(userDetailsJwt.getUserId())
                .address(userDetailsJwt.getAddress())
                .mail(userDetailsJwt.getUsername())
                .name(userDetailsJwt.getName())
                .role(userDetailsJwt.getRole())
                .state(userDetailsJwt.getState())
                .build();
    }

}

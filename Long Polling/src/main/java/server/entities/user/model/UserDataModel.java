package server.entities.user.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.shop.entities.model.ProductModel;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDataModel {
    private Long id;
    private String mail;
    private String hashPassword;
    private String name;
    private String address;
    private String token;

    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "userDataModel")
    private List<ProductModel> productModels;
}

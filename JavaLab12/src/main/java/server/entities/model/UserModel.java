package server.entities.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String mail;

    @Column(nullable = false)
    private String hashPassword;

    @Transient
    @OneToMany(mappedBy = "userModel", fetch = FetchType.EAGER)
    private Set<MessageModel> messageModels;

    @Transient
    @OneToMany(mappedBy = "userModel", fetch = FetchType.EAGER)
    private Set<LinkModel> linkModels;
}

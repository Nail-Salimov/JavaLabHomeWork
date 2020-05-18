package server.entities.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import server.entities.document.Document;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String mail;

    @Transient
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<Document> documents;


    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @Where(clause = "type = '.mp4'")
    private Set<Document> mp4Documents;

}

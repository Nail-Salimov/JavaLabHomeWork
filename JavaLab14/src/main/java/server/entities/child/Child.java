package server.entities.child;

import lombok.*;
import lombok.experimental.SuperBuilder;
import server.entities.human.Human;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Child extends Human {

    private Boolean read;
}

package server.addition.chat.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class ChatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    @OneToMany(mappedBy = "chatModel", fetch = FetchType.EAGER)
    private Set<Member> members;

    @Transient
    @OneToMany(mappedBy = "chatModel", fetch = FetchType.EAGER)
    private Set<MessageModel> messages;
}

package server.entity.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import server.entity.queue.QueueModel;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class MessageModel {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "queue")
    private QueueModel queue;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private Boolean received;

    @Column(nullable = false)
    private Boolean completed;
}

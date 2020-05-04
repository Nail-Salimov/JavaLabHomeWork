package server.entity.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.queue.QueueDto;
import server.entity.queue.QueueModel;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessageDto {
    private String id;
    private QueueDto queue;
    private String body;
    private Boolean received;
    private Boolean completed;
}

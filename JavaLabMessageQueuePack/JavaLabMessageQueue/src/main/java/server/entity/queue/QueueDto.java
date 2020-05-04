package server.entity.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.message.MessageDto;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueDto {

    private Long id;
    private String queueName;
    private Set<MessageDto> messages;
}

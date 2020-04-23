package server.entities.dto;


import lombok.Data;

@Data
public class MessageDto {
    private String text;
    private Long from;
    private Long roomId;
    private String type;
}


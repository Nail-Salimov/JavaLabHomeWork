package server.bl.services;

import server.entities.dto.RoomDto;
import server.entities.dto.UserDto;
import server.entities.model.RoomModel;
import server.entities.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    RoomDto createRoom(UserDto userDto);

    List<RoomDto> findAllRooms();

    Optional<RoomDto> findRoomById(Long id);

    void join(RoomDto roomDto, UserDto userDto);
}

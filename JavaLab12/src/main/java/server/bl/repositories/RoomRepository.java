package server.bl.repositories;

import server.entities.model.LinkModel;
import server.entities.model.RoomModel;
import server.entities.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    void addLink(LinkModel linkModel);
    RoomModel addNewRoom(RoomModel roomModel);
    List<RoomModel> findAllRooms();
    List<LinkModel> findLinksByRoomId(RoomModel roomModel);

    Optional<RoomModel> findRoomById(Long id);
    boolean roomIsExist(Long id);

    boolean linkIsExist(RoomModel roomId, UserModel userModel);
    void addNewLink(LinkModel linkModel);
}

package server.addition.chat.lobby;

import org.springframework.stereotype.Component;
import server.addition.chat.entity.model.ChatModel;
import server.addition.chat.entity.model.Member;

import java.util.*;

@Component
public class Lobby {

    private List<Room> rooms;

    public Lobby() {
        rooms = new LinkedList<>();
    }

    public Room createRoom(ChatModel chatModel) {

        System.out.println(rooms);

        Room room = new Room();
        room.setId(chatModel.getId());
        Set<Member> members = chatModel.getMembers();

        Avatar[] avatars = new Avatar[members.size()];
        Member[] membersArray = new Member[members.size()];

        members.toArray(membersArray);

        avatars[0] = new Avatar((membersArray[0]).getMemberId());
        avatars[1] = new Avatar((membersArray[1]).getMemberId());
        room.addAvatars(avatars);
        avatars = null;
        rooms.add(room);
        return room;
    }

    public Optional<Room> findRoom(long[] membersId) {

        boolean find = false;
        for (Room room : rooms) {
            find = true;

            for (Long id : membersId) {
                if (!room.isBelong(id)) {
                    find = false;
                }
            }
            if (find) {
                return Optional.of(room);
            }
        }

        return Optional.empty();
    }

    public Optional<Room> findRoom(Long id) {
        for (Room room : rooms) {
            if (room.getId().equals(id)) {
                return Optional.of(room);
            }
        }

        return Optional.empty();
    }
}

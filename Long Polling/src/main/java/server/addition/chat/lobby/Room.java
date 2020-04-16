package server.addition.chat.lobby;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class Room {

    private Long id;

    private final List<Avatar> avatars;

    public Room(){
        avatars = new LinkedList<>();
    }

    public void addAvatars(Avatar[] newAvatars){
        avatars.addAll(Arrays.asList(newAvatars));
    }

    public List<Avatar> getAvatars(){
        return avatars;
    }

    public Avatar findMyAvatar(Long id){
        for (Avatar avatar : avatars){
            if(avatar.getId().equals(id)){
                return avatar;
            }
        }
        throw new IllegalStateException("user not belong this room");
    }

    public boolean isBelong(Long id){
        for (Avatar avatar : avatars){
            if(avatar.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

}

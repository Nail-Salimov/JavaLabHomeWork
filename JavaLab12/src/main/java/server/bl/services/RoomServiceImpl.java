package server.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.bl.repositories.RoomRepository;
import server.bl.repositories.UserRepository;
import server.entities.dto.LinkDto;
import server.entities.dto.RoomDto;
import server.entities.dto.UserDto;
import server.entities.model.LinkModel;
import server.entities.model.RoomModel;
import server.entities.model.UserModel;

import java.util.*;

@Component
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RoomDto createRoom(UserDto userDto) {
        UserModel userModel = UserModel.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .mail(userDto.getMail())
                .hashPassword(userDto.getPassword())
                .build();


        RoomModel roomModel = new RoomModel();
        Set<LinkModel> set = new HashSet<>();
        set.add(LinkModel.builder()
                .roomModel(roomModel)
                .userModel(userModel)
                .build());
        roomModel.setLinkModels(set);


        roomModel = roomRepository.addNewRoom(roomModel);
        return createRoomDto(roomModel);

    }

    @Override
    public List<RoomDto> findAllRooms() {
        List<RoomModel> models = roomRepository.findAllRooms();
        List<RoomDto> roomDtoList = new LinkedList<>();

        for (RoomModel model : models) {
            roomDtoList.add(createRoomDto(model));
        }
        return roomDtoList;
    }

    @Override
    public Optional<RoomDto> findRoomById(Long id) {
        if (!roomRepository.roomIsExist(id)) {
            return Optional.empty();
        }

        Optional<RoomModel> optionalRoomModel = roomRepository.findRoomById(id);
        if (!optionalRoomModel.isPresent()) {
            return Optional.empty();
        }

        RoomModel roomModel = optionalRoomModel.get();
        return Optional.of(createRoomDto(roomModel));

    }

    @Override
    public void join(RoomDto roomDto, UserDto userDto) {

        Optional<RoomModel> optionalRoomModel = roomRepository.findRoomById(roomDto.getId());
        if (!optionalRoomModel.isPresent()) {
            throw new IllegalArgumentException();
        }
        RoomModel roomModel = optionalRoomModel.get();

        Optional<UserModel> optionalUserModel = userRepository.findUserByMail(userDto.getMail());
        if (!optionalUserModel.isPresent()) {
            throw new IllegalArgumentException();
        }
        UserModel userModel = optionalUserModel.get();

        if(!roomRepository.linkIsExist(roomModel, userModel)){
            LinkModel linkModel = LinkModel.builder()
                    .roomModel(roomModel)
                    .userModel(userModel)
                    .build();
            roomRepository.addNewLink(linkModel);
        }
    }

    public RoomDto createRoomDto(RoomModel roomModel) {
        List<LinkModel> models = roomRepository.findLinksByRoomId(roomModel);
        Set<LinkDto> links = new HashSet<>();
        RoomDto roomDto = new RoomDto();


        for (LinkModel model : models) {
            links.add(LinkDto.builder()
                    .roomDto(roomDto)
                    .userDto(getUserDto(model.getUserModel()))
                    .build());
        }
        roomDto.setId(roomModel.getId());
        roomDto.setLinks(links);
        return roomDto;
    }

    public UserDto getUserDto(UserModel userModel) {
        return UserDto.builder()
                .name(userModel.getName())
                .mail(userModel.getMail())
                .id(userModel.getId())
                .password(userModel.getHashPassword())
                .build();
    }
}

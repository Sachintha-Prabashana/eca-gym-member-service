package lk.ijse.eca.memberservice.mapper;

import lk.ijse.eca.memberservice.dto.request.MemberRegisterRequestDTO;
import lk.ijse.eca.memberservice.entity.Member;
import lk.ijse.eca.memberservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import lk.ijse.eca.memberservice.dto.response.UserResponseDTO;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "trainer", ignore = true)
    Member toMemberEntity(MemberRegisterRequestDTO requestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "profileImageUrl", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUserEntity(MemberRegisterRequestDTO requestDTO);

    UserResponseDTO toUserResponseDTO(User user);
}

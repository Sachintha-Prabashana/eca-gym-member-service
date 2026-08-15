package lk.ijse.eca.memberservice.mapper;

import lk.ijse.eca.memberservice.dto.response.TrainerSummaryDTO;
import lk.ijse.eca.memberservice.dto.response.MemberDetailsResponseDTO;
import lk.ijse.eca.memberservice.dto.response.MemberResponseDTO;
import lk.ijse.eca.memberservice.dto.response.TrainerResponseDTO;
import lk.ijse.eca.memberservice.dto.response.UserResponseDTO;
import lk.ijse.eca.memberservice.entity.Member;
import lk.ijse.eca.memberservice.entity.Trainer;
import lk.ijse.eca.memberservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toUserResponseDTO(User user);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "profileImageUrl", source = "user.profileImageUrl")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "phone", source = "member.phone")
    @Mapping(target = "dob", source = "member.dateOfBirth")
    @Mapping(target = "gender", source = "member.gender")
    MemberResponseDTO toMemberResponseDTO(User user, Member member);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "profileImageUrl", source = "user.profileImageUrl")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "phone", source = "trainer.phone")
    @Mapping(target = "specialization", source = "trainer.specialization")
    TrainerResponseDTO toTrainerResponseDTO(User user, Trainer trainer);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    TrainerSummaryDTO toTrainerSummaryDTO(Trainer trainer);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "trainer", source = "trainer")
    MemberDetailsResponseDTO toMemberDetailsResponseDTO(Member member);
}

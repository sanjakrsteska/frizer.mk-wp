package mk.frizer.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import mk.frizer.model.Appointment;
import mk.frizer.model.BaseUser;
import mk.frizer.model.dto.BaseUserAddDTO;
import mk.frizer.model.dto.BaseUserUpdateDTO;
import mk.frizer.model.enums.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BaseUserService {
    List<BaseUser> getBaseUsers();
    Optional<BaseUser> getBaseUserById(Long id);
    Optional<BaseUser> createBaseUser(BaseUserAddDTO baseUserAddDTO);
    Optional<BaseUser> updateBaseUser(Long id, BaseUserUpdateDTO baseUserUpdateDTO);
    Optional<BaseUser> changeBaseUserPassword(Long id, String password);
    Optional<BaseUser> deleteBaseUserById(Long id);
}
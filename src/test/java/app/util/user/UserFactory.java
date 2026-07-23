package app.util.user;

import app.model.dto.user.UserDto;
import app.model.dto.user.UserRegisterRequest;
import app.model.entity.user.Country;
import app.model.entity.user.UserRole;
import app.service.user.AuthenticationUserDetails;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.UUID;

@UtilityClass
public class UserFactory {

    public static UserDto getUserDto() {

        return UserDto.builder()
                .id(UUID.randomUUID())
                .username("User")
                .role(UserRole.USER)
                .country(Country.BULGARIA)
                .isActive(true)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }

    public static AuthenticationUserDetails getAdminUser() {

        return AuthenticationUserDetails.builder()
                .id(UUID.randomUUID())
                .username("AdminUser")
                .role(UserRole.ADMIN)
                .isActive(true)
                .build();
    }

    public static AuthenticationUserDetails getUserPrincipal() {

        return AuthenticationUserDetails.builder()
                .id(UUID.randomUUID())
                .username("AdminUser")
                .role(UserRole.ADMIN)
                .isActive(true)
                .build();
    }

    public static UserRegisterRequest getUserRegisterRequest() {
        return UserRegisterRequest.builder()
                .username("User123")
                .password("Password")
                .country(Country.BULGARIA)
                .build();
    }
}

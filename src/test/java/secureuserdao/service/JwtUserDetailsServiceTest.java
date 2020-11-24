package secureuserdao.service;

import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.Test;
import secureuserdao.dao.UserRepository;
import secureuserdao.model.CreateUserRequest;
import secureuserdao.model.GetUserRequest;
import secureuserdao.model.UpdateUserRequest;

import java.util.Optional;
import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test
public class JwtUserDetailsServiceTest {

    private static final String GENERIC_PASSWORD = "0035890234u5jqgnuiw%letnw";

    private static final String GENERIC_PHONE_NO = "+6490078601";

    private static final String GENERIC_USER_NAME = "JOHN_DOE";

    @NotNull
    private PasswordEncoder passwordEncoder() {
        final PasswordEncoder mock = mock(PasswordEncoder.class);

        when(mock.encode(anyString())).thenReturn(GENERIC_PASSWORD);

        return mock;
    }

    @NotNull
    private CreateUserRequest createUserRequest(final String userName,
                                                final String password,
                                                final String phoneNumber) {

        final CreateUserRequest mock = mock(CreateUserRequest.class);

        when(mock.userName()).thenReturn(userName);
        when(mock.password()).thenReturn(password);
        when(mock.phoneNumber()).thenReturn(phoneNumber);

        return mock;
    }

    @NotNull
    private UpdateUserRequest updateUser(final Long userId,
                                         final String userName,
                                         final String password,
                                         final String phoneNumber) {

        final UpdateUserRequest mock = mock(UpdateUserRequest.class);

        when(mock.userId()).thenReturn(userId);
        when(mock.userName()).thenReturn(Optional.ofNullable(userName));
        when(mock.password()).thenReturn(Optional.ofNullable(password));
        when(mock.phoneNumber()).thenReturn(Optional.ofNullable(phoneNumber));

        return mock;
    }

    @NotNull
    private GetUserRequest getUserRequest(final Long userId) {

        final GetUserRequest mock = mock(GetUserRequest.class);

        when(mock.userId()).thenReturn(Optional.of(userId));

        return mock;
    }

    @NotNull
    private Predicate<String> isValidNumber(final boolean value) {

        //noinspection unchecked
        final Predicate<String> mock = mock(Predicate.class);

        when(mock.test(anyString())).thenReturn(value);

        return mock;
    }

    @NotNull
    private UserRepository userRepository() {
        return mock(UserRepository.class);
    }

    public void loadUserByUsernameThrowsUsernameNotFoundException() {

        final UserRepository userRepository = userRepository();

        when(userRepository.findByUsername(anyString())).thenReturn(null);

        final JwtUserDetailsService testMe = new JwtUserDetailsService(userRepository,
                passwordEncoder(),
                isValidNumber(true));

        Assertions.assertThatThrownBy(() -> testMe.loadUserByUsername(GENERIC_USER_NAME))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found with username: " + GENERIC_USER_NAME);
    }

    public void invalidPhoneNumberThrowsInvalidPhoneNumberExceptionOnSaveNewUser() {

        final UserRepository userRepository = userRepository();

        final JwtUserDetailsService testMe = new JwtUserDetailsService(userRepository,
                passwordEncoder(),
                isValidNumber(false));

        final CreateUserRequest request = createUserRequest(GENERIC_USER_NAME, GENERIC_PASSWORD, "dasdac");

        Assertions.assertThatThrownBy(() -> testMe.save(request))
                .isInstanceOf(InvalidPhoneNumberException.class)
                .hasMessage("invalid Phone Number [dasdac] Provided");
    }

    public void invalidPhoneNumberThrowsInvalidPhoneNumberExceptionOnUpdateUser() {

        final UserRepository userRepository = userRepository();

        final JwtUserDetailsService testMe = new JwtUserDetailsService(userRepository,
                passwordEncoder(),
                isValidNumber(false));

        final UpdateUserRequest request = updateUser(1L, GENERIC_USER_NAME,  GENERIC_PASSWORD, "dasdac");

        Assertions.assertThatThrownBy(() -> testMe.updateUser(request))
                .isInstanceOf(InvalidPhoneNumberException.class)
                .hasMessage("invalid Phone Number [dasdac] Provided");
    }

    public void usernameNotFoundExceptionOnGetUser() {

        final UserRepository userRepository = userRepository();

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        final JwtUserDetailsService testMe = new JwtUserDetailsService(userRepository,
                passwordEncoder(),
                isValidNumber(false));

        final GetUserRequest userRequest = getUserRequest(1L);

        Assertions.assertThatThrownBy(() -> testMe.getUser(userRequest))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("user with id 1 doesnt exist");
    }

}

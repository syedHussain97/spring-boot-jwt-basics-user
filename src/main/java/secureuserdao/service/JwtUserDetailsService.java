package secureuserdao.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import secureuserdao.dao.UserRepository;
import secureuserdao.model.*;

import java.util.ArrayList;
import java.util.function.Predicate;

public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder bcryptEncoder;

    private final Predicate<String> isValidNumber;

    public JwtUserDetailsService(final UserRepository userRepository,
                                 final PasswordEncoder bcryptEncoder,
                                 final Predicate<String> isValidNumber) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.isValidNumber = isValidNumber;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final DAOUser user = userRepository.findByUsername(username);

        if (null == user) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(user.getUsername(), user.getPassword(),
                new ArrayList<>(0));
    }

    public CreateUserResponse save(final CreateUserRequest request) {
        final DAOUser newUser = new DAOUser();

        newUser.setUsername(request.userName());

        final boolean isNumberValid = isValidNumber.test(request.phoneNumber());

        if (isNumberValid) {
            newUser.setPhoneNumber(request.phoneNumber());
        } else {
            throw new InvalidPhoneNumberException(request.phoneNumber());
        }

        newUser.setPassword(bcryptEncoder.encode(request.password()));

        userRepository.save(newUser);

        return CreateUserResponse.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .phoneNumber(newUser.getPhoneNumber())
                .build();
    }

    public GetUserResponse getUser(final GetUserRequest request) {

        final DAOUser daoUser;

        if (request.userId().isPresent()) {
            daoUser = userRepository.findById(request.userId().get()).orElseThrow(() ->
                    new UsernameNotFoundException(String.format("user with id %s doesnt exist",
                            request.userId().get())));
        } else {
            daoUser = userRepository.findByUsername(request.userName().get());
        }

        return GetUserResponse.of(daoUser.getId(), daoUser.getUsername(), daoUser.getPhoneNumber());
    }


    public UpdateDeleteUserResponse updateUser(final UpdateUserRequest request) {
        final DAOUser userToUpdate = new DAOUser();

        userToUpdate.setId(request.userId());

        request.userName().ifPresent(userToUpdate::setUsername);
        request.password().ifPresent(password -> userToUpdate.setPassword(bcryptEncoder.encode(password)));
        request.phoneNumber().ifPresent(phoneNo -> {
            final boolean isNumberValid = isValidNumber.test(phoneNo);

            if (isNumberValid) {
                userToUpdate.setPhoneNumber(phoneNo);
            } else {
                throw new InvalidPhoneNumberException(phoneNo);
            }
        });

        return UpdateDeleteUserResponse.of("User has been Updated");
    }

    public UpdateDeleteUserResponse remove(final DeleteUserRequest request) {
        userRepository.deleteById(request.userId());
        return UpdateDeleteUserResponse.of("user has been deleted");
    }
}
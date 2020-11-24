package secureuserdao.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import secureuserdao.dao.UserRepository;

import java.util.function.Predicate;

@Configuration
public class ServiceConfig {

    @Bean
    JwtUserDetailsService jwtUserDetailsService(final UserRepository userRepository,
                                             final PasswordEncoder bcryptEncoder,
                                             final Predicate<String> isValidNumber) {
        return new JwtUserDetailsService(userRepository, bcryptEncoder, isValidNumber);
    }

    @Bean
    Predicate<String> isValidNumber(){
        final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        return str -> {
            try {
                return phoneNumberUtil.isPossibleNumber(phoneNumberUtil.parse(str,
                        Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name()));
            } catch (final NumberParseException ignored) {
             return false;
            }
        };
    }
}

package secureuserdao.controller;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(final Exception exception) {

        final Map<String, Object> body = new HashMap<String, Object>(4);

        body.put("timestamp", LocalDateTime.now());
        body.put("status", INTERNAL_SERVER_ERROR.value());
        body.put("error", HttpStatus.valueOf(INTERNAL_SERVER_ERROR.value()).getReasonPhrase());
        body.put("exception", exception.getMessage());

        return new ResponseEntity<>(body, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<Object> illegalStateException(
            final HttpMessageConversionException httpMessageConversionException) {

        LOGGER.warn(httpMessageConversionException.getMessage());

        final Map<String, Object> body = new HashMap<String, Object>(4);

        body.put("timestamp", LocalDateTime.now());
        body.put("status", BAD_REQUEST.value());
        body.put("error", HttpStatus.valueOf(BAD_REQUEST.value()).getReasonPhrase());
        body.put("exception", httpMessageConversionException.getMostSpecificCause().getMessage());

        return new ResponseEntity<>(body, BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public void usernameNotFoundException(final UsernameNotFoundException e,
                                          final HttpServletResponse httpServletResponse) throws IOException {

        LOGGER.warn("user not found");
        httpServletResponse.sendError(HttpServletResponse.SC_GONE);
    }

}

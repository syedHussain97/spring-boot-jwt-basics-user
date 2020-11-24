package secureuserdao.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Value.Immutable
@Value.Style(allParameters = true,
        visibility = Value.Style.ImplementationVisibility.PUBLIC,
        typeImmutable = "UpdateUserRequest")
@JsonSerialize(as = UpdateUserRequest.class)
@JsonDeserialize(as = UpdateUserRequest.class)
public interface UpdateUserRequestType {

    @NotNull
    Long userId();

    @NotNull
    Optional<String> userName();

    @NotNull
    Optional<String> password();

    @NotNull
    Optional<String> phoneNumber();

    @Value.Check
    default void validateRequest() {
        Preconditions.checkArgument(password().isPresent() ||
                phoneNumber().isPresent() ||
                userName().isPresent());
    }
}
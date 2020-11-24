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
        typeImmutable = "GetUserRequest")
@JsonSerialize(as = GetUserRequest.class)
@JsonDeserialize(as = GetUserRequest.class)
public interface GetUserRequestType {

    @NotNull
    Optional<Long> userId();

    @NotNull
    Optional<String> userName();


    @Value.Check
    default void validateRequest() {
        Preconditions.checkArgument(userName().isPresent() ||
                userId().isPresent());
    }
}
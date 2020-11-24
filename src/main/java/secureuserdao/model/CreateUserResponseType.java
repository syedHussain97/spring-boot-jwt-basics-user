package secureuserdao.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

@Value.Immutable
@Value.Style(allParameters = true,
        visibility = Value.Style.ImplementationVisibility.PUBLIC,
        typeImmutable = "CreateUserResponse")
@JsonSerialize(as = CreateUserResponse.class)
@JsonDeserialize(as = CreateUserResponse.class)
public interface CreateUserResponseType {

    @NotNull
    @Value.Default
    default String message() {
        return "user has been created";
    }

   @NotNull
   long id();

    @NotNull
    String username();

    @NotNull
    String phoneNumber();

}

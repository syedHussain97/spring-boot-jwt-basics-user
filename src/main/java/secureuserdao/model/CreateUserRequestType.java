package secureuserdao.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

@Value.Immutable
@Value.Style(allParameters = true,
        visibility = Value.Style.ImplementationVisibility.PUBLIC,
        typeImmutable = "CreateUserRequest")
@JsonSerialize(as = CreateUserRequest.class)
@JsonDeserialize(as = CreateUserRequest.class)
public interface CreateUserRequestType {

    @NotNull
    String userName();

    @NotNull
    String password();

    @NotNull
    String phoneNumber();
}
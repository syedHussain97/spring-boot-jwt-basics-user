package secureuserdao.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

@Value.Immutable
@Value.Style(allParameters = true,
        visibility = Value.Style.ImplementationVisibility.PUBLIC,
        typeImmutable = "DeleteUserRequest")
@JsonSerialize(as = DeleteUserRequest.class)
@JsonDeserialize(as = DeleteUserRequest.class)
public interface DeleteUserRequestType {

    @NotNull
    Long userId();
}
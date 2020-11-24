package secureuserdao.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

@Value.Immutable
@Value.Style(allParameters = true,
        visibility = Value.Style.ImplementationVisibility.PUBLIC,
        typeImmutable = "GetUserResponse")
@JsonSerialize(as = GetUserResponse.class)
@JsonDeserialize(as = GetUserResponse.class)
public interface GetUserResponseType {

   @NotNull
   long id();

    @NotNull
    String username();

    @NotNull
    String phoneNumber();

}

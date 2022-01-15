package yar.web_lab4.DTO;

import lombok.*;

@Data
public class UserDTO {
    @NonNull
    private String username;

    @NonNull
    private String password;
}

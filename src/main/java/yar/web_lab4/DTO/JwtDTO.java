package yar.web_lab4.DTO;

import lombok.*;

@Data
public class JwtDTO {
    @NonNull
    private String username;

    @NonNull
    private String jwt;
}

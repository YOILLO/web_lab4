package yar.web_lab4.DTO;

import lombok.*;

@Data
public class EntryDTO {
    @NonNull
    private double x;

    @NonNull
    private double y;

    @NonNull
    private double r;
}

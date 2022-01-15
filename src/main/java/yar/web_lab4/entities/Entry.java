package yar.web_lab4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data //generate getters and setters
@NoArgsConstructor
@Entity //data base
@Table(name = "wl4_entry")
public class Entry {
    @Id
    @SequenceGenerator(name = "wl4_entry_id_seq", sequenceName = "wl4_entry_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wl4_entry_id_seq")
    @JsonIgnore
    private long id;

    private double x;

    private double y;

    private double r;

    private boolean result;

    @ManyToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private User user;

    public Entry(double x, double y, double r, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.user = user;
        check();
    }

    public void check() {
        if (r > 0) {
            result = (x <= 0 && y >= 0 && x >= y - r / 2) ||
                    (x <= 0 && y <= 0 && x >= -r && y >= -r / 2) ||
                    (x >= 0 && y >= 0 && x * x + y * y <= (r / 2) * (r / 2));
        } else {
            result = (-x <= 0 && -y >= 0 && -x >= -y + r / 2) ||
                    (-x <= 0 && -y <= 0 && -x >= r && -y >= r / 2) ||
                    (-x >= 0 && -y >= 0 && x * x + y * y <= (r / 2) * (r / 2));
        }
    }
}
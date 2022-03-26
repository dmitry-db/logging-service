package liga.medical.logging.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LogEntity {

    private Long id;

    private String type;

    private LocalDateTime dateTime;

    private String nameMethod;

    private String fullNameClass;

    private String nameUser;

    public LogEntity(String type, LocalDateTime dateTime, String nameMethod, String fullNameClass, String nameUser) {
        this.type = type;
        this.dateTime = dateTime;
        this.nameMethod = nameMethod;
        this.fullNameClass = fullNameClass;
        this.nameUser = nameUser;
    }
}

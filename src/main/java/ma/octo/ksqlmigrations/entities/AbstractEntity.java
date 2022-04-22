package ma.octo.ksqlmigrations.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbstractEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    private void setInsertDate() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    private void setUpdateDate() {
        this.updatedAt = Instant.now();
    }
}

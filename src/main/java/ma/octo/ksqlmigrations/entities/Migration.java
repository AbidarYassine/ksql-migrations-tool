package ma.octo.ksqlmigrations.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "migration")
public class Migration extends AbstractEntity {

    private String fileName;
    private String fileContent;
    private String version;
    private String hashFile;

}

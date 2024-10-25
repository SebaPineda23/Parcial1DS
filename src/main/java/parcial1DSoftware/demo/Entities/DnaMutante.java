package parcial1DSoftware.demo.Entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "dna_mutante")
public class DnaMutante extends Base{
    private String dna;
    private boolean isMutant;
}

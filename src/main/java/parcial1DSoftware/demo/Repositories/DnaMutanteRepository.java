package parcial1DSoftware.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import parcial1DSoftware.demo.Entities.DnaMutante;
@Repository
public interface DnaMutanteRepository extends JpaRepository<DnaMutante, Long> {
}

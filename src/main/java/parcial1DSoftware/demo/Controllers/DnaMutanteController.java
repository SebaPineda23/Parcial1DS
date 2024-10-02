package parcial1DSoftware.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parcial1DSoftware.demo.Entities.DnaMutante;
import parcial1DSoftware.demo.Services.DnaMutanteService;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/mutantes")
public class DnaMutanteController {
    @Autowired
    private DnaMutanteService dnaMutanteService;

    @PostMapping()
    public ResponseEntity<?> comprobarMutante(@RequestBody Map<String, String[]> dnaMutante){
        String[] dna = dnaMutante.get("dna");
        if (dna == null || dna.length == 0) {
            return ResponseEntity.badRequest().build();
        }

        boolean isMutant = dnaMutanteService.esMutante(dna);

        if (isMutant) {
            return ResponseEntity.ok().build(); // Respuesta 200 OK para mutantes
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Respuesta 403 Forbidden para humanos
        }
    }
    @GetMapping("/stats")
    public ResponseEntity<?> estadisticasMutantes(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(dnaMutanteService.getEstadisticas());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

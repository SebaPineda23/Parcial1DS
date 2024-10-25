package parcial1DSoftware.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            return ResponseEntity.ok().body("{\"mensaje\":\"El DNA es totalmente mutante.\"}"); // Respuesta 200 OK para mutantes
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"mensaje\":\"El DNA es humano.\"}"); // Respuesta 403 Forbidden para humanos
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMutante(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(dnaMutanteService.delete(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteMutante(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(dnaMutanteService.deleteAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
}

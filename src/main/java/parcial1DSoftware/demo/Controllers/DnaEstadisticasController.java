package parcial1DSoftware.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parcial1DSoftware.demo.Services.DnaMutanteService;

@RestController
@CrossOrigin("*")
@RequestMapping("/dnaEstadisticas")
public class DnaEstadisticasController {
    @Autowired
    private DnaMutanteService dnaMutanteService;
    @GetMapping("/stats")
    public ResponseEntity<?> estadisticasMutantes(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(dnaMutanteService.getEstadisticas());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

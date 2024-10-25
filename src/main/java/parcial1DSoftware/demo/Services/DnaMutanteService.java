package parcial1DSoftware.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parcial1DSoftware.demo.Entities.DnaMutante;
import parcial1DSoftware.demo.Repositories.DnaMutanteRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class DnaMutanteService {
    @Autowired
    private DnaMutanteRepository dnaMutanteRepository;

    private long contadorMutanteDna = 0;
    private long contadorHumanoDna = 0;

    public boolean esMutante(String[] dna) {

        String dnaString = Arrays.toString(dna);

        boolean siEsMutante = confirmacionMutante(dna);

        DnaMutante dnaMutante = new DnaMutante();
        dnaMutante.setDna(dnaString);
        dnaMutante.setMutant(siEsMutante);
        dnaMutanteRepository.save(dnaMutante);

        if (siEsMutante) {
            contadorMutanteDna++;
        } else {
            contadorHumanoDna++;
        }

        return siEsMutante;
    }

    public Map<String, Object> getEstadisticas() {
        double ratio = contadorHumanoDna > 0 ? (double) contadorMutanteDna / contadorHumanoDna : 0.0;
        Map<String, Object> stats = new HashMap<>();
        stats.put("count_mutant_dna", contadorMutanteDna);
        stats.put("count_human_dna", contadorHumanoDna);
        stats.put("ratio", ratio);
        return stats;
    }

    public boolean confirmacionMutante(String[] dna) {
        if (dna == null || dna.length == 0) {
            throw new IllegalArgumentException("El DNA no puede ser nulo o vacío.");
        }

        int n = dna.length;
        validarFormatoDna(dna, n);
        int secuenciasEncontradas = contarSecuencias(dna, n, 0, 0, 0);

        return secuenciasEncontradas >= 2;
    }

    private void validarFormatoDna(String[] dna, int n) {
        validarFila(dna, n, 0);
    }

    private void validarFila(String[] dna, int n, int index) {
        if (index >= n) return;

        String row = dna[index];
        if (row.length() != n) {
            throw new IllegalArgumentException("Todas las filas deben tener la misma longitud. El DNA debe ser cuadrado (NxN)");
        }
        if (!row.matches("[ATCG]+")) {
            throw new IllegalArgumentException("El DNA contiene caracteres no válidos. Solo se permiten A, T, C, G.");
        }

        validarFila(dna, n, index + 1);
    }

    // Método principal de recursión para buscar secuencias
    private int contarSecuencias(String[] dna, int n, int row, int col, int secuenciasEncontradas) {
        if (row >= n) return secuenciasEncontradas;
        if (col <= n - 4 && checkSequence(dna, row, col, 0, 1)) {
            secuenciasEncontradas++;
        }
        if (row <= n - 4 && checkSequence(dna, row, col, 1, 0)) {
            secuenciasEncontradas++;
        }
        if (row <= n - 4 && col <= n - 4 && checkSequence(dna, row, col, 1, 1)) {
            secuenciasEncontradas++;
        }
        if (row <= n - 4 && col >= 3 && checkSequence(dna, row, col, 1, -1)) {
            secuenciasEncontradas++;
        }

        if (secuenciasEncontradas >= 2) return secuenciasEncontradas;
        if (col + 1 < n) {
            return contarSecuencias(dna, n, row, col + 1, secuenciasEncontradas);
        } else {
            return contarSecuencias(dna, n, row + 1, 0, secuenciasEncontradas);
        }
    }

    private boolean checkSequence(String[] dna, int row, int col, int rowDir, int colDir) {
        return checkRecursively(dna, row, col, rowDir, colDir, dna[row].charAt(col), 1);
    }

    private boolean checkRecursively(String[] dna, int row, int col, int rowDir, int colDir, char target, int count) {
        if (count == 4) return true;  // Encontró una secuencia de 4 iguales
        if (row + rowDir >= dna.length || col + colDir >= dna.length || row + rowDir < 0 || col + colDir < 0) {
            return false;
        }
        if (dna[row + rowDir].charAt(col + colDir) != target) {
            return false;
        }

        return checkRecursively(dna, row + rowDir, col + colDir, rowDir, colDir, target, count + 1);
    }

    public boolean delete(Long id) throws Exception{
        try{
            if (dnaMutanteRepository.existsById(id)){
                dnaMutanteRepository.deleteById(id);
                return true;
            }else{
                throw new Exception();
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean deleteAll() throws Exception{
        try{
            dnaMutanteRepository.deleteAll();
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}

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

    private static final int SECUENCIA_MUTANTE = 4;

    public boolean esMutante(String[] dna) {

        String dnaString = Arrays.toString(dna);

        if (dnaMutanteRepository.existsByDna(dnaString)) {
            throw new RuntimeException("El ADN ya ha sido registrado");
        }

        boolean isMutant = confirmacionMutante(dna);

        DnaMutante dnaMutante = new DnaMutante();
        dnaMutante.setDna(dnaString);
        dnaMutante.setMutant(isMutant);
        dnaMutanteRepository.save(dnaMutante);

        if (isMutant) {
            contadorMutanteDna++;
        } else {
            contadorHumanoDna++;
        }

        return isMutant;
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
        int length = dna.length;
        int secuenciasEncontradas = 0;

        for (int row = 0; row < length; row++) {
            for (int col = 0; col < length; col++) {
                if (col <= length - SECUENCIA_MUTANTE && verificaHorizontal(dna, row, col)) {
                    secuenciasEncontradas++;
                }
                if (row <= length - SECUENCIA_MUTANTE && verificaVertical(dna, row, col)) {
                    secuenciasEncontradas++;
                }
                if (row <= length - SECUENCIA_MUTANTE && col <= length - SECUENCIA_MUTANTE && verificaDiagonalDerecha(dna, row, col)) {
                    secuenciasEncontradas++;
                }
                if (row <= length - SECUENCIA_MUTANTE && col >= SECUENCIA_MUTANTE - 1 && verificaDiagonalIzquierda(dna, row, col)) {
                    secuenciasEncontradas++;
                }

                // Si se encuentran más de una secuencia, confirmamos que es mutante y detenemos la búsqueda
                if (secuenciasEncontradas > 1) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean verificaHorizontal(String[] dna, int row, int col) {
        char inicial = dna[row].charAt(col);
        for (int i = 1; i < SECUENCIA_MUTANTE; i++) {
            if (dna[row].charAt(col + i) != inicial) {
                return false;
            }
        }
        return true;
    }

    private boolean verificaVertical(String[] dna, int row, int col) {
        char inicial = dna[row].charAt(col);
        for (int i = 1; i < SECUENCIA_MUTANTE; i++) {
            if (dna[row + i].charAt(col) != inicial) {
                return false;
            }
        }
        return true;
    }

    private boolean verificaDiagonalDerecha(String[] dna, int row, int col) {
        char inicial = dna[row].charAt(col);
        for (int i = 1; i < SECUENCIA_MUTANTE; i++) {
            if (dna[row + i].charAt(col + i) != inicial) {
                return false;
            }
        }
        return true;
    }

    private boolean verificaDiagonalIzquierda(String[] dna, int row, int col) {
        char inicial = dna[row].charAt(col);
        for (int i = 1; i < SECUENCIA_MUTANTE; i++) {
            if (dna[row + i].charAt(col - i) != inicial) {
                return false;
            }
        }
        return true;
    }
}

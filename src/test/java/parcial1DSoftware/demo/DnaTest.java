package parcial1DSoftware.demo;

import org.junit.jupiter.api.*;

import parcial1DSoftware.demo.Services.DnaMutanteService;

import static org.junit.jupiter.api.Assertions.*;

public class DnaTest {

    private static DnaMutanteService dnaMutanteService;

    @BeforeAll
    public static void setUp() {
        dnaMutanteService = new DnaMutanteService();
    }

    @AfterAll
    public static void tearDown() {
        dnaMutanteService = null;
    }

    @Nested
    @DisplayName("Pruebas de Confirmación de Mutante")
    class ConfirmacionMutanteTests {

        @Test
        @DisplayName("Debería confirmar que el DNA es mutante")
        public void testConfirmacionMutante() {
            String[] dna = {
                    "ATGGGA",
                    "CAGGGC",
                    "TTATGT",
                    "AGGAGG",
                    "CCCTTA",
                    "TATAAG"
            };
            boolean resultado = dnaMutanteService.confirmacionMutante(dna);
            assertTrue(resultado);
        }

        @Test
        @DisplayName("Debería lanzar excepción si el DNA contiene caracteres no válidos")
        public void testDNAInvalido() {
            String[] dna = {
                    "ATGGGA",
                    "HAGGGC",
                    "TTATGT",
                    "AGGAGG",
                    "CCCTTA",
                    "TATAAB"
            };
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
                dnaMutanteService.confirmacionMutante(dna);
            });
            assertEquals("El DNA contiene caracteres no válidos. Solo se permiten A, T, C, G.", thrown.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar excepción si el DNA es nulo")
        public void testDNANulo() {
            String[] dna = {};
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
                dnaMutanteService.confirmacionMutante(dna);
            });
            assertEquals("El DNA no puede ser nulo o vacío.", thrown.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar excepción si hay elementos vacíos en el DNA")
        public void testDNAElementoNulos() {
            String[] dna = {"", "", "", ""};
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
                dnaMutanteService.confirmacionMutante(dna);
            });
            assertEquals("Cada cadena de DNA debe tener una longitud de exactamente 6 caracteres.", thrown.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar excepción si el DNA contiene números")
        public void testDnaArrayConNumeros() {
            String[] dna = {"012333", "203332", "110131", "033033", "222110", "101003"};
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
                dnaMutanteService.confirmacionMutante(dna);
            });
            assertEquals("El DNA contiene caracteres no válidos. Solo se permiten A, T, C, G.", thrown.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar excepción si el DNA no es cuadrado(NxN)")
        public void testDnaMxN() {
            String[] dna = {
                    "ATCGTA",
                    "GGCATT",
                    "TTAGGC",
                    "CCTGAA"
            };
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
                dnaMutanteService.confirmacionMutante(dna);
            });
            assertEquals("Todas las filas deben tener la misma longitud. Es decir el DNA debe ser cuadrado(NxN)", thrown.getMessage());
        }
    }
}

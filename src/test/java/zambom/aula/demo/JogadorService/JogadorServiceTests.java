package zambom.aula.demo.JogadorService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import zambom.aula.demo.jogador.Jogador;
import zambom.aula.demo.jogador.JogadorNaoEncontradoException;
import zambom.aula.demo.jogador.JogadorRepository;
import zambom.aula.demo.jogador.JogadorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class JogadorServiceTests {

    @InjectMocks
    private JogadorService jogadorService;

    @Mock
    private JogadorRepository jogadorRepository;

    @Test
    public void testGetJogadorWhenJogadorIsNotNull() {

        Jogador jogador = new Jogador();
        jogador.setId("jogador-1");

        Mockito.when(jogadorRepository.findById("jogador-1")).thenReturn(Optional.of(jogador));

        List<Jogador> jogadorRetorno = jogadorService.getJogadores(Optional.of("jogador-1"));

        Assertions.assertNotNull(jogadorRetorno);
        Assertions.assertEquals("jogador-1", jogadorRetorno.get(0).getId());
    }

    @Test
    public void testGetJogadorWhenJogadorIsNull() {

        Mockito.when(jogadorRepository.findById("jogador-1")).thenReturn(Optional.empty());

        Assertions.assertThrows(JogadorNaoEncontradoException.class,
                () -> jogadorService.getJogadores(Optional.of("jogador-1")));
    }

    @Test
    public void testGetJogadoresWhenIdIsNotPassed() {
        // Arrange
        List<Jogador> expectedJogadores = new ArrayList<>();
        Jogador jogador = new Jogador();
        jogador.setId("jogador-1");
        expectedJogadores.add(jogador);

        Mockito.when(jogadorRepository.findAll()).thenReturn(expectedJogadores);

        // Act
        List<Jogador> result = jogadorService.getJogadores(Optional.empty());

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedJogadores.size(), result.size());
        Assertions.assertEquals(expectedJogadores.get(0).getId(), result.get(0).getId());
    }

    @Test
    public void testSaveWhenNameIsNull() {
        // Arrange
        Jogador jogador = new Jogador();
        jogador.setIdade(20);

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> jogadorService.save(jogador));
    }

    @Test
    public void testSaveWhenAgeIsLessThan15() {
        // Arrange
        Jogador jogador = new Jogador();
        jogador.setNome("Test");
        jogador.setIdade(14);

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> jogadorService.save(jogador));
    }

    @Test
    public void testSaveWhenEverythingIsCorrect() {
        // Arrange
        Jogador jogador = new Jogador();
        jogador.setNome("Test");
        jogador.setIdade(20);

        Mockito.when(jogadorRepository.save(jogador)).thenReturn(jogador);

        // Act
        Jogador savedJogador = jogadorService.save(jogador);

        // Assert
        Assertions.assertNotNull(savedJogador);
        Assertions.assertEquals(jogador.getNome(), savedJogador.getNome());
        Assertions.assertEquals(jogador.getIdade(), savedJogador.getIdade());
    }



}

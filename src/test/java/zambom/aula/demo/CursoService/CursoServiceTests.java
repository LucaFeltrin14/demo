package zambom.aula.demo.CursoService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import zambom.aula.demo.curso.Curso;
import zambom.aula.demo.curso.CursoRepository;
import zambom.aula.demo.curso.CursoService;
import zambom.aula.demo.usuario.Usuario;
import zambom.aula.demo.usuario.UsuarioService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTests {

    @InjectMocks
    private CursoService cursoService;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private UsuarioService usuarioService;

    @Test
    public void testGetCursoWhenCursoIsNotNull() {

        Curso curso = new Curso();
        curso.setNome("curso-1");

        Mockito.when(cursoRepository.findByNome("curso-1")).thenReturn(Collections.singletonList(curso));

        List<Curso> cursoRetorno = cursoService.getCursos(Optional.of("curso-1"));

        Assertions.assertNotNull(cursoRetorno);
        Assertions.assertEquals("curso-1", cursoRetorno.get(0).getNome());
    }

    @Test
    public void testGetCursoWhenCursoIsNull() {

        Mockito.when(cursoRepository.findByNome("curso-1")).thenReturn(Collections.emptyList());

        List<Curso> cursoRetorno = cursoService.getCursos(Optional.of("curso-1"));

        Assertions.assertTrue(cursoRetorno.isEmpty());
    }

    @Test
    public void testGetCursosWhenNomeIsNotPassed() {
        // Arrange
        List<Curso> expectedCursos = new ArrayList<>();
        Curso curso = new Curso();
        curso.setNome("curso-1");
        expectedCursos.add(curso);

        Mockito.when(cursoRepository.findAll()).thenReturn(expectedCursos);

        // Act
        List<Curso> result = cursoService.getCursos(Optional.empty());

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedCursos.size(), result.size());
        Assertions.assertEquals(expectedCursos.get(0).getNome(), result.get(0).getNome());
    }


    @Test
    public void testSaveWhenProfessorExists() {
        // Arrange
        Curso curso = new Curso();
        curso.setCpf_professor("cpf-1");

        Usuario professor = new Usuario();
        professor.setCpf("cpf-1");

        Mockito.when(usuarioService.getUsuario("cpf-1")).thenReturn(new ResponseEntity<>(professor, HttpStatus.OK));
        Mockito.when(cursoRepository.save(curso)).thenReturn(curso);

        // Act
        Curso savedCurso = cursoService.save(curso);

        // Assert
        Assertions.assertNotNull(savedCurso);
        Assertions.assertEquals(curso, savedCurso);
    }

    @Test
    public void testSaveWhenProfessorDoesNotExist() {
        // Arrange
        Curso curso = new Curso();
        curso.setCpf_professor("cpf-1");

        Mockito.when(usuarioService.getUsuario("cpf-1")).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> cursoService.save(curso));
    }

    @Test
    public void testAddAlunoWhenAlunoAndCursoExistAndCursoIsNotFull() {
        // Arrange
        Curso curso = new Curso();
        curso.setId("curso-1");
        curso.setCapacidade_maxima(2);

        Usuario aluno = new Usuario();
        aluno.setCpf("cpf-1");

        Mockito.when(usuarioService.getUsuario("cpf-1")).thenReturn(new ResponseEntity<>(aluno, HttpStatus.OK));
        Mockito.when(cursoRepository.findById("curso-1")).thenReturn(Optional.of(curso));
        Mockito.when(cursoRepository.save(curso)).thenReturn(curso);

        // Act
        Curso updatedCurso = cursoService.addAluno("cpf-1", "curso-1");

        // Assert
        Assertions.assertNotNull(updatedCurso);
        Assertions.assertEquals(1, updatedCurso.getAlunos().size());
        Assertions.assertEquals(aluno, updatedCurso.getAlunos().get(0));
    }

    @Test
    public void testAddAlunoWhenAlunoDoesNotExist() {
        // Arrange
        Mockito.when(usuarioService.getUsuario("cpf-1")).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> cursoService.addAluno("cpf-1", "curso-1"));
    }

    @Test
    public void testAddAlunoWhenCursoDoesNotExist() {
        // Arrange
        Usuario aluno = new Usuario();
        aluno.setCpf("cpf-1");

        Mockito.when(usuarioService.getUsuario("cpf-1")).thenReturn(new ResponseEntity<>(aluno, HttpStatus.OK));
        Mockito.when(cursoRepository.findById("curso-1")).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> cursoService.addAluno("cpf-1", "curso-1"));
    }

    @Test
    public void testAddAlunoWhenCursoIsFull() {
        // Arrange
        Curso curso = new Curso();
        curso.setId("curso-1");
        curso.setCapacidade_maxima(1);
        curso.getAlunos().add(new Usuario());

        Usuario aluno = new Usuario();
        aluno.setCpf("cpf-1");

        Mockito.when(usuarioService.getUsuario("cpf-1")).thenReturn(new ResponseEntity<>(aluno, HttpStatus.OK));
        Mockito.when(cursoRepository.findById("curso-1")).thenReturn(Optional.of(curso));

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> cursoService.addAluno("cpf-1", "curso-1"));
    }
}
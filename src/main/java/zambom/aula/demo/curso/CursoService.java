package zambom.aula.demo.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zambom.aula.demo.usuario.Usuario;
import zambom.aula.demo.usuario.UsuarioService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Curso> getCursos(Optional<String> nomeCurso) {
        if (nomeCurso.isPresent()) {
            return cursoRepository.findByNome(nomeCurso.get());
        } else {
            return cursoRepository.findAll();
        }
    }

    public Curso save(Curso curso) {
        ResponseEntity<Usuario> response = usuarioService.getUsuario(curso.getCpf_professor());
        if (response.getStatusCode() == HttpStatus.OK) {
            return cursoRepository.save(curso);
        } else {
            throw new IllegalArgumentException("Professor with CPF " + curso.getCpf_professor() + " does not exist");
        }
    }

    public Curso addAluno(String cpf, String cursoId) {
        ResponseEntity<Usuario> response = usuarioService.getUsuario(cpf);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("User with CPF " + cpf + " does not exist");
        }

        Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new IllegalArgumentException("Course with ID " + cursoId + " does not exist"));

        if (curso.getAlunos().size() >= curso.getCapacidade_maxima()) {
            throw new IllegalArgumentException("Course is already full");
        }

        Usuario aluno = response.getBody();
        curso.getAlunos().add(aluno);
        return cursoRepository.save(curso);
    }


}



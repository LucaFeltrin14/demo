package zambom.aula.demo.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zambom.aula.demo.usuario.RetornaUsuarioDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> getCursos(@RequestParam Optional<String> nomeCurso) {
        List<Curso> cursos = cursoService.getCursos(nomeCurso);
        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Curso> saveCurso(@RequestBody Curso curso) {
        Curso savedCurso = cursoService.save(curso);
        return new ResponseEntity<>(savedCurso, HttpStatus.CREATED);
    }

    @PutMapping("/{cursoId}/alunos")
    public ResponseEntity<Curso> addAluno(@PathVariable String cursoId, @RequestBody RetornaUsuarioDTO usuarioDto) {
        Curso updatedCurso = cursoService.addAluno(usuarioDto.getCpf(), cursoId);
        return new ResponseEntity<>(updatedCurso, HttpStatus.OK);
    }
}
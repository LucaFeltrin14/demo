package zambom.aula.demo.jogador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zambom.aula.demo.time.RetornaTimeDTO;
import zambom.aula.demo.time.Time;
import zambom.aula.demo.time.TimeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jogador")
public class JogadorController {

    @Autowired
    private TimeService timeService;

    @Autowired
    private JogadorService jogadorService;

    @GetMapping
    public ResponseEntity<List<Jogador>> getJogadores(@RequestParam Optional<String> jogadorId) {
        return ResponseEntity.ok(jogadorService.getJogadores(jogadorId));
    }

    @PostMapping
    public ResponseEntity<Jogador> saveJogador(@RequestBody Jogador jogador) {
        return ResponseEntity.ok(jogadorService.save(jogador));
    }

    @PutMapping("/{jogadorId}/time")
    public ResponseEntity<Jogador> addTimeToJogador(@PathVariable String jogadorId, @RequestBody RetornaTimeDTO timeDto) {
        return ResponseEntity.ok(jogadorService.addTimeToJogador(jogadorId, timeDto.getId()));
    }

}
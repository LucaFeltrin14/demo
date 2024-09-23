package zambom.aula.demo.curso;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import zambom.aula.demo.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
public class Curso {

    @Id
    private String id;
    private String nome;
    private String descricao;
    private Integer capacidade_maxima;
    private String cpf_professor;
    private List<Usuario> alunos = new ArrayList<>();



}

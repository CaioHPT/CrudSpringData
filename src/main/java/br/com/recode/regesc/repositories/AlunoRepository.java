package br.com.recode.regesc.repositories;

import br.com.recode.regesc.orm.Aluno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlunoRepository extends CrudRepository<Aluno, Long> {
    List<Aluno> findByNomeStartingWith(String nome);
    List<Aluno> findByNomeStartingWithAndIdadeLessThanEqual(String nome, Integer idade);

    @Query("SELECT a from Aluno a where a.nome like :nome% and a.idade >= :idade")
    List<Aluno> findNomeIdadeIgualOuMaior(String nome, Integer idade);

    @Query("SELECT a from Aluno a  INNER JOIN a.disciplinas d where a.nome like :nome% and a.idade >= :idade and d.nome like :nomeDisciplina%")
    List<Aluno> findNomeIdadeIgualOuMaiorMatriculado(String nome, Integer idade, String nomeDisciplina);

}

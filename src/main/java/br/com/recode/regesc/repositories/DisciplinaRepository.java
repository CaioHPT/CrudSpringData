package br.com.recode.regesc.repositories;

import br.com.recode.regesc.orm.Disciplina;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository  extends CrudRepository<Disciplina, Long> {

}

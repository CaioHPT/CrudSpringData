package br.com.recode.regesc.service;

import br.com.recode.regesc.orm.Aluno;
import br.com.recode.regesc.orm.Disciplina;
import br.com.recode.regesc.orm.Professor;
import br.com.recode.regesc.repositories.AlunoRepository;
import br.com.recode.regesc.repositories.DisciplinaRepository;
import br.com.recode.regesc.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Transactional
    public void menu(Scanner scanner){
        Boolean isTrue = true;
        while(isTrue){
            System.out.println("O que deseja fazer?");
            System.out.println("0 - SAIR");
            System.out.println("1 - Adicionar Disciplina no BD");
            System.out.println("2 - Atualizar Disciplina no BD");
            System.out.println("3 - Deletar Disciplina no BD");
            System.out.println("4 - Consultar Disciplina no BD");
            System.out.println("5 - Matricular alunos");
            try{
                int opcao = scanner.nextInt();
                switch (opcao){
                    case 0:
                        isTrue = false;
                        break;
                    case 1:
                        this.save(scanner);
                        break;
                    case 2:
                        this.update(scanner);
                        break;
                    case 3:
                        this.delete(scanner);
                        break;
                    case 4:
                        this.selectAll();
                        break;
                    case 5:
                        this.matricularAlunos(scanner);
                        break;
                    default:
                        System.out.println("Digite uma opção valida");
                }
            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    private void save(Scanner sc){
        System.out.println("Digite o nome da Disciplina");
        sc.nextLine();
        String nome = sc.nextLine();
        System.out.println("Digite o Semestre");
        Integer semestre = sc.nextInt();
        System.out.println("Professor Id:");
        Long id = sc.nextLong();
        Optional<Professor> professor = professorRepository.findById(id);
        if (professor.isPresent()){
            Professor prof = professor.get();
            Disciplina disciplina = new Disciplina(nome, semestre, prof);
            disciplinaRepository.save(disciplina);
            System.out.println("Disciplina salvo com sucesso");
        }else{
            System.out.println("Disciplina invalido");
        }
    }

    private void update(Scanner sc){
        System.out.println("Digite o id da disciplina:");
        Long idDisciplina = sc.nextLong();
        Optional<Disciplina> disciplina = disciplinaRepository.findById(idDisciplina);
        if(disciplina.isPresent()) {
            System.out.println("Digite o nome da Disciplina");
            sc.nextLine();
            String nome = sc.nextLine();
            System.out.println("Digite o Semestre");
            Integer semestre = sc.nextInt();
            System.out.println("Professor Id:");
            Long id = sc.nextLong();
            Optional<Professor> professor = professorRepository.findById(id);
            if (professor.isPresent()){
                Professor prof = professor.get();
                disciplina.get().setNome(nome);
                disciplina.get().setSemestre(semestre);
                disciplina.get().setProfessor(prof);
                disciplinaRepository.save(disciplina.get());
                System.out.println("Disciplina atualizada com sucesso");
            }else{
                System.out.println("Professor invalido");
            }
        }
        else{
            System.out.println("Disciplina inexistente");
        }
    }

    @Transactional
    private void selectAll(){
        Iterable<Disciplina> disciplinas = disciplinaRepository.findAll();
        disciplinas.forEach(disciplina -> {
            System.out.println("disciplina: " + disciplina.getId());
            System.out.println("Nome: " + disciplina.getNome());
            System.out.println("Semestre: " + disciplina.getSemestre());
            System.out.println("Nome do professor: " + disciplina.getProfessor().getNome());
            disciplina.getAlunos().forEach(aluno -> {
                if(aluno.getDisciplinas() != null) {
                    System.out.println("Aluno");
                    System.out.println("Nome:" + aluno.getNome());
                    System.out.println("Idade:" + aluno.getIdade());
                    System.out.println();
                }else{
                    System.out.println("Sem alunos para essa disciplina");
                }
            });
            System.out.println();
        });
    }

    private void delete(Scanner sc){
        System.out.println("Digite o id da disciplina a ser deletada");
        Long id = sc.nextLong();
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
        if(disciplina.isPresent()){
            disciplinaRepository.delete(disciplina.get());
            System.out.println("Deletado com sucesso");
        }else{
            System.out.println("Disciplina inexistente");
        }
    }

    private Set<Aluno> matricular(Scanner scanner){
        Boolean isTrue = true;
        Set<Aluno> alunos = new HashSet<>();
        while(isTrue){
            System.out.println("ID do aluno a ser matriculado");
            Long id = scanner.nextLong();
            Optional<Aluno> aluno = alunoRepository.findById(id);
            if (aluno.isPresent()){
                alunos.add(aluno.get());
                System.out.println("Deseja adicionar mais algum aluno?");
                String opcao = scanner.next().toUpperCase(Locale.ROOT);
                if(opcao.equals("N")){
                    isTrue = false;
                }
            }else{
                System.out.println("ALUNO NÃO ENCONTRADO");
                isTrue = false;
            }
        }
        return alunos;
    }

    private void matricularAlunos(Scanner sc){
        System.out.println("Digite o id da disciplina para matricular alunos:");
        Long id = sc.nextLong();

        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
        if(disciplina.isPresent()){
            Set<Aluno> alunos = this.matricular(sc);
            disciplina.get().getAlunos().addAll(alunos);
            disciplinaRepository.save(disciplina.get());

        }else{
            System.out.println("Disciplina não encontrada");
        }
    }
}

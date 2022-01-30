package br.com.recode.regesc.service;

import br.com.recode.regesc.orm.Professor;
import br.com.recode.regesc.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional
    public void menu(Scanner scanner){
        Boolean isTrue = true;
        while(isTrue){
            System.out.println("O que deseja fazer?");
            System.out.println("0 - SAIR");
            System.out.println("1 - Adicionar Professor no BD");
            System.out.println("2 - Atualizar Professor no BD");
            System.out.println("3 - Deletar Professor no BD");
            System.out.println("4 - Consultar Professores no BD");
            System.out.println("5 - Consultar professor por id");
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
                        this.getProfessorById(scanner);
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
        System.out.println("Digite o nome do professor");
        sc.nextLine();
        String nome = sc.nextLine();
        System.out.println("Digite o prontuario do professor");
        String prontuario = sc.next();

        Professor professor = new Professor(nome, prontuario);
        professorRepository.save(professor);
        System.out.println("Professor salvo com sucesso");
    }

    private void update(Scanner sc){
        System.out.println("Digite o id do professor a ser atualizado");
        Long id = sc.nextLong();
        Optional<Professor> professor = professorRepository.findById(id);
        if(professor.isPresent()){
            System.out.println("Digite o nome do professor");
            sc.nextLine();
            String nome = sc.nextLine();
            System.out.println("Digite o prontuario do professor");
            String prontuario = sc.next();

            professor.get().setNome(nome);
            professor.get().setProntuario(prontuario);
            professorRepository.save(professor.get());
            System.out.println("Professor atualizado com sucesso");
        }else{
            System.out.println("Professor inexistente");
        }
    }

    private void selectAll(){
        Iterable<Professor> professors = professorRepository.findAll();
        professors.forEach(professor -> {
            System.out.println("Professor: " + professor.getId());
            System.out.println("Nome: " + professor.getNome());
            System.out.println("Prontuario: " + professor.getProntuario());
            System.out.println();
        });
    }

    private void delete(Scanner sc){
        System.out.println("Digite o id do professor a ser deletado");
        Long id = sc.nextLong();
        Optional<Professor> professor = professorRepository.findById(id);
        if(professor.isPresent()){
            professorRepository.delete(professor.get());
            System.out.println("Deletado com sucesso");
        }else{
            System.out.println("Professor inexistente");
        }
    }

    @Transactional
    private void getProfessorById(Scanner sc){
        System.out.println("Digite o id do professor");
        Long id = sc.nextLong();
        Optional<Professor> professor = professorRepository.findById(id);
        if(professor.isPresent()){
            System.out.println("Professor: " + professor.get().getId());
            System.out.println("Nome: " + professor.get().getNome());
            System.out.println("Prontuario: " + professor.get().getProntuario());
            System.out.println();
            System.out.println("Disciplina:");
            professor.get().getDisciplinas().forEach(disciplina -> {
                System.out.println("Nome: " + disciplina.getNome());
                System.out.println("Semestre: " + disciplina.getSemestre());
            });
            System.out.println();
        }else{
            System.out.println("Professor inexistente");
        }
    }

}

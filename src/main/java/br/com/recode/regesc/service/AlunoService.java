package br.com.recode.regesc.service;

import br.com.recode.regesc.orm.Aluno;
import br.com.recode.regesc.orm.Disciplina;
import br.com.recode.regesc.repositories.AlunoRepository;
import br.com.recode.regesc.repositories.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Transactional
    public void menu(Scanner scanner){
        Boolean isTrue = true;
        while(isTrue){
            System.out.println("O que deseja fazer?");
            System.out.println("0 - SAIR");
            System.out.println("1 - Adicionar Aluno no BD");
            System.out.println("2 - Atualizar Aluno no BD");
            System.out.println("3 - Deletar Aluno no BD");
            System.out.println("4 - Consultar Alunos no BD");
            System.out.println("5 - Consultar Aluno por id");
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
                        this.getAlunoById(scanner);
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
        System.out.println("Digite o nome do aluno");
        sc.nextLine();
        String nome = sc.nextLine();
        System.out.println("Digite a idade do aluno");
        Integer idade = sc.nextInt();

        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setIdade(idade);
        alunoRepository.save(aluno);
        System.out.println("Aluno adicionado");
    }

    private void delete(Scanner sc){
        System.out.println("Digite o id do aluno para deletar");
        Long id = sc.nextLong();
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if (aluno.isPresent()){
            alunoRepository.delete(aluno.get());
            System.out.println("Deletado com sucesso");
        }else{
            System.out.println("Alunos não encontrado");
        }
    }

    private void update(Scanner sc){
        System.out.println("Digite o id do aluno: ");
        Long id = sc.nextLong();
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if (aluno.isPresent()){
            System.out.println("Digite o nome do aluno");
            sc.nextLine();
            String nome = sc.nextLine();
            System.out.println("Digite a idade do aluno");
            Integer idade = sc.nextInt();

            aluno.get().setNome(nome);
            aluno.get().setIdade(idade);
            alunoRepository.save(aluno.get());
            System.out.println("Atualizado com sucesso");

        }else{
            System.out.println("Aluno não encontrado");
        }

    }

    private void selectAll(){
        Iterable<Aluno> alunos = alunoRepository.findAll();
        alunos.forEach(aluno -> {
            System.out.println();
            System.out.println("Aluno: " + aluno.getId());
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("Idade: " + aluno.getIdade());
            System.out.println();
        });
    }

    @Transactional
    private void getAlunoById(Scanner sc){
        System.out.println("Digite o id do aluno:");
        Long id = sc.nextLong();
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if (aluno.isPresent()){
            System.out.println();
            System.out.println("Aluno: " + aluno.get().getId());
            System.out.println("Nome: " + aluno.get().getNome());
            System.out.println("Idade: " + aluno.get().getIdade());
            if(aluno.get().getDisciplinas() != null) {
                System.out.println("Disciplinas");
                aluno.get().getDisciplinas().forEach(disciplina -> {
                    System.out.println("Nome disciplina: " + disciplina.getNome());
                    System.out.println("Semestre disciplina: " + disciplina.getSemestre());
                });
            }
            else{
                System.out.println("Sem disciplinas para esse aluno");
            }
        }else {
            System.out.println("Aluno não encontrado");
        }
    }
}

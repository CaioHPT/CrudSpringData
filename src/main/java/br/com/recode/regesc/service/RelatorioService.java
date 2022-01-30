package br.com.recode.regesc.service;

import br.com.recode.regesc.orm.Aluno;
import br.com.recode.regesc.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioService {

    @Autowired
    private AlunoRepository alunoRepository;

    public void menu(Scanner scanner){
        Boolean isTrue = true;
        while(isTrue){
            System.out.println("Qual relatório você deseja??");
            System.out.println("0 - SAIR");
            System.out.println("1 - Alunos por nome");
            System.out.println("2 - Alunos por um dado nome e idade menor ou igual");
            System.out.println("3 - Alunos por um dado nome e idade maior ou igual");
            System.out.println("4 - Alunos por um dado nome e idade maior ou igual e matriculados");
            try{
                int opcao = scanner.nextInt();
                switch (opcao){
                    case 0:
                        isTrue = false;
                        break;
                    case 1:
                        this.getAlunoByName(scanner);
                        break;
                    case 2:
                        this.getAlunoByNameAndIdade(scanner);
                        break;
                    case 3:
                        this.getAlunoByNameAndIdadeOlderThan(scanner);
                        break;
                    case 4:
                        this.getAlunoByNameAndIdadeOlderThanRegistered(scanner);
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

    private void getAlunoByName(Scanner sc){
        System.out.println("Digite o nome do aluno");
        sc.nextLine();
        String nome = sc.nextLine();

        List<Aluno> alunos = alunoRepository.findByNomeStartingWith(nome);

        alunos.forEach(aluno -> {
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("Idade: " + aluno.getIdade());

        });
    }
    private void getAlunoByNameAndIdade(Scanner sc){
        System.out.println("Digite o nome do aluno");
        sc.nextLine();
        String nome = sc.nextLine();
        System.out.println("Digite a idade");
        Integer idade = sc.nextInt();
        List<Aluno> alunos = alunoRepository.findByNomeStartingWithAndIdadeLessThanEqual(nome, idade);

        alunos.forEach(aluno -> {
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("Idade: " + aluno.getIdade());

        });
    }

    private void getAlunoByNameAndIdadeOlderThan(Scanner sc){
        System.out.println("Digite o nome do aluno");
        sc.nextLine();
        String nome = sc.nextLine();
        System.out.println("Digite a idade");
        Integer idade = sc.nextInt();
        List<Aluno> alunos = alunoRepository.findNomeIdadeIgualOuMaior(nome, idade);

        alunos.forEach(System.out::println);
    }

    private void getAlunoByNameAndIdadeOlderThanRegistered(Scanner sc){
        System.out.println("Digite o nome do aluno");
        sc.nextLine();
        String nome = sc.nextLine();
        System.out.println("Digite a idade");
        Integer idade = sc.nextInt();
        System.out.println("Digite o nome da disciplina");
        sc.nextLine();
        String disciplina = sc.nextLine();
        List<Aluno> alunos = alunoRepository.findNomeIdadeIgualOuMaiorMatriculado(nome, idade, disciplina);

        alunos.forEach(aluno -> {
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("Idade: " + aluno.getIdade());

        });
    }

}

package br.com.recode.regesc;

import br.com.recode.regesc.orm.Professor;
import br.com.recode.regesc.repositories.ProfessorRepository;
import br.com.recode.regesc.service.AlunoService;
import br.com.recode.regesc.service.DisciplinaService;
import br.com.recode.regesc.service.ProfessorService;
import br.com.recode.regesc.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class RegescApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RegescApplication.class, args);
	}

	@Autowired
	private ProfessorService professorService;

	@Autowired
	private DisciplinaService disciplinaService;

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private RelatorioService relatorioService;

	@Override
	public void run(String... args) throws Exception {

		Scanner sc = new Scanner(System.in);
		Boolean isTrue = true;
		while(isTrue){
			System.out.println("Qual entidade deseja interagir?");
			System.out.println("0 - SAIR");
			System.out.println("1 - Professor");
			System.out.println("2 - Disciplina");
			System.out.println("3 - Aluno");
			System.out.println("4 - Relatorio");
			try{
				int opcao = sc.nextInt();
				switch (opcao){
					case 0:
						isTrue = false;
						break;
					case 1:
						professorService.menu(sc);
						break;
					case 2:
						disciplinaService.menu(sc);
						break;
					case 3:
						alunoService.menu(sc);
						break;
					case 4:
						relatorioService.menu(sc);
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
}

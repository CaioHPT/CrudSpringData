package br.com.recode.regesc.orm;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "professores")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String prontuario;


    //@OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
    //@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<Disciplina> disciplinas;

    public Professor(){

    }

    public Professor(String nome, String prontuario) {
        this.nome = nome;
        this.prontuario = prontuario;
    }

    public Long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }


    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", prontuario='" + prontuario + '\'' +
                '}';
    }
}

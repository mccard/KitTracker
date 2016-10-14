package edu.uco.mcamposcardoso.kittracker.types;

/**
 * Created by matheuscamposcardoso on 10/6/16.
 */
public class Aluno {

    String nomeAluno;
    String matriculaAluno;
    String curso;
    String periodo;
    String telefone;

    public Aluno(String nomeAluno, String matriculaAluno, String curso, String periodo, String telefone) {
        this.nomeAluno = nomeAluno;
        this.matriculaAluno = matriculaAluno;
        this.curso = curso;
        this.periodo = periodo;
        this.telefone = telefone;
    }

    public String getNomeAluno() {

        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(String matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}

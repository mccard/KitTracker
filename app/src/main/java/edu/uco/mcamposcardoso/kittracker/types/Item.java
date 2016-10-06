package edu.uco.mcamposcardoso.kittracker.types;

/**
 * Created by matheuscamposcardoso on 10/3/16.
 */
public class Item {

    private String nomeItem;
    private String numeroItem;
    private String matriculaAluno;

    public Item(String nomeItem, String numeroItem, String matriculaAluno) {
        this.nomeItem = nomeItem;
        this.numeroItem = numeroItem;
        this.matriculaAluno = matriculaAluno;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public String getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(String numeroItem) {
        this.numeroItem = numeroItem;
    }

    public String getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(String matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

}

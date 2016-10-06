package edu.uco.mcamposcardoso.kittracker.types;

import java.util.Date;

/**
 * Created by matheuscamposcardoso on 9/20/16.
 */
public class Log {

    private String nome;
    private String telefone;
    private String curso;
    private String periodo;
    private String item;
    private Date entrada;
    private Date saida;

    public Log(String nome, String telefone, String curso, String periodo, String item){
        this. nome = nome;
        this. telefone = telefone;
        this.curso = curso;
        this.periodo = periodo;
        this.item = item;
   //     this.entrada = entrada;
   //     this.saida = saida;
    }

    public Date getSaida() {
        return saida;
    }

    public void setSaida(Date saida) {
        this.saida = saida;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Date getEntrada() {
        return entrada;
    }

    public void setEntrada(Date entrada) {
        this.entrada = entrada;
    }
}

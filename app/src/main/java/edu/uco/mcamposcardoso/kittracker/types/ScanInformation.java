package edu.uco.mcamposcardoso.kittracker.types;

/**
 * Created by matheuscamposcardoso on 10/08/17.
 */
public class ScanInformation {

    private String matricula;
    private String kit;

    public ScanInformation(String matricula, String kit) {
        this.matricula = matricula;
        this.kit = kit;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getKit() {
        return kit;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

}

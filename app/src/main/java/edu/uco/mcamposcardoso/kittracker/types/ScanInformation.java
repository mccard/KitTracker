package edu.uco.mcamposcardoso.kittracker.types;

/**
 * Created by matheuscamposcardoso on 10/08/17.
 */
public class ScanInformation {

    private String matricula;
    private String kit;
    private String feed_type;

    public ScanInformation(String matricula, String kit, String feed_type) {
        this.matricula = matricula;
        this.kit = kit;
        this.feed_type = feed_type;
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

    public String getFeed_type() {
        return feed_type;
    }

    public void setFeed_type(String feed_type) {
        this.feed_type = feed_type;
    }
}

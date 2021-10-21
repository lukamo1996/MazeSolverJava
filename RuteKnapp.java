import java.util.ArrayList;
import javax.swing.JButton;

public class RuteKnapp extends JButton {
    private Integer kolonne;
    private Integer rad;
    private Labyrint labyrint;
    private Rute rute;
    private String type;

    public RuteKnapp(Rute rute, Labyrint labyrint, String type) {
        this.labyrint = labyrint;
        this.rute = rute;
        this.rad = this.rute.hentRad();
        this.kolonne = this.rute.hentKolonne();
        this.type = type;
    }

    public ArrayList<ArrayList<Tuppel>> finnVei(){
        return this.labyrint.finnUtveiFra(this.kolonne, this.rad);
    }

    public Integer hentRad(){
        return this.rute.hentRad();
    }

    public String hentType(){
        return this.type;
    }

    public Integer hentKolonne(){
        return this.rute.hentKolonne();
    }

    public String toString(){
        return String.valueOf(this.rute.tilTegn());
    }

    public Rute hentRute(){
        return this.rute;        
    }
}
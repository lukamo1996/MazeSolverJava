import java.util.ArrayList;

public abstract class Rute {
    //Variabler og sånn
    protected Integer kolonne;
    protected Integer rad;
    protected Labyrint labyrint;

    //Naboer
    protected Rute naboWest;
    protected Rute naboEast;
    protected Rute naboNorth;
    protected Rute naboSouth;

    //Konstruktør
    public Rute(){
        super();
    }

    //Finnutvei
    public void finnUtvei(){
        //Vi lagrer startRuten og instantierer en ArrayList
        ArrayList<Tuppel> koordinater = new ArrayList<>();
        this.labyrint.start = this;
        
        //Vi leter etter utveier
        this.gaa(koordinater);
    }

    //Returnerer rad
    public Integer hentRad(){
        return this.rad;
    }

    //Returnerer kolonne
    public Integer hentKolonne(){
        return this.kolonne;
    }

    //Abstrakte metoder
    abstract public void gaa(ArrayList<Tuppel> koordinater);
    abstract public char tilTegn();
}
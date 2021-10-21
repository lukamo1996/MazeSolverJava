public class Tuppel {
    //Koordinater
    private Integer x;
    private Integer y;

    //Konstruktør
    public Tuppel(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    //toString-metode
    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }

    //Hent rad(y)
    public Integer hentRad(){
        return this.y;
    }

    //Hent rad(y)
    public Integer hentKolonne(){
        return this.x;
    }
}
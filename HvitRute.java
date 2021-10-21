import java.util.ArrayList;

public class HvitRute extends Rute {
    //Rutens symbol
    private char symbol = '.';
    
    //Til-tegn metode
    public char tilTegn(){
        return symbol;
    }
    
    //toString-metoden
    @Override
    public String toString(){
        return String.valueOf(this.symbol);
    }

    //Gå-metode
    @Override
    public void gaa(ArrayList<Tuppel> koordinater){

        this.labyrint.besoektePlasser[this.rad][this.kolonne] = true;

        //Logikk
        if(this.naboNorth != null && this.labyrint.besoektePlasser[naboNorth.rad][naboNorth.kolonne] == false){
            ArrayList<Tuppel> nyeKoordinater = new ArrayList<Tuppel>(koordinater);
            Tuppel tuppel = new Tuppel(this.kolonne, this.rad);
            nyeKoordinater.add(tuppel);
            naboNorth.gaa(nyeKoordinater);
        }
        if(this.naboSouth != null && this.labyrint.besoektePlasser[naboSouth.rad][naboSouth.kolonne] == false){
            ArrayList<Tuppel> nyeKoordinater = new ArrayList<Tuppel>(koordinater);
            Tuppel tuppel = new Tuppel(this.kolonne, this.rad);
            nyeKoordinater.add(tuppel);
            naboSouth.gaa(nyeKoordinater);
        }
        if(this.naboWest != null && this.labyrint.besoektePlasser[naboWest.rad][naboWest.kolonne] == false){
            ArrayList<Tuppel> nyeKoordinater = new ArrayList<Tuppel>(koordinater);
            Tuppel tuppel = new Tuppel(this.kolonne, this.rad);
            nyeKoordinater.add(tuppel);
            naboWest.gaa(nyeKoordinater);
        }
        if(this.naboEast != null && this.labyrint.besoektePlasser[naboEast.rad][naboEast.kolonne] == false){
            ArrayList<Tuppel> nyeKoordinater = new ArrayList<Tuppel>(koordinater);
            Tuppel tuppel = new Tuppel(this.kolonne, this.rad);
            nyeKoordinater.add(tuppel);
            naboEast.gaa(nyeKoordinater);
        }

        this.labyrint.besoektePlasser[this.rad][this.kolonne] = false;

    }
}

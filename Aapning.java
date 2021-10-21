import java.util.ArrayList;

public class Aapning extends HvitRute {
    //Konstruktør
    public Aapning(){
        super();
    }

    //Gå-metoden
    @Override
    public void gaa(ArrayList<Tuppel> koordinater){
        //Hvis vi begynte i en åpning/hvitrute så sjekker vi om åpningen vi er i er den vi begynte i
        if(this.labyrint.start != this){
            ArrayList<Tuppel> nyeKoordinater = new ArrayList<Tuppel>(koordinater);
            Tuppel tuppel = new Tuppel(this.kolonne, this.rad);
            this.labyrint.besoektePlasser[this.rad][this.kolonne] = true;
            nyeKoordinater.add(tuppel);
            this.labyrint.utveier.add(nyeKoordinater);
        }

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

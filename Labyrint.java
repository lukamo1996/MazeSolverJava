import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Labyrint {
    //Beholdere og variabler
    public Rute start;
    public Integer antallKolonner;
    public Integer antallRader;
    public ArrayList<ArrayList<Tuppel>> utveier = new ArrayList<>();
    public boolean[][] besoektePlasser;
    private Rute[][] rutenett;

    //Legg inn fra fil
    public Labyrint(File fil) throws FileNotFoundException{
        lagLabyrint(fil);
    }

    //Legg inn fra filnavn
    public Labyrint(String filnavn) throws FileNotFoundException{
        File fil = new File(filnavn);
        lagLabyrint(fil);
    }
    
    //Lag labyrinten fra fil/filnavn
    public void lagLabyrint(File fil) throws FileNotFoundException{
        Scanner scanner = new Scanner(fil);
        String[] foresteLinje = scanner.nextLine().split(" ");
        Integer radTeller = 0;
        Integer kolonneTeller = 0;

        this.antallKolonner = Integer.parseInt(foresteLinje[1]);
        this.antallRader = Integer.parseInt(foresteLinje[0]);
        this.besoektePlasser = new boolean[antallRader][antallKolonner];
        this.rutenett = new Rute[antallRader][antallKolonner];

        //Legger inn ruter fra fil
        while(scanner.hasNextLine()){
            String[] linje = scanner.nextLine().split("");
            for(String element : linje){
                if(linje[kolonneTeller].equals(".")) {
                    if(radTeller == 0 || radTeller == antallRader - 1 || kolonneTeller == 0 || kolonneTeller == antallKolonner - 1){
                        this.rutenett[radTeller][kolonneTeller] = new Aapning();
                    }
                    else{
                        this.rutenett[radTeller][kolonneTeller] = new HvitRute();
                    }
                }
                else {
                    this.rutenett[radTeller][kolonneTeller] = new SortRute();
                }
                kolonneTeller++;
            }
            radTeller++;
            kolonneTeller = 0;
        }
        scanner.close();

        //Finner naboer
        for(Integer y = 0; y < this.rutenett.length; y++){
            for(Integer x = 0; x < this.rutenett[y].length; x++){
                //Nåværende rute
                Rute rute = this.rutenett[y][x];

                //Setter rad, kolonne og labyrintreferanse
                rute.rad = y;
                rute.kolonne = x;
                rute.labyrint = this;

                //Naboer
                if((x - 1) >= 0) rute.naboWest = this.rutenett[y][x - 1];
                if((x + 1) < antallKolonner) rute.naboEast = this.rutenett[y][x + 1];
                if((y + 1) < antallRader) rute.naboSouth = this.rutenett[y + 1][x];
                if((y - 1) >= 0) rute.naboNorth = this.rutenett[y - 1][x];
            }
        }
    }

    //ToString
    @Override
    public String toString(){
        String resultat = "";
        for(Integer y = 0; y < this.rutenett.length; y++){
            for(Integer x = 0; x < this.rutenett[y].length; x++){
                resultat = resultat + this.rutenett[y][x];
            }
            resultat = resultat + "\n";
        }
        return resultat;
    }

    //Finn Utvei Fra
    public ArrayList<ArrayList<Tuppel>> finnUtveiFra(int kol, int rad){
        //Sjekker at input er akseptabel
        if(kol < 0 || kol > antallKolonner || rad < 0 || rad > antallRader) {
            System.out.println("De koordinatene der finnes ikke.");
            return null;
        }

        //Nullstiller labyrinten, fjerner alt i this.utveier og besoekteplasser før vi kjører programmet på nytt for å unngå opphoping av tidligere resultater i instansvariabelen
        this.utveier.clear();
        this.besoektePlasser = new boolean[antallRader][antallKolonner];

        //Lagrer StartRute
        this.start = this.rutenett[rad][kol];
        
        //Leter etter vei
        this.hentRute(kol, rad).finnUtvei();

        //Returner veier du fant
        System.out.println("Vi fant: " + this.utveier.size() + " utveier.");
        return this.utveier;
    }

    //Hent rute
    public Rute hentRute(int kol, int rad){
        //Sjekker at input er akseptabel
        if(kol < 0 || kol > antallKolonner || rad < 0 || rad > antallRader) {
            System.out.println("De koordinatene der finnes ikke.");
            return null;
        }   
        return this.rutenett[rad][kol];
    }

    //Se labyrinten som Array
    public String somArray(){
        return Arrays.deepToString(this.rutenett);
    }
    
    //Print ut ønsket rute med koordinater
    public String printUtRute(Integer x, Integer y){
        return String.valueOf(this.rutenett[y][x]);
    }

    //Print ut info om ønsket rute
    public void printUtInfoOmRute(Integer x, Integer y){
        System.out.println("Info:\n " + " Type: " + this.rutenett[y][x].getClass().getName() + "\n  Symbol: " + this.rutenett[y][x].tilTegn() + "\n  Rad: " + x + "\n  Kolonne: " + y + "\n  NaboWest: " + this.rutenett[y][x].naboWest + "\n  NaboEast: " + this.rutenett[y][x].naboEast + "\n  NaboNord: " + this.rutenett[y][x].naboNorth + "\n  NaboSouth: " + this.rutenett[y][x].naboSouth);
    }

    //Returner antall rader
    public Integer hentRader(){
        return this.antallRader;
    }

    //Returner antall kolonner
    public Integer hentKolonner(){
        return this.antallKolonner;
    }

    //Returnerer labyrintRutenett
    public Rute[][] hentRutenett(){
        return this.rutenett;
    }
}

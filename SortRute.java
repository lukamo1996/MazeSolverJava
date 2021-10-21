import java.util.ArrayList;

public class SortRute extends Rute {
    //Rutens symbol
    private char symbol = '#';
    
    //Til-tegn-metoden
    public char tilTegn(){
        return symbol;
    }
    
    //toString metoden
    @Override
    public String toString(){
        return String.valueOf(this.symbol);
    }

    //Sort-Rute gåmetode
    @Override
    public void gaa(ArrayList<Tuppel> koordinater){
        return;
    }
}
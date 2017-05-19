package dzikizachod;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author squar
 */
public abstract class Gracz {
    private static final int MIN_LICZBA_ŻYĆ = 3;
    
    private int maksymalnePunktyŻycia;
    private int aktualnePunktyŻycia;
    private List<Akcja> trzymaneKarty;
    private int zasięg;
    private int różnicaZabitychPomocnikówIBandytów;
    private boolean czyStrzelałDoSzeryfa;
    private Strategia strategia;
    
    public Gracz() {
        this.trzymaneKarty = new ArrayList<>();
        this.zasięg = 1;
        this.różnicaZabitychPomocnikówIBandytów = 0;
        this.czyStrzelałDoSzeryfa = false;
    }
    
    public Gracz(int maksymalnePunktyŻycia, Strategia strategia) {
        this();
        this.maksymalnePunktyŻycia = maksymalnePunktyŻycia;
        this.aktualnePunktyŻycia = maksymalnePunktyŻycia;
        this.strategia = strategia;
    }
    
    public Gracz(Strategia strategia) {
        this((new Random()).nextInt(2) + MIN_LICZBA_ŻYĆ, strategia);
    }
    
    @Override
    public abstract String toString();
    
    public abstract void dodajSięDoListy(List<Gracz> listaGraczy);
    
    public int ileMaszKart() {
        return trzymaneKarty.size();
    }
    
    public void dodajKartę(Akcja akcja) {
        trzymaneKarty.add(akcja);
    }
        
    public String akcjeToString() {
        String akcjeString;
        
        akcjeString = "    Akcje: [";
        for (Akcja akcja : trzymaneKarty) {
            akcjeString += akcja + ", ";
        }
        akcjeString = akcjeString.substring(0, akcjeString.length() - 3);
        akcjeString += "]";
        
        return akcjeString;
    }
    
    public int wykonajRuch(Akcja akcja) {
        
        
        return -2;
    }

    public int getMaksymalnePunktyŻycia() {
        return this.maksymalnePunktyŻycia;
    }

    public int getAktualnePunktyŻycia() {
        return this.aktualnePunktyŻycia;
    }

    public void setAktualnePunktyŻycia(int aktualnePunktyŻycia) {
        this.aktualnePunktyŻycia = aktualnePunktyŻycia;
    }

    public int getZasięg() {
        return this.zasięg;
    }

    public void setZasięg(int zasięg) {
        this.zasięg = zasięg;
    }

    public int getRóżnicaZabitychPomocnikówIBandytów() {
        return this.różnicaZabitychPomocnikówIBandytów;
    }

    public void setRóżnicaZabitychPomocnikówIBandytów(int różnicaZabitychPomocnikówIBandytów) {
        this.różnicaZabitychPomocnikówIBandytów = różnicaZabitychPomocnikówIBandytów;
    }

    public boolean getCzyStrzelałDoSzeryfa() {
        return this.czyStrzelałDoSzeryfa;
    }
    
    
    
}

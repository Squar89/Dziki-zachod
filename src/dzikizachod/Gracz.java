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
    private List<WidokGracza> widokGraczy;
    private int indeks;
    private boolean czyTejTuryZabiłBandytę;
    
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
    
    /* gracz przekazuje tu swoją tożsamość
     * metoda jest publiczna, ale do Gracza ma dostęp jedynie Gra, więc jest to zabezpieczone */
    @Override
    public abstract String toString();
    
    public int ileMaszKart() {
        return this.trzymaneKarty.size();
    }
    
    public void dodajKartę(Akcja akcja) {
        this.trzymaneKarty.add(akcja);
    }
    
    public Akcja oddajKartę() {
        return this.trzymaneKarty.remove(0);
    }
        
    public String akcjeToString() {
        String akcjeString;
        
        akcjeString = "    Akcje: [";
        for (Akcja akcja : this.trzymaneKarty) {
            akcjeString += akcja + ", ";
        }
        /* usuwa ", " po ostatniej akcji */
        akcjeString = akcjeString.substring(0, akcjeString.length() - 2);
        akcjeString += "]";
        
        return akcjeString;
    }
    
    public int wykonajRuch(Akcja akcja) {
        int cel, liczbaKartDanejAkcji = 0;
        
        for (Akcja karta : this.trzymaneKarty) {
            if (karta == akcja) {
                liczbaKartDanejAkcji++;
            }
        }
        
        if (liczbaKartDanejAkcji == 0) {
            cel = -1;
        }
        else {
            cel = this.strategia.wybierzCel(this, akcja, liczbaKartDanejAkcji);
            
            if (cel == 0 && akcja == Akcja.STRZEL) {
                this.czyStrzelałDoSzeryfa = true;
            }
        }
        
        if (cel != -1) {
            this.trzymaneKarty.remove(akcja);
        }
        
        return cel;
    }
    
    public void ustawListęWidokuGraczy(List<WidokGracza> widokGraczy, int indeks) {
        this.widokGraczy = widokGraczy;
        this.indeks = indeks;
    }
    
    public void resetujGracza() {
        this.zasięg = 1;
        this.różnicaZabitychPomocnikówIBandytów = 0;
        this.czyStrzelałDoSzeryfa = false;
    }
    
    public boolean czyJestBandytą(Gracz gracz) {
        return false;
    }
    
    public boolean czyJestSzeryfem() {
        return false;
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

    public List<WidokGracza> getWidokGraczy() {
        return this.widokGraczy;
    }

    public int getIndeks() {
        return this.indeks;
    }

    public boolean getCzyTejTuryZabiłBandytę() {
        return this.czyTejTuryZabiłBandytę;
    }

    public void setCzyTejTuryZabiłBandytę(boolean czyTejTuryZabiłBandytę) {
        this.czyTejTuryZabiłBandytę = czyTejTuryZabiłBandytę;
    }
}

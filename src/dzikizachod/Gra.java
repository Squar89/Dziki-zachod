package dzikizachod;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;
/**
 *
 * @author squar
 */
public class Gra {
    private static final int LIMIT_TUR = 42;
    private static final int LICZBA_KART = 5;
    
    private List<Gracz> listaGraczy;
    private PulaAkcji pulaAkcji;
    private List<WidokGracza> widokGraczy;
    private int indeksDynamitu;
    private int liczbaBandytów;
    
    public Gra() {};
    
    public void rozgrywka(List<Gracz> gracze, PulaAkcji pulaAkcji) {
        int numerTury, rzutKostką;
        Gracz szeryf, aktualnyGracz;
        String opisTury, koniecString;
        Random generator = new Random();
        boolean koniec;
        
        this.indeksDynamitu = -1;
        
        /* jeśli lista graczy nie będzie zawierała szeryfa to zostanie on dodany */
        szeryf = new Szeryf();
        for (int indeks = 0; indeks < gracze.size(); indeks++) {
            aktualnyGracz = gracze.get(indeks);
            
            if (aktualnyGracz.czyJestSzeryfem()) {
                szeryf = aktualnyGracz;
                gracze.remove(indeks);
                break;
            }
        }
        
        this.listaGraczy = gracze;
        Collections.shuffle(this.listaGraczy);
        this.listaGraczy.add(0, szeryf);
        
        this.widokGraczy = new ArrayList<>();
        for (Gracz gracz : this.listaGraczy) {
            WidokGracza widokGracza = new WidokGracza(gracz);
            this.widokGraczy.add(widokGracza);
        }
        
        for (int indeks = 0; indeks < this.listaGraczy.size(); indeks++) {
            this.listaGraczy.get(indeks).ustawListęWidokuGraczy(this.widokGraczy, indeks);
        }
        
        policzBandytów();
        
        this.pulaAkcji = pulaAkcji;
        this.pulaAkcji.przetasujPulę();
        
        System.out.println("** START");
        wypiszStatusGraczy();
        koniecString = "** KONIEC\n";
        koniec = false;
        
        numerTury = 1;
        while (numerTury <= LIMIT_TUR) {
            wypiszTurę(numerTury);
            
            for (int indeks = 0; indeks < this.listaGraczy.size(); indeks++) {
                aktualnyGracz = this.listaGraczy.get(indeks);
                opisTury = "  GRACZ " + (indeks + 1) + " (" + aktualnyGracz.toString() + "):\n";
                
                if (aktualnyGracz.getAktualnePunktyŻycia() > 0) {
                    uzupełnijKarty(aktualnyGracz);
                    opisTury += aktualnyGracz.akcjeToString() + "\n";
                    
                    if (this.indeksDynamitu == indeks) {
                        rzutKostką = generator.nextInt(6) + 1;
                        if (rzutKostką == 1) {
                            opisTury += "    Dynamit: WYBUCHł\n";
                            this.indeksDynamitu = -1;
                            aktualnyGracz.setAktualnePunktyŻycia(
                                    Math.max(aktualnyGracz.getAktualnePunktyŻycia() - 3, 0));
                            if (aktualnyGracz.getAktualnePunktyŻycia() == 0) {
                                umiera(indeks);//indeks aktualnegoGracza w listach WidokGraczy i ListaGraczy
                            }
                        }
                        else {
                            opisTury += "    Dynamit: PRZECHODZI DALEJ\n";
                            this.indeksDynamitu++;
                            if (this.indeksDynamitu == this.listaGraczy.size()) {
                            this.indeksDynamitu = 0;
                            }
                        }
                    }
                        opisTury += "    Ruchy:";
                        if (aktualnyGracz.getAktualnePunktyŻycia() > 0) {
                            opisTury += "\n" + wykonujAkcje(aktualnyGracz);
                        }
                }
                if (aktualnyGracz.getAktualnePunktyŻycia() == 0) {
                    if (this.indeksDynamitu == indeks) {
                        this.indeksDynamitu++;
                        if (this.indeksDynamitu == this.listaGraczy.size()) {
                            this.indeksDynamitu = 0;
                        }
                    }
                    opisTury += "    MARTWY\n";
                }
                
                System.out.println(opisTury);
                wypiszStatusGraczy();
                
                if (liczbaBandytów == 0) {
                    koniecString += "  WYGRANA STRONA: szeryf i pomocnicy";
                    koniec = true;
                    break;
                }
                if (szeryf.getAktualnePunktyŻycia() == 0) {
                    koniecString += "  WYGRANA STRONA: bandyci";
                    koniec = true;
                    break;
                }
            }
            if (koniec == true) {
                break;
            }
            if (numerTury == LIMIT_TUR) {
                koniecString += "  REMIS - OSIĄGNIĘTO LIMIT TUR";
                break;
            }
            numerTury++;
        }
        System.out.println(koniecString);
    }
    
    private void uzupełnijKarty(Gracz aktualnyGracz) {
        while (aktualnyGracz.ileMaszKart() < LICZBA_KART) {
            aktualnyGracz.dodajKartę(this.pulaAkcji.podajKartę());
        }
    }
    
    private String wykonujAkcje(Gracz aktualnyGracz) {
        int celIndeks;
        Gracz cel;
        WidokGracza widokCelu;
        String wykonaneAkcje = "";
        
        for (Akcja akcja : Akcja.values()) {
            celIndeks = aktualnyGracz.wykonajRuch(akcja);
            while (celIndeks != -1) {
                cel = this.listaGraczy.get(celIndeks);
                switch (akcja) {
                    case ULECZ: {
                        cel.setAktualnePunktyŻycia(cel.getAktualnePunktyŻycia() + 1);

                        this.pulaAkcji.dodajDoUżytych(Akcja.ULECZ);
                        break;
                    }
                    case ZASIEG_PLUS_JEDEN: {
                        cel.setZasięg(cel.getZasięg() + 1);

                        this.pulaAkcji.dodajDoUżytych(Akcja.ZASIEG_PLUS_JEDEN);
                        break;
                    }
                    case ZASIEG_PLUS_DWA: {
                        cel.setZasięg(cel.getZasięg() + 2);

                        this.pulaAkcji.dodajDoUżytych(Akcja.ZASIEG_PLUS_DWA);
                        break;
                    }
                    case STRZEL: {
                        cel.setAktualnePunktyŻycia(cel.getAktualnePunktyŻycia() - 1);

                        if (cel.getAktualnePunktyŻycia() == 0) {
                            umiera(celIndeks);
                            widokCelu = this.widokGraczy.get(celIndeks);
                            
                            if (widokCelu.zobaczTożsamośćGracza().equals("PomocnikSzeryfa")) {
                                aktualnyGracz.setRóżnicaZabitychPomocnikówIBandytów(
                                        aktualnyGracz.getRóżnicaZabitychPomocnikówIBandytów() + 1);
                            }
                            else {
                                aktualnyGracz.setCzyTejTuryZabiłBandytę(true);
                                aktualnyGracz.setRóżnicaZabitychPomocnikówIBandytów(
                                        aktualnyGracz.getRóżnicaZabitychPomocnikówIBandytów() - 1);
                            }
                        }

                        this.pulaAkcji.dodajDoUżytych(Akcja.STRZEL);
                        break;
                    }
                    case DYNAMIT: {
                        this.indeksDynamitu = celIndeks + 1;
                        if (this.indeksDynamitu == this.listaGraczy.size()) {
                            this.indeksDynamitu = 0;
                        }
                        
                        break;
                    }
                }
                wykonaneAkcje += "      " + akcja + " ";
                if (akcja == Akcja.STRZEL || akcja == Akcja.ULECZ) {
                    wykonaneAkcje += (celIndeks + 1);
                }
                wykonaneAkcje += "\n";
                celIndeks = aktualnyGracz.wykonajRuch(akcja);
            }
        }
        aktualnyGracz.setCzyTejTuryZabiłBandytę(false);
        
        return wykonaneAkcje;
    }
    
    private void umiera(int indeksGracza) {
        Gracz gracz;
        WidokGracza widokGracza;
        
        gracz = this.listaGraczy.get(indeksGracza);
        widokGracza = this.widokGraczy.get(indeksGracza);
        
        while (gracz.ileMaszKart() != 0) {
            this.pulaAkcji.dodaj(gracz.oddajKartę(), 1);
        }
        
        widokGracza.setTożsamośćGracza(gracz.toString());
        
        if (gracz.toString().equals("Bandyta")) {
            this.liczbaBandytów--;
        }
    }
    
    private void wypiszStatusGraczy() {
        Gracz aktualnyGracz;
        String graczString;
        System.out.println("  Gracze:");
        
        for (int indeks = 0; indeks < this.listaGraczy.size(); indeks++) {
            aktualnyGracz = this.listaGraczy.get(indeks);
            System.out.print("    " + (indeks + 1) + ": ");
            
            graczString = aktualnyGracz.toString();
            graczString += " (liczba żyć: " + aktualnyGracz.getAktualnePunktyŻycia() + ")";
            System.out.println(graczString);
        }
        
        System.out.println("");
    }
    
    private void wypiszTurę(int numerTury) {
        System.out.println("** TURA " + numerTury);
    }
    
    public void policzBandytów() {
        Gracz atrapaBandyty;
        
        this.liczbaBandytów = 0;
        atrapaBandyty = new Bandyta();
        
        for (Gracz gracz : this.listaGraczy) {
            if (gracz.czyJestBandytą(atrapaBandyty)) {
                this.liczbaBandytów++;
            }
        }
    }
        
    public List<WidokGracza> getWidokGraczy() {
        return this.widokGraczy;
    }
    
    public int getIndeksGracza(Gracz gracz) {
        return this.listaGraczy.indexOf(gracz);
    }
    
    public static void main(String[] args) {
        List<Gracz> gracze = new ArrayList<Gracz>();
        gracze.add(new Szeryf());
        for (int i=0;i<9;i++) gracze.add(new PomocnikSzeryfa());
        for (int i=0;i<10;i++) gracze.add(new Bandyta());
        
        PulaAkcji pulaAkcji = new PulaAkcji();
        pulaAkcji.dodaj(Akcja.ULECZ, 20);
        pulaAkcji.dodaj(Akcja.STRZEL, 60);
        pulaAkcji.dodaj(Akcja.ZASIEG_PLUS_JEDEN, 3);
        pulaAkcji.dodaj(Akcja.ZASIEG_PLUS_DWA, 1);
        pulaAkcji.dodaj(Akcja.DYNAMIT, 1);

        Gra gra = new Gra();
        gra.rozgrywka(gracze, pulaAkcji);
    }
    
}

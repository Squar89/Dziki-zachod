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
    private List<WidokGracza> widokBandytów;
    private static List<Gracz> listaBandytów;
    private int indeksDynamitu;
    private int liczbaBandytów;
    
    public Gra() {};
    
    public void rozgrywka(List<Gracz> gracze, PulaAkcji pulaAkcji) {
        int numerTury, rzutKostką;
        Gracz szeryf, aktualnyGracz;
        String opisTury, koniecString;
        Random generator = new Random();
        
        this.indeksDynamitu = -1;
        
        List<Gracz> tymczasowaListaGraczy = new ArrayList<>();
        listaBandytów = new ArrayList<>();
        
        /* TODO koniecznie wytłumacz co tutaj się dzieje */
        for (Gracz gracz : gracze) {
            gracz.dodajSięDoListy(tymczasowaListaGraczy);//szeryf bedzie sie dodawal na pierwszej pozycji, reszta na koniec
        }
        
        szeryf = tymczasowaListaGraczy.remove(0);
        Collections.shuffle(tymczasowaListaGraczy);
        tymczasowaListaGraczy.add(0, szeryf);
        
        this.listaGraczy = tymczasowaListaGraczy;
        
        this.widokBandytów = new ArrayList<>();
        this.widokGraczy = new ArrayList<>();
        for (Gracz gracz : this.listaGraczy) {
            WidokGracza widokGracza = new WidokGracza(gracz);
            this.widokGraczy.add(widokGracza);
            if (listaBandytów.contains(gracz)) {
                this.widokBandytów.add(widokGracza);
            }
        }
        Bandyta.setListaWidokuBandytów(this.widokBandytów);
        this.liczbaBandytów = widokBandytów.size();
        
        for (int indeks = 0; indeks < this.listaGraczy.size(); indeks++) {
            this.listaGraczy.get(indeks).ustawListęWidokuGraczy(this.widokGraczy, indeks);
        }
        
        this.pulaAkcji = pulaAkcji;
        this.pulaAkcji.przetasujPulę();
        
        System.out.println("** START");
        wypiszStatusGraczy();
        koniecString = "** KONIEC\n";
        
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
                        }
                    }
                        opisTury += "    Ruchy:\n";
                        if (aktualnyGracz.getAktualnePunktyŻycia() > 0) {
                            opisTury += "\n" + wykonujAkcje(aktualnyGracz);
                        }
                }
                if (aktualnyGracz.getAktualnePunktyŻycia() == 0) {
                    if (this.indeksDynamitu == indeks) {
                        this.indeksDynamitu++;
                    }
                    opisTury += "    MARTWY\n";
                }
                
                System.out.println(opisTury);
                wypiszStatusGraczy();
            }
            if (liczbaBandytów == 0) {
                koniecString += "  WYGRANA STRONA: szeryf i pomocnicy";
                break;
            }
            if (szeryf.getAktualnePunktyŻycia() == 0) {
                koniecString += "  WYGRANA STRONA: bandyci";
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
                                aktualnyGracz.setRóżnicaZabitychPomocnikówIBandytów(
                                        aktualnyGracz.getRóżnicaZabitychPomocnikówIBandytów() - 1);
                            }
                        }

                        this.pulaAkcji.dodajDoUżytych(Akcja.STRZEL);
                        break;
                    }
                    case DYNAMIT: {
                        this.indeksDynamitu = celIndeks;
                        
                        break;
                    }
                }
                wykonaneAkcje += "      " + akcja + celIndeks + "\n";
                celIndeks = aktualnyGracz.wykonajRuch(akcja);
            }
        }
        return wykonaneAkcje;
    }
    
    private void umiera(int indeksGracza) {
        Gracz gracz;
        WidokGracza widokGracza;
        
        gracz = this.listaGraczy.get(indeksGracza);
        widokGracza = this.widokGraczy.get(indeksGracza);
        
        while (gracz.ileMaszKart() != 0) {
            this.pulaAkcji.dodajDoUżytych(gracz.oddajKartę());
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
    }
    
    private void wypiszTurę(int numerTury) {
        System.out.println("** TURA " + numerTury);
    }
    
    public static void dodajDoListyBandytów(Gracz bandyta) {
        listaBandytów.add(bandyta);
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
        for (int i=0;i<2;i++) gracze.add(new PomocnikSzeryfa());
        for (int i=0;i<3;i++) gracze.add(new Bandyta());
        
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

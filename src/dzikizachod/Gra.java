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
    
    private List<WidokGracza> widokGraczy;
    private List<WidokGracza> widokBandytów;
    private static List<Gracz> listaBandytów;
    private int indeksDynamitu;
    private int liczbaBandytów;
    
    public Gra() {};
    
    public void rozgrywka(List<Gracz> gracze, PulaAkcji pulaAkcji) {
        int numerTury, rzutKostką;
        Gracz szeryf, aktualnyGracz;
        String opisTury;
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
        
        gracze = tymczasowaListaGraczy;
        
        this.widokBandytów = new ArrayList<>();
        this.widokGraczy = new ArrayList<>();
        for (Gracz gracz : gracze) {
            WidokGracza widokGracza = new WidokGracza(gracz);
            this.widokGraczy.add(widokGracza);
            if (listaBandytów.contains(gracz)) {
                this.widokBandytów.add(widokGracza);
            }
        }
        Bandyta.setListaWidokuBandytów(this.widokBandytów);
        this.liczbaBandytów = widokBandytów.size();
        
        pulaAkcji.przetasujPulę();
        
        System.out.println("** START");
        wypiszStatusGraczy(gracze);
        
        numerTury = 1;
        while (numerTury <= LIMIT_TUR) {
            wypiszTurę(numerTury);
            
            for (int indeks = 0; indeks < gracze.size(); indeks++) {
                aktualnyGracz = gracze.get(indeks);
                opisTury = "  GRACZ " + (indeks + 1) + " (" + aktualnyGracz.toString() + "):\n";
                
                if (aktualnyGracz.getAktualnePunktyŻycia() > 0) {
                    uzupełnijKarty(aktualnyGracz, pulaAkcji);
                    opisTury += aktualnyGracz.akcjeToString() + "\n";
                    
                    if (this.indeksDynamitu == indeks) {
                        rzutKostką = generator.nextInt(6) + 1;
                        if (rzutKostką == 1) {
                            opisTury += "    Dynamit: WYBUCHł\n";
                            this.indeksDynamitu = -1;
                            aktualnyGracz.setAktualnePunktyŻycia(
                                    Math.max(aktualnyGracz.getAktualnePunktyŻycia() - 3, 0));
                            if (aktualnyGracz.getAktualnePunktyŻycia() == 0) {
                                umiera(aktualnyGracz, this.widokGraczy.get(indeks), pulaAkcji);
                            }
                        }
                        else {
                            opisTury += "    Dynamit: PRZECHODZI DALEJ\n";
                            this.indeksDynamitu++;
                        }
                    }
                        opisTury += "    Ruchy:\n";
                        if (aktualnyGracz.getAktualnePunktyŻycia() > 0) {
                            wykonujAkcje(gracze, aktualnyGracz, pulaAkcji);
                            opisTury += "\n";
                        }
                }
                if (aktualnyGracz.getAktualnePunktyŻycia() == 0) {
                    if (this.indeksDynamitu == indeks) {
                        this.indeksDynamitu++;
                    }
                    opisTury += "    MARTWY\n";
                }
                
                System.out.println(opisTury);
                wypiszStatusGraczy(gracze);
            }
            if (liczbaBandytów == 0) {
                /* Wypisz komunikat o wygranej bandytów */
            }
            /* if Szeryf nie żyje */
            numerTury++;
        }
        /* Wypisz komunikat o remisie */
    }
    
    private void uzupełnijKarty(Gracz aktualnyGracz, PulaAkcji pulaAkcji) {
        while (aktualnyGracz.ileMaszKart() < LICZBA_KART) {
            aktualnyGracz.dodajKartę(pulaAkcji.podajKartę());
        }
    }
    
    /* zmien to co przekazujesz na atrybuty obiektu, przekazuj opisTury */
    private void wykonujAkcje(List<Gracz> gracze, Gracz aktualnyGracz, PulaAkcji pulaAkcji) {
        int celIndeks;
        Gracz cel;
        WidokGracza widokCelu;
        
        for (Akcja akcja : Akcja.values()) {
            celIndeks = aktualnyGracz.wykonajRuch(akcja);
            while (celIndeks != -1) {
                cel = gracze.get(celIndeks);
                switch (akcja) {
                    case ULECZ: {
                        cel.setAktualnePunktyŻycia(cel.getAktualnePunktyŻycia() + 1);

                        pulaAkcji.dodajDoUżytych(Akcja.ULECZ);
                        break;
                    }
                    case ZASIEG_PLUS_JEDEN: {
                        cel.setZasięg(cel.getZasięg() + 1);

                        pulaAkcji.dodajDoUżytych(Akcja.ZASIEG_PLUS_JEDEN);
                        break;
                    }
                    case ZASIEG_PLUS_DWA: {
                        cel.setZasięg(cel.getZasięg() + 2);

                        pulaAkcji.dodajDoUżytych(Akcja.ZASIEG_PLUS_DWA);
                        break;
                    }
                    case STRZEL: {
                        cel.setAktualnePunktyŻycia(cel.getAktualnePunktyŻycia() - 1);

                        if (cel.getAktualnePunktyŻycia() == 0) {
                            widokCelu = widokGraczy.get(celIndeks);
                            umiera(cel, widokCelu, pulaAkcji);
                            if (widokCelu.zobaczTożsamośćGracza().equals("PomocnikSzeryfa")) {
                                aktualnyGracz.setRóżnicaZabitychPomocnikówIBandytów(
                                        aktualnyGracz.getRóżnicaZabitychPomocnikówIBandytów() + 1);
                            }
                            else {
                                aktualnyGracz.setRóżnicaZabitychPomocnikówIBandytów(
                                        aktualnyGracz.getRóżnicaZabitychPomocnikówIBandytów() - 1);
                            }
                        }

                        pulaAkcji.dodajDoUżytych(Akcja.STRZEL);
                        break;
                    }
                    case DYNAMIT: {
                        this.indeksDynamitu = celIndeks;

                        pulaAkcji.dodajDoUżytych(Akcja.DYNAMIT);
                        break;
                    }
                }
                celIndeks = aktualnyGracz.wykonajRuch(akcja);
            }
        }
    }
    
    private void umiera(Gracz gracz, WidokGracza widokGracza, PulaAkcji pulaAkcji) {
        while (gracz.ileMaszKart() != 0) {
            pulaAkcji.dodajDoUżytych(gracz.oddajKartę());
        }
        
        widokGracza.setTożsamośćGracza(gracz.toString());
    }
    
    public List<WidokGracza> getWidokGraczy() {
        return this.widokGraczy;
    }
    
    private void wypiszStatusGraczy(List<Gracz> gracze) {
        Gracz aktualnyGracz;
        String graczString;
        System.out.println("  Gracze:");
        
        for (int indeks = 0; indeks < gracze.size(); indeks++) {
            aktualnyGracz = gracze.get(indeks);
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

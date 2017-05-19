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
    private List<Gracz> listaŻywychGraczy;
    private List<WidokGracza> widokŻywychGraczy;
    
    public Gra() {};
    
    public void rozgrywka(List<Gracz> gracze, PulaAkcji pulaAkcji) {
        int numerTury, indeksDynamitu, rzutKostką;
        Gracz szeryf, aktualnyGracz;
        String opisTury;
        Random generator = new Random();
        
        indeksDynamitu = -1;
        
        List<Gracz> tymczasowaListaGraczy = new ArrayList<>();
        
        /* TODO koniecznie wytłumacz co tutaj się dzieje */
        for (Gracz gracz : gracze) {
            gracz.dodajSięDoListy(tymczasowaListaGraczy);//szeryf bedzie sie dodawal na pierwszej pozycji, reszta na koniec
        }
        
        szeryf = tymczasowaListaGraczy.remove(0);
        Collections.shuffle(tymczasowaListaGraczy);
        tymczasowaListaGraczy.add(0, szeryf);
        
        gracze = tymczasowaListaGraczy;
        this.listaŻywychGraczy = tymczasowaListaGraczy;
        
        for (Gracz gracz : gracze) {
            this.widokŻywychGraczy.add(new WidokGracza(gracz));
        }
        
        System.out.println("** START");
        wypiszStatusGraczy(gracze);
        
        numerTury = 1;
        while (numerTury <= LIMIT_TUR) {
            wypiszTurę(numerTury);
            
            for (int indeks = 0; indeks < gracze.size(); indeks++) {
                aktualnyGracz = gracze.get(indeks);
                opisTury = "GRACZ " + (indeks + 1);
                
                if (aktualnyGracz.getAktualnePunktyŻycia() > 0) {
                    aktualnyGracz.dobierzKarty();
                    opisTury += aktualnyGracz.turaToString();
                    
                    if (indeksDynamitu == indeks) {
                        rzutKostką = generator.nextInt(6) + 1;
                        if (rzutKostką == 0) {
                            opisTury += "Dynamit: WYBUCHł\n";
                            indeksDynamitu = -1;
                            aktualnyGracz.setAktualnePunktyŻycia(
                                    Math.min(aktualnyGracz.getAktualnePunktyŻycia() - 3, 0));
                            if (aktualnyGracz.getAktualnePunktyŻycia() == 0) {
                                umiera(aktualnyGracz);
                                opisTury += "Ruchy:\n  MARTWY\n";
                            }
                        }
                        else {
                            opisTury += "Dynamit: PRZECHODZI DALEJ\n";
                            indeksDynamitu++;
                        }
                        
                        if (aktualnyGracz.getAktualnePunktyŻycia() > 0) {
                            wykonujAkcje(aktualnyGracz, Akcja.ULECZ);
                            wykonujAkcje(aktualnyGracz, Akcja.ZASIEG_PLUS_JEDEN);
                            wykonujAkcje(aktualnyGracz, Akcja.ZASIEG_PLUS_DWA);
                            wykonujAkcje(aktualnyGracz, Akcja.STRZEL);
                            wykonujAkcje(aktualnyGracz, Akcja.DYNAMIT);
                        }
                    }
                }
                else /*(aktualnyGracz.getPunktyŻycia() == 0) */{
                    if (indeksDynamitu == indeks) {
                        indeksDynamitu++;
                    }
                    opisTury = aktualnyGracz.turaToString();
                }
                
                System.out.println(opisTury);
            }
            
            numerTury++;
        }
    }
    
    private void wykonujAkcje(Gracz aktualnyGracz, Akcja akcja) {
        int cel;
        
        cel = aktualnyGracz.wykonajRuch(akcja);
        while (cel != -1) {
            switch (akcja) {
                case ULECZ: decyzjaUlecz(cel); break;
                case ZASIEG_PLUS_JEDEN: decyzjaZwiększZasięg(cel, 1); break;
                case ZASIEG_PLUS_DWA: decyzjaZwiększZasięg(cel, 2); break;
                case STRZEL: decyzjaStrzel(cel); break;
                case DYNAMIT: decyzjaDynamit(cel); break;
                
            }
            cel = aktualnyGracz.wykonajRuch(akcja);
        }
        
    }
    
    private void decyzjaUlecz(int indeks) {
        //listaŻywychGraczy
    }
    
    private void decyzjaZwiększZasięg(int indeks, int ile) {
        //listaŻywychGraczy
    }
    
    private void decyzjaStrzel(int indeks) {
        //listaŻywychGraczy
    }
    
    private void decyzjaDynamit(int indeks) {
        //listaŻywychGraczy
    }
    
    private void umiera(Gracz gracz) {
        /* TODO */
    }
    
    public List<WidokGracza> getWidokŻywychGraczy() {
        return this.widokŻywychGraczy;
    }
    
    private void wypiszStatusGraczy(List<Gracz> gracze) {
        String graczString;
        System.out.println("  Gracze:");
        
        for (int indeks = 0; indeks < gracze.size(); indeks++) {
            System.out.print("    " + (indeks + 1) + ": ");
            
            graczString = (gracze.get(indeks)).toString();
            System.out.println(graczString);
        }
    }
    
    private void wypiszTurę(int numerTury) {
        System.out.println("** TURA " + numerTury);
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

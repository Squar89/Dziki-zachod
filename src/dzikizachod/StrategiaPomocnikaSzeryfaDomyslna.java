package dzikizachod;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author squar
 */
public class StrategiaPomocnikaSzeryfaDomyslna extends StrategiaPomocnikaSzeryfa {
    public StrategiaPomocnikaSzeryfaDomyslna() {}
    
    @Override
    public int strzel(Gracz gracz, int liczbaKart) {
        List<WidokGracza> widokGraczy;
        WidokGracza aktualnyGracz;
        List<Integer> indeksyGraczyWZasięgu;
        int indeksGracza, pozostałyZasięg, ostatniSprawdzony, cel, następnyIndeks;
        
        widokGraczy = gracz.getWidokGraczy();
        indeksyGraczyWZasięgu = new ArrayList<>();
        indeksGracza = gracz.getIndeks();
        
        /* fory mogą na siebie nachodzić w przypadku bardzo dużego zasięgu lub małej
         * ilości żywych graczy, dlatego ograniczam je ostatnim indeksem pod którym byłem */
        ostatniSprawdzony = indeksGracza;
        pozostałyZasięg = gracz.getZasięg();
        /* tym razem interesują nas wszyscy gracze dookoła */
        for (int indeks = indeksGracza - 1; indeks != indeksGracza; indeks--) {
            if (indeks == -1) {
                indeks = widokGraczy.size() - 1;
            }
            aktualnyGracz = widokGraczy.get(indeks);
            ostatniSprawdzony = indeks;
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                if (indeks != 0) {
                    indeksyGraczyWZasięgu.add(indeks);
                }
                
                pozostałyZasięg--;
                if (pozostałyZasięg == 0) {
                    break;
                }
            }
        }
        
        pozostałyZasięg = gracz.getZasięg();
        /* następnyIndeks jest konieczny, bo eliminuje problemy z minięciem ostatniegoSprawdzonego */
        następnyIndeks = indeksGracza + 1;
        if (następnyIndeks == widokGraczy.size()) {
            następnyIndeks = 0;
        }
        /* interesują nas tylko ci gracze, których jeszcze nie rozpatrzyliśmy w poprzednim
         * forze, stąd ogarniczenie przez ostatniSprawdzony */
        for (int indeks = następnyIndeks; indeks != ostatniSprawdzony; indeks++) {
            if (indeks == widokGraczy.size()) {
                indeks = 0;
            }
            aktualnyGracz = widokGraczy.get(indeks);
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                if (indeks != 0) {
                    indeksyGraczyWZasięgu.add(indeks);
                }
                
                pozostałyZasięg--;
                if (pozostałyZasięg == 0) {
                    break;
                }
            }
        }
        
        if (!indeksyGraczyWZasięgu.isEmpty()) {
            cel = indeksyGraczyWZasięgu.get(losujIndeks(indeksyGraczyWZasięgu.size()));
        }
        else {
            /* jeśli nie ma żadnego możliwego celu ataku, to gracz zwraca -1 równoważne braku akcji */
            cel = -1;
        }

        return cel;
    }
}

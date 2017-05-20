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
        
        ostatniSprawdzony = indeksGracza;
        pozostałyZasięg = gracz.getZasięg();
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
        następnyIndeks = indeksGracza + 1;
        if (następnyIndeks == widokGraczy.size()) {
            następnyIndeks = 0;
        }
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
            cel = -1;
        }

        return cel;
    }
}

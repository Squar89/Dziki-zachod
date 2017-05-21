package dzikizachod;

import java.util.List;

/**
 *
 * @author squar
 */
public class StrategiaBandytyCierpliwa extends StrategiaBandyty {
    public StrategiaBandytyCierpliwa() {}
    
    @Override
    public int strzel(Gracz gracz, int liczbaKart) {
        List<WidokGracza> widokGraczy;
        WidokGracza aktualnyGracz;
        int indeksGracza, pozostałyZasięg;
        
        widokGraczy = gracz.getWidokGraczy();
        indeksGracza = gracz.getIndeks();
        
        pozostałyZasięg = gracz.getZasięg();
        /* jeśli szeryf jest w zasięgu bandyty, to automatycznie staje się jego celem
         * dlatego interesuje nas przedział [0, indeksGracza) */
        for (int indeks = indeksGracza - 1; indeks >= 0; indeks--) {
            aktualnyGracz = widokGraczy.get(indeks);
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                if (indeks == 0) {
                    return 0;
                }
                
                pozostałyZasięg--;
                if (pozostałyZasięg == 0) {
                    break;
                }
            }
        }
        
        pozostałyZasięg = gracz.getZasięg();
        for (int indeks = indeksGracza + 1; indeks >= widokGraczy.size(); indeks++) {
            /* gracze stoją w kole, więc gracz o indeksie widokGraczy.size(), to
             * gracz o indeksie 0, czyli szeryf na którym skończymy szukanie celu */
            if (indeks == widokGraczy.size()) {
                indeks = 0;
            }
            aktualnyGracz = widokGraczy.get(indeks);
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                if (indeks == 0) {
                    return 0;
                }
                
                pozostałyZasięg--;
                if (pozostałyZasięg == 0) {
                    break;
                }
            }
        }
        
        /* jeśli szeryf nie jest w zasięgu, to zgodnie ze strategią bandyta czeka,
         * więc zwraca -1, które jest sygnałem braku akcji ze strony gracza */
        return -1;
    }
}

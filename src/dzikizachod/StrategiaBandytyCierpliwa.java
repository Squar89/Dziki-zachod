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
        
        return -1;
    }
}

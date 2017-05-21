package dzikizachod;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author squar
 */
public class StrategiaBandytyDomyslna extends StrategiaBandyty {
    public StrategiaBandytyDomyslna() {}
    
    @Override
    public int strzel(Gracz gracz, int liczbaKart) {
        List<WidokGracza> widokGraczy;
        WidokGracza aktualnyGracz;
        List<Integer> indeksyMożliweCele;
        int indeksGracza, pozostałyZasięg, cel;
        
        widokGraczy = gracz.getWidokGraczy();
        indeksyMożliweCele = new ArrayList<>();
        indeksGracza = gracz.getIndeks();
        
        pozostałyZasięg = gracz.getZasięg();
        for (int indeks = indeksGracza - 1; indeks >= 0; indeks--) {
            aktualnyGracz = widokGraczy.get(indeks);
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                if (indeks == 0) {
                    return 0;
                }
                else if (!aktualnyGracz.zobaczCzyJestBandytą(gracz)) {
                    indeksyMożliweCele.add(indeks);
                }
                
                pozostałyZasięg--;
                if (pozostałyZasięg == 0) {
                    break;
                }
            }
        }
        
        pozostałyZasięg = gracz.getZasięg();
        for (int indeks = indeksGracza + 1; indeks <= widokGraczy.size(); indeks++) {
            if (indeks == widokGraczy.size()) {
                indeks = 0;
            }
            aktualnyGracz = widokGraczy.get(indeks);
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                if (indeks == 0) {
                    return 0;
                }
                else if (!aktualnyGracz.zobaczCzyJestBandytą(gracz)) {
                    indeksyMożliweCele.add(indeks);
                }
                
                pozostałyZasięg--;
                if (pozostałyZasięg == 0) {
                    break;
                }
            }
        }
        
        if (!indeksyMożliweCele.isEmpty()) {
            cel = indeksyMożliweCele.get(losujIndeks(indeksyMożliweCele.size()));
        }
        else {
            cel = -1;
        }

        return cel;
    } 
}
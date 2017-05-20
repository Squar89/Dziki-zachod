package dzikizachod;

import java.util.List;

/**
 *
 * @author squar
 */
public abstract class StrategiaBandyty extends Strategia {
    public StrategiaBandyty() {}
    
    @Override
    public int dynamit(Gracz gracz) {
        List<WidokGracza> widokGraczy;
        WidokGracza aktualnyGracz;
        int indeksGracza, licznikŻywychGraczy;
        
        widokGraczy = gracz.getWidokGraczy();
        indeksGracza = gracz.getIndeks();
        licznikŻywychGraczy = 0;
        
        for (int indeks = indeksGracza + 1; indeks < widokGraczy.size(); indeks++) {
            aktualnyGracz = widokGraczy.get(indeks);
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                licznikŻywychGraczy++;
                if (licznikŻywychGraczy == 3) {
                    break;
                }
            }
        }
        
        if (licznikŻywychGraczy < 3) {
            return indeksGracza;
        }
        else {
            return -1;
        }
    }
}

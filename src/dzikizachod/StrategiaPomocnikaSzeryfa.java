package dzikizachod;

import java.util.List;
/**
 *
 * @author squar
 */
public abstract class StrategiaPomocnikaSzeryfa extends Strategia {
    public StrategiaPomocnikaSzeryfa() {}
    
    @Override
    public int ulecz(Gracz gracz) {
        List<WidokGracza> widokGraczy;
        WidokGracza aktualnyGracz;
        int indeksGracza, pozostałyZasięg, cel, następnyIndeks;
        
        widokGraczy = gracz.getWidokGraczy();
        indeksGracza = gracz.getIndeks();
        
        pozostałyZasięg = 1;
        cel = -1;
        
        /* interesuje nas przedział [0, indeksGracza) ponieważ szukamy szeryfa */
        for (int indeks = indeksGracza - 1; indeks >= 0; indeks--) {
            aktualnyGracz = widokGraczy.get(indeks);
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                /* Sprawdzam czy sąsiad jest szeryfem i czy potrzebuje leczenia */
                if (indeks == 0
                        && aktualnyGracz.zobaczAktualnePunktyŻycia() < aktualnyGracz.zobaczMaksymalnePunktyŻycia()) {
                    cel = 0;
                }
                
                pozostałyZasięg--;
                if (pozostałyZasięg == 0) {
                    break;
                }
            }
        }
        
        pozostałyZasięg = 1;
        for (int indeks = indeksGracza + 1; indeks <= widokGraczy.size(); indeks++) {
            if (indeks == widokGraczy.size()) {
                indeks = 0;
            }
            aktualnyGracz = widokGraczy.get(indeks);
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                /* Sprawdzam czy sąsiad jest szeryfem i czy potrzebuje leczenia */
                if (indeks == 0
                        && aktualnyGracz.zobaczAktualnePunktyŻycia() < aktualnyGracz.zobaczMaksymalnePunktyŻycia()) {
                    cel = 0;
                }
                
                pozostałyZasięg--;
                if (pozostałyZasięg == 0) {
                    break;
                }
            }
        }
        
        /* sprawdzam czy jeśli nie mogę uleczyć szeryfa, to czy mogę uleczyć siebie */
        if (cel != 0 && gracz.getAktualnePunktyŻycia() < gracz.getMaksymalnePunktyŻycia()) {
            cel = indeksGracza;
        }
        else {
            cel = -1;
        }
        
        return cel;
    }
    
    @Override
    public int dynamit(Gracz gracz) {
        List<WidokGracza> widokGraczy;
        WidokGracza aktualnyGracz;
        int indeksGracza, licznikŻywychGraczy, licznikPodejrzanych, ułamekŻywych;
        
        widokGraczy = gracz.getWidokGraczy();
        indeksGracza = gracz.getIndeks();
        licznikŻywychGraczy = licznikPodejrzanych = 0;
        
        /* musimy przejść od gracza do szeryfa zgodnie z kierunkiem ruchów graczy,
         * zatem interesuje nas przedział (indeksGracza, widokGraczy.size()] */
        for (int indeks = indeksGracza + 1; indeks < widokGraczy.size(); indeks++) {
            aktualnyGracz = widokGraczy.get(indeks);
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                licznikŻywychGraczy++;
                if (aktualnyGracz.zobaczRóżnicaZabitychPomocnikówIBandytów() > 0) {
                    licznikPodejrzanych++;
                }
            }
        }
        
        if (licznikŻywychGraczy > 0 ) {
            ułamekŻywych = (licznikŻywychGraczy / 3) * 2;
         
            if (licznikŻywychGraczy > 3 && licznikPodejrzanych >= ułamekŻywych) {
                return indeksGracza;
            }
        }
           
        /* ten kawałek kodu wykona się tylko jeśli nie została podjęta decyzja o wypuszczeniu dynamitu */ 
        return -1;
    }
}

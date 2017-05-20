package dzikizachod;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author squar
 */
public class StrategiaPomocnikaSzeryfaZliczajaca extends StrategiaPomocnikaSzeryfa {
    public StrategiaPomocnikaSzeryfaZliczajaca() {}
    
    @Override
    public int strzel(Gracz gracz, int liczbaKart) {
        List<WidokGracza> widokGraczy;
        WidokGracza aktualnyGracz;
        List<Integer> indeksyMożliwychCeli;
        int indeksGracza, pozostałyZasięg, ostatniSprawdzony, cel, następnyIndeks;
        
        widokGraczy = gracz.getWidokGraczy();
        indeksyMożliwychCeli = new ArrayList<>();
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
                if (indeks !=0 && (aktualnyGracz.zobaczCzyStrzelałDoSzeryfa()
                        || aktualnyGracz.zobaczRóżnicaZabitychPomocnikówIBandytów() > 0)) {
                    indeksyMożliwychCeli.add(indeks);
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
                if (indeks !=0 && (aktualnyGracz.zobaczCzyStrzelałDoSzeryfa()
                        || aktualnyGracz.zobaczRóżnicaZabitychPomocnikówIBandytów() > 0)) {
                    indeksyMożliwychCeli.add(indeks);
                }
                
                pozostałyZasięg--;
                if (pozostałyZasięg == 0) {
                    break;
                }
            }
        }
        
        if (!indeksyMożliwychCeli.isEmpty()) {
            cel = indeksyMożliwychCeli.get(losujIndeks(indeksyMożliwychCeli.size()));
        }
        else {
            cel = -1;
        }

        return cel;
    }
}

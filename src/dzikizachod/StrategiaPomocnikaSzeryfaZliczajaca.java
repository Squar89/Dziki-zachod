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
            /* metoda zwraca -1 tylko w przypadku gdy nie ma żadnego celu zgodnego z strategią */
            cel = -1;
        }

        return cel;
    }
}

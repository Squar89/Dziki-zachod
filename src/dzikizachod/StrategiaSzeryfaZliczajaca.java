package dzikizachod;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author squar
 */
public class StrategiaSzeryfaZliczajaca extends StrategiaSzeryfa{
    public StrategiaSzeryfaZliczajaca() {}
    
    @Override
    public int strzel(Gracz gracz, int liczbaKart) {
        List<WidokGracza> widokGraczy;
        List<Integer> indeksyMożliwychCeli;
        int indeksGracza, pozostałyZasięg, ostatniSprawdzony, cel;
        
        widokGraczy = gracz.getWidokGraczy();
        indeksyMożliwychCeli = new ArrayList<>();
        indeksGracza = 0; /* Szeryf zawsze stoi na pierwszej pozycji - czyli zerowy indeks*/
        
        /* fory mogą na siebie nachodzić w przypadku bardzo dużego zasięgu lub małej
         * ilości żywych graczy, dlatego ograniczam je ostatnim indeksem pod którym byłem */
        ostatniSprawdzony = widokGraczy.size() - 1;
        pozostałyZasięg = gracz.getZasięg();
        /* Sprawdzamy na lewo od szeryfa który zawsze stoi na pierwszej pozycji, 
         * czyli od końca listy */
        for (int indeks = widokGraczy.size() - 1; indeks > indeksGracza; indeks--) {
            WidokGracza aktualnyGracz = widokGraczy.get(indeks);
            ostatniSprawdzony = indeks;
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                if (aktualnyGracz.zobaczCzyStrzelałDoSzeryfa()
                        || aktualnyGracz.zobaczRóżnicaZabitychPomocnikówIBandytów() > 0) {
                    indeksyMożliwychCeli.add(indeks);
                }
                
                pozostałyZasięg--;
                if (pozostałyZasięg == 0) {
                    break;
                }
            }
        }
        
        pozostałyZasięg = gracz.getZasięg();
        /* interesują nas tylko ci gracze, których jeszcze nie rozpatrzyliśmy w poprzednim
         * forze, stąd ogarniczenie przez ostatniSprawdzony */
        for (int indeks = 1; indeks < ostatniSprawdzony; indeks++) {
            WidokGracza aktualnyGracz = widokGraczy.get(indeks);
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                if (aktualnyGracz.zobaczCzyStrzelałDoSzeryfa()
                        || aktualnyGracz.zobaczRóżnicaZabitychPomocnikówIBandytów() > 0) {
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
        /* jeśli żaden z graczy w zasięgu nie spełnia kryterium strategii, to
         * metoda zwraca -1 równoważne braku akcji ze strony gracza */
        else {
            cel = -1;
        }

        return cel;
    }
}

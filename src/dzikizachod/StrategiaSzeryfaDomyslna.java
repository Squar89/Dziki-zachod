package dzikizachod;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author squar
 */
public class StrategiaSzeryfaDomyslna extends StrategiaSzeryfa {
    public StrategiaSzeryfaDomyslna() {}
    
    @Override
    public int strzel(Gracz gracz, int liczbaKart) {
        List<WidokGracza> widokGraczy;
        WidokGracza aktualnyGracz;
        List<Integer> indeksyGraczyWZasięgu, indeksyStrzelaliDoSzeryfa;
        int indeksGracza, pozostałyZasięg, ostatniSprawdzony, cel;
        
        widokGraczy = gracz.getWidokGraczy();
        indeksyGraczyWZasięgu = new ArrayList<>();
        indeksyStrzelaliDoSzeryfa = new ArrayList<>();
        /* Szeryf zawsze stoi na pierwszej pozycji - czyli zerowy indeks*/
        indeksGracza = 0; 
        
        /* fory mogą na siebie nachodzić w przypadku bardzo dużego zasięgu lub małej
         * ilości żywych graczy, dlatego ograniczam je ostatnim indeksem pod którym byłem */
        ostatniSprawdzony = widokGraczy.size() - 1;
        pozostałyZasięg = gracz.getZasięg();
        /* Sprawdzamy na lewo od szeryfa który zawsze stoi na pierwszej pozycji, 
         * czyli od końca listy */
        for (int indeks = widokGraczy.size() - 1; indeks > indeksGracza; indeks--) {
            aktualnyGracz = widokGraczy.get(indeks);
            ostatniSprawdzony = indeks;
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                indeksyGraczyWZasięgu.add(indeks);
                if (aktualnyGracz.zobaczCzyStrzelałDoSzeryfa()) {
                    indeksyStrzelaliDoSzeryfa.add(indeks);
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
            aktualnyGracz = widokGraczy.get(indeks);
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                indeksyGraczyWZasięgu.add(indeks);
                if (aktualnyGracz.zobaczCzyStrzelałDoSzeryfa()) {
                    indeksyStrzelaliDoSzeryfa.add(indeks);
                }
                
                pozostałyZasięg--;
                if (pozostałyZasięg == 0) {
                    break;
                }
            }
        }
        
        /* jeśli żaden z graczy w zasięgu nie strzelał do szeryfa, to wtedy sprawdzamy
         * czy jest jakikolwiek możliwy cel, jeśli też nie, to metoda zwraca -1, co jest
         * równoważne braku akcji ze strony gracza */
        if (!indeksyStrzelaliDoSzeryfa.isEmpty()) {
            cel = indeksyStrzelaliDoSzeryfa.get(losujIndeks(indeksyStrzelaliDoSzeryfa.size()));
        }
        else if (!indeksyGraczyWZasięgu.isEmpty()) {
            cel = indeksyGraczyWZasięgu.get(losujIndeks(indeksyGraczyWZasięgu.size()));
        }
        else {
            cel = -1;
        }

        return cel;
    }
}

package dzikizachod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 *
 * @author squar
 */
public class StrategiaSzeryfaDomyslna extends StrategiaSzeryfa {
    public StrategiaSzeryfaDomyslna() {}
    
    @Override
    public int strzel(Gracz gracz, int liczbaKart) {
        List<WidokGracza> widokGraczy;
        List<Integer> indeksyGraczyWZasięgu, indeksyStrzelaliDoSzeryfa;
        int indeksGracza, pozostałyZasięg, ostatniSprawdzony, cel;
        Random generator = new Random();
        
        widokGraczy = gracz.getWidokGraczy();
        indeksyGraczyWZasięgu = new ArrayList<>();
        indeksyStrzelaliDoSzeryfa = new ArrayList<>();
        indeksGracza = 1; /* Szeryf zawsze stoi na pierwszej pozycji */
        
        ostatniSprawdzony = widokGraczy.size() - 1;
        pozostałyZasięg = gracz.getZasięg();
        /* Sprawdzamy na lewo od szeryfa który zawsze stoi na pierwszej pozycji, 
         * czyli od końca listy */
        for (int indeks = widokGraczy.size() - 1; indeks > indeksGracza; indeks--) {
            WidokGracza aktualnyGracz = widokGraczy.get(indeks);
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
        for (int indeks = 2; indeks < ostatniSprawdzony; indeks++) {
            WidokGracza aktualnyGracz = widokGraczy.get(indeks);
            
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
        
        if (!indeksyStrzelaliDoSzeryfa.isEmpty()) {
            cel = losujIndeks(indeksyStrzelaliDoSzeryfa.size());
        }
        else if (!indeksyGraczyWZasięgu.isEmpty()) {
            cel = losujIndeks(indeksyGraczyWZasięgu.size());
        }
        else {
            cel = -1;
        }
        
        return cel;
    }
}

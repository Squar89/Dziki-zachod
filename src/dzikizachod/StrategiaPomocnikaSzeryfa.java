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
        int indeksGracza, pozostałyZasięg, ostatniSprawdzony, cel, następnyIndeks;
        
        widokGraczy = gracz.getWidokGraczy();
        indeksGracza = gracz.getIndeks();
        
        ostatniSprawdzony = indeksGracza - 1;
        pozostałyZasięg = 1;
        cel = -1;
        
        for (int indeks = indeksGracza - 1; indeks >= 0; indeks--) {
            aktualnyGracz = widokGraczy.get(indeks);
            ostatniSprawdzony = indeks;
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                /* Sprawdzamy czy sąsiad jest szeryfem i czy potrzebuje leczenia */
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
        następnyIndeks = indeksGracza + 1;
        if (następnyIndeks == widokGraczy.size()) {
            następnyIndeks = 0;
        }
        for (int indeks = następnyIndeks; indeks != ostatniSprawdzony; indeks++) {
            aktualnyGracz = widokGraczy.get(indeks);
            ostatniSprawdzony = indeks;
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                /* Sprawdzamy czy sąsiad jest szeryfem i czy potrzebuje leczenia */
                if (indeks == 0
                        && aktualnyGracz.zobaczAktualnePunktyŻycia() < aktualnyGracz.zobaczMaksymalnePunktyŻycia()) {
                    cel = 0;
                }
                
                pozostałyZasięg--;
                if (pozostałyZasięg == 0) {
                    break;
                }
            }
            
            if (indeks == widokGraczy.size() - 1) {
                indeks = -1; /* więc po operacji indeks++ indeks będzie równy 0 */
            }
        }
        
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
        
        for (int indeks = indeksGracza + 1; indeks < widokGraczy.size(); indeks++) {
            aktualnyGracz = widokGraczy.get(indeks);
            
            if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                licznikŻywychGraczy++;
                if (aktualnyGracz.zobaczRóżnicaZabitychPomocnikówIBandytów() > 0) {
                    licznikPodejrzanych++;
                }
            }
        }
        
        ułamekŻywych = (licznikŻywychGraczy / 3) * 2;
        if (licznikŻywychGraczy > 3 && licznikPodejrzanych >= ułamekŻywych) {
            return indeksGracza;
        }

        return -1;
    }
}

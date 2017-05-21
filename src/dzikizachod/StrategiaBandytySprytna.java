package dzikizachod;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author squar
 */
public class StrategiaBandytySprytna extends StrategiaBandyty {
    public StrategiaBandytySprytna() {}
    
    @Override
    public int strzel(Gracz gracz, int liczbaKart) {
        List<WidokGracza> widokGraczy;
        WidokGracza aktualnyGracz;
        List<Integer> indeksyMożliweCele;
        int indeksGracza, pozostałyZasięg, cel;
        
        widokGraczy = gracz.getWidokGraczy();
        indeksyMożliweCele = new ArrayList<>();
        indeksGracza = gracz.getIndeks();
        cel = -1;
        
        /* Czyli w tej turze zabił już bandytę i dalej będzie działał ze strategią domyślną */
        if (gracz.getCzyTejTuryZabiłBandytę()) {
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
        }
        else {
            pozostałyZasięg = gracz.getZasięg();
            for (int indeks = indeksGracza - 1; indeks >= 0; indeks--) {
                aktualnyGracz = widokGraczy.get(indeks);

                if (aktualnyGracz.zobaczAktualnePunktyŻycia() > 0) {
                    if (indeks == 0) {
                        return 0;
                    }
                    /* sprawdzam czy aktualnyGracz jest bandytą i czy będę miał wystarczającą
                     * ilość strzałów żeby zabić go w tej turze */
                    else if (aktualnyGracz.zobaczCzyJestBandytą(gracz)
                            && aktualnyGracz.zobaczAktualnePunktyŻycia() <= liczbaKart) {
                        return indeks;
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
                    else if (aktualnyGracz.zobaczCzyJestBandytą(gracz)
                            && aktualnyGracz.zobaczAktualnePunktyŻycia() <= liczbaKart) {
                        return indeks;
                    }

                    pozostałyZasięg--;
                    if (pozostałyZasięg == 0) {
                        break;
                    }
                }
            }
        }
        
        return cel;
    }
}

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
        /* możliweCele, to wszyscy gracze w zasięgu bandyty nie będący bandytami */
        List<Integer> indeksyMożliweCele;
        int indeksGracza, pozostałyZasięg, cel;
        
        widokGraczy = gracz.getWidokGraczy();
        indeksyMożliweCele = new ArrayList<>();
        indeksGracza = gracz.getIndeks();
        /* ustawiam tutaj cel na -1 równoważne braku akcji, ponieważ wyjątkowo nie ma tutaj żadnego
         * losowania (metoda zwróci indeks pierwszego celu jaki znajdzie), więc cel = -1
         * zostanie zwrócony tylko w przypadku nieznalezienia żadnego możliwego celu */
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
            /* jeśli szeryf jest w zasięgu bandyty, to automatycznie staje się jego celem
             * dlatego interesuje nas przedział [0, indeksGracza) */
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

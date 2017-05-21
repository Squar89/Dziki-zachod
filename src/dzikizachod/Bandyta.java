package dzikizachod;

/**
 *
 * @author squar
 */
public class Bandyta extends Gracz {
    public Bandyta() {
        super(new StrategiaBandytyDomyslna());
    }
    
    public Bandyta(Strategia strategia) {
        super(strategia);
    }
    
    @Override
    public String toString() {
        return "Bandyta";
    }
    
    /* sprawdza czy ten kto wywołał zapytanie również jest bandytą, jeśli nie, to zwraca false
     * mimo że gracz o którego się pytał jest bandytą */
    @Override
    public boolean czyJestBandytą(Gracz gracz) {
        return gracz.toString().equals(this.toString());
    }
}

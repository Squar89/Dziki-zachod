package dzikizachod;

/**
 *
 * @author squar
 */
public class Szeryf extends Gracz {
    
    public Szeryf() {
        super(5, new StrategiaSzeryfaDomyslna());
    }
    
    public Szeryf(Strategia strategia) {
        super(5, strategia);
    }
    
    @Override
    public String toString() {
        return "Szeryf";
    }
    
    /* Każdy gracz wie kto jest szeryfem, więc ta metoda może być publiczna */
    @Override
    public boolean czyJestSzeryfem() {
        return true;
    }
}

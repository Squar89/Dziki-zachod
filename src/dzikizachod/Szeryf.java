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
}

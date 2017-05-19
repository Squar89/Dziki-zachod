package dzikizachod;

import java.util.Random;

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
}

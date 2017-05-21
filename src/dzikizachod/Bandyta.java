package dzikizachod;

import java.util.List;

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
    
    @Override
    public void dodajSięDoListy(List<Gracz> listaGraczy) {
        listaGraczy.add(this);
    }
    
    @Override
    public boolean czyJestBandytą(Gracz gracz) {
        return gracz.toString().equals(this.toString());
    }
}

package dzikizachod;

import java.util.List;
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
    
    @Override
    public void dodajSięDoListy(List<Gracz> listaGraczy) {
        listaGraczy.add(0, this);
    }
}

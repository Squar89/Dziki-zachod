package dzikizachod;

import java.util.List;

/**
 *
 * @author squar
 */
public class Bandyta extends Gracz {
    private static List<WidokGracza> listaWidokuBandytów;
    
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
        Gra.dodajDoListyBandytów(this);
    }
    
    public static void setListaWidokuBandytów(List<WidokGracza> lista) {
        listaWidokuBandytów = lista;
    }
    
    public boolean czyJestBandytą(WidokGracza widokGracza) {
        return listaWidokuBandytów.contains(widokGracza);
    }
}

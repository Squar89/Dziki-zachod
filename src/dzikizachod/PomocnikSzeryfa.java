package dzikizachod;

/**
 *
 * @author squar
 */
public class PomocnikSzeryfa extends Gracz {
    
    public PomocnikSzeryfa() {
        super(new StrategiaPomocnikaSzeryfaDomyslna());
    }
    
    public PomocnikSzeryfa(Strategia strategia) {
        super(strategia);
    }
}

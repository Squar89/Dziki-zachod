package dzikizachod;

/**
 *
 * @author squar
 */
public abstract class StrategiaSzeryfa extends Strategia {
    public StrategiaSzeryfa() {}
    
    @Override
    public int dynamit(Gracz gracz) {
        return -1;
    }
}

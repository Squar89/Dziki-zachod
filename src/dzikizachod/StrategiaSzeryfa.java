package dzikizachod;

/**
 *
 * @author squar
 */
public abstract class StrategiaSzeryfa extends Strategia {
    public StrategiaSzeryfa() {}
    
    /* Szeryf nigdy nie będzie używał dynamitu, dlatego zwraca -1, co jest
     * równoważne braku akcji ze strony gracza */
    @Override
    public int dynamit(Gracz gracz) {
        return -1;
    }
}

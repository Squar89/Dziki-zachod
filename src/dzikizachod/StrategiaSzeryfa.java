package dzikizachod;

/**
 *
 * @author squar
 */
public abstract class StrategiaSzeryfa extends Strategia {
    public StrategiaSzeryfa() {}
    
    @Override
    public int ulecz(Gracz gracz) {
        if (gracz.getAktualnePunktyŻycia() != gracz.getMaksymalnePunktyŻycia()) {
            return gracz.getIndeks();
        }
        else {
            return -1;
        }
    }
    
    @Override
    public int dynamit(Gracz gracz) {
        return -1;
    }
}

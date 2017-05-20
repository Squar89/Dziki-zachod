package dzikizachod;

import java.util.Random;
/**
 *
 * @author squar
 */
public abstract class Strategia {
    public Strategia() {}
    
    public int wybierzCel(Gracz gracz, Akcja akcja, int liczbaKartDanejAkcji) {
        int cel;
        switch (akcja) {
            case ULECZ: cel = ulecz(gracz); break;
            case ZASIEG_PLUS_JEDEN:
            case ZASIEG_PLUS_DWA: cel = zwiększZasięg(gracz); break;
            case STRZEL: cel = strzel(gracz, liczbaKartDanejAkcji); break;
            case DYNAMIT: cel = dynamit(gracz); break;
            default: cel = -1;
        }
        
        return cel;
    }
    
    public int losujIndeks(int rozmiarListy) {
        Random generator = new Random();
        
        return generator.nextInt(rozmiarListy);
    }
    
    public int zwiększZasięg(Gracz gracz) {
        return gracz.getIndeks();
    }
    
    public abstract int ulecz(Gracz gracz);
    
    public abstract int strzel(Gracz gracz, int liczbaKart);
    
    public abstract int dynamit(Gracz gracz);
    
}

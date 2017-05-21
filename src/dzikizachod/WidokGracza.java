package dzikizachod;

/**
 *
 * @author squar
 */
public class WidokGracza {
    private final Gracz gracz;
    private String tożsamośćGracza;
    
    /* Klasa ta służy jako "bufor" do gracza, umozliwia wgląd tylko do niektórych
     * atrybutów klasy Gracz */
    public WidokGracza(Gracz gracz) {
        this.gracz = gracz;
        this.tożsamośćGracza = "Tożsamość ukryta";
    }
    
    public int zobaczMaksymalnePunktyŻycia() {
        return gracz.getMaksymalnePunktyŻycia();
    }
    
    public int zobaczAktualnePunktyŻycia() {
        return gracz.getAktualnePunktyŻycia();
    }
    
    public int zobaczRóżnicaZabitychPomocnikówIBandytów() {
        return gracz.getRóżnicaZabitychPomocnikówIBandytów();
    }
    
    public boolean zobaczCzyStrzelałDoSzeryfa() {
        return gracz.getCzyStrzelałDoSzeryfa();
    }
    
    public String zobaczTożsamośćGracza() {
        return this.tożsamośćGracza;
    }
    
    public void setTożsamośćGracza(String tożsamość) {
        this.tożsamośćGracza = tożsamość;
    }
    
    public boolean zobaczCzyJestBandytą(Gracz graczKtoryPyta) {
        return gracz.czyJestBandytą(graczKtoryPyta);
    }
}

package dzikizachod;

import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author squar
 */
public class PulaAkcji {
    private ArrayList<Akcja> pulaKarty;
    private ArrayList<Akcja> użyteKarty;
    
    public PulaAkcji() {
        this.pulaKarty = new ArrayList<>();
        this.użyteKarty = new ArrayList<>();
    }
    
    public void dodaj(Akcja akcja, int liczba) {
        while (liczba > 0) {
            this.pulaKarty.add(akcja);
            
            liczba--;
        }
    }
    
    public Akcja podajKartę() {
        if (this.pulaKarty.isEmpty()) {
            this.pulaKarty = this.użyteKarty;//to mi da referencje na ten Arraylist ktory jest w srodku czy na użyte karty?
            this.użyteKarty = new ArrayList<>();
            
            this.przetasujPulę();
        }
        
        return pulaKarty.remove(0);
    }
    
    public void przetasujPulę() {
        Collections.shuffle(this.pulaKarty);
    }
}

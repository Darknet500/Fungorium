package Model.Bug;

import Model.Shroomer.Hypa;
import Model.Shroomer.Spore;
import Model.Tekton.TektonBase;

/**
 * A Strategy interfész meghatározza a Bug viselkedését különböző állapotban.
 * Ez az interfész biztosítja a stratégiai műveleteket, a mozgást, harapást
 * és evést, valamint a kör végrehajtását.
 */
public interface Strategy {

    /**
     * Meghatározza, hogy a Bug tud-e enni az adott körben.
     * Csak akkor tud enni, ha nincs effekt hatása alatt, azaz Normal.
     * @return Igaz, ha a Bug ehet, hamis egyébként.
     */
    boolean eat(Bug b, Spore s);

    /**
     * Meghatározza, hogy a Bug meg tudja-e harapni a megadott Hypa példányt.
     *
     * @param b A Bug, amely harapni próbál.
     * @param h A Hypa, amelyet a Bug meg akar harapni.
     * @return Igaz, ha a harapást el lehet végezni, hamis egyébként.
     */
    boolean bite(Bug b, Hypa h);

    /**
     * Meghatározza, hogy a Bug át tud-e mozogni egy másik Tektonra.
     *
     * @param b  A Bug, amely mozogni próbál.
     * @param to A cél Tekton helyszín.
     * @return Igaz, ha a mozgást el lehet végezni, hamis egyébként.
     */
    boolean move(Bug b, TektonBase to);


    boolean eatenByHypa(Bug b, Hypa h);

    /**
     * Végrehajtja a Bug körének lezárását, amely tartalmazhat állapotfrissítéseket
     *
     * @param b A Bug, amelynek a köre lezárul.
     */
    void endOfTurn(Bug b);

}
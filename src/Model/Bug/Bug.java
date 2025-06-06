package Model.Bug;

import Model.Bridge.GameBoard;
import Model.Tekton.*;
import Model.Shroomer.*;
import View.Hitbox.BugHitbox;

/**
 * Bogárt megvalósító osztály, amit a játékos írányít.
 */
public class Bug {

    /**
     * eltárolja a hozzá kapcsolódó hitbox-ot, hogy tudja azt értesíteni az őt ért változásokról
     */
    private BugHitbox hitbox;

    /**
     * A Bug jelenlegi helyét reprezentáló Tekton példány.
     */
    private TektonBase tekton;

    /**
     * A Bug által használt stratégia a mozgáshoz, harapáshoz és evéshez. Ez jelenti azt, hogy milyen hatás alatt áll.
     */
    private Strategy strategy;

    /**
     * A Bugot "birtokló" Bugger objektum
     */
    private Bugger bugger;

    /**
     * megadja, hogy hány köre vna valamamilyen spóra hatása alatt
     */
    private int underEffectSince;

    /**
     * konstruktor, amely a Bug objektumot hely nélküli állapotban
     * és egy normál stratégiával inicializálja.
     * Elnevezi magát, és beleteszi magát a gameBoard nameObjectMap-jébe
     */
    public Bug(Bugger bugger, TektonBase tekton) {
        this.bugger=bugger;
        this.tekton = tekton;
        strategy = new Normal();
        GameBoard.addReferenceToMaps("bug", this);
    }


    public Bugger getBugger() {
        return bugger;
    }

    public void addObserver(BugHitbox hitbox){
        this.hitbox = hitbox;
    }

    /**
     * Beállítja a Bug stratégiáját.
     *
     * @param s Az új stratégia.
     */
    public void setStrategy(Strategy s) {
        if(strategy!=null){
            GameBoard.removeReferenceFromMaps(strategy);
            resetUnderEffectSince();
        }
        strategy = s;

    }

    public Strategy getStrategy() {
        return strategy;
    }

    public BugHitbox getHitbox() {
        return hitbox;
    }

     /**
     * A Bug új Tekton helyre mozog.
     * Ha a mozgás sikeres, a Bug interakcióba lép az új Tektonnal,
     * frissíti a helyét, és végrehajtja a kör végi logikát.
     *
     * @param to A cél Tekton példány.
     */
    public boolean move(TektonBase to) {
        return strategy.move(this, to);
    }

    /**
     * A Bug megharap egy Hypa-t.
     * Ha a stratégia engedélyezi, a Hypa elpusztul,
     * és a Bug köre véget ér.
     *
     * @param h A megharapandó Hypa példány.
     */
    public boolean bite(Hypa h) {
        return strategy.bite(this, h);
    }

    /**
     * A Bug megeszik egy Spore-t.
     * Ha a stratégia engedélyezi az evést, és a spóra elérhető az aktuális Tektonon,
     * annak hatása érvényesül a Bug-ra, növeli a pontszámát, és a spóra eltávolításra kerül.
     *
     * @param s A megevendő Spore példány.
     */
    public boolean eat(Spore s) {
        return strategy.eat(this, s);
    }

    public boolean beEaten(Hypa h){
        return strategy.eatenByHypa(this, h);
    }

    /**
     * Visszaadja a Bug jelenlegi Tekton helyzetét.
     *
     * @return A Bug helyzetét reprezentáló Tekton példány.
     */
    public TektonBase getLocation(){
        return tekton;
    }

     /**
     * Beállítja a Bug helyzetét egy új Tektonra.
     *
     * @param t Az új Tekton helyszín.
     */
    public void setLocation(TektonBase t){

        tekton = t;
        hitbox.onPositionChanged();
    }

    /**
     * Visszaadja, hogy a Bug hány kör óta van hatás alatt.
     * Az érték egy 0 és 2 közötti egész szám, amelyet a SKELETON rendszer kér be.
     *
     * @return A körök száma, amióta a Bug hatás alatt áll.
     */
    public int getUnderEffectSince(){
        return underEffectSince;
    }

    public void increaseUnderEffectSince(){
        underEffectSince++;
    }

    public void endOfTurn(){
        strategy.endOfTurn(this);
    }

    public void resetUnderEffectSince(){
        underEffectSince=0;
    }
}
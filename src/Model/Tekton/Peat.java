package Model.Tekton;

import Model.Bridge.GameBoard;
import Model.Shroomer.Hypa;
import Model.Shroomer.Shroomer;
import Model.Shroomer.Spore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A Peat egy speciális Tekton típus, amelyen több fonal (Hypa) is keresztezheti egymást.
 * Ez azt jelenti, hogy az acceptHypa metódus mindig igaz értéket ad vissza.
 */
public class Peat extends TektonBase {

    /**
     * Alapértelmezett konstruktor, amely meghívja az ősosztály (Tekton) konstruktorát.
     */
    public Peat() {
        super();
        GameBoard.addReferenceToMaps("peat", this);
    }

    /**
     * Ellenőrzi, hogy a megadott Shroomer kapcsolhat-e Hypa-t.
     * Mivel a Peat típusú Tekton nem korlátozza a fonalak számát,
     * ezért mindig igaz értéket ad vissza.
     *
     * @param shroomer - A Shroomer, amely Hypa-t szeretne csatlakoztatni.
     * @return - Mindig true, mivel a Peat nem korlátozza a Hypa-k számát.
     */
    @Override
    public boolean acceptHypa(Shroomer shroomer) {
        return true;
    }

    @Override
    public void endOfRound(){}

    /**
     * Megvizsgálja, hogy a megadott Shroomer növeszthet-e gombát ezen a Tektonon.
     * @param s - A kérdéses Shroomer.
     * @return - Igaz, ha növeszthet (mert teljesül minden feltétel), egyébként hamis.
     */
    @Override
    public boolean canMushroomGrow(Shroomer s) {
        if (hasMushroom())
            return false;
        if (s == null) {
            return false;  // Ha a Shroomer null, akkor nem tud nőni gomba
        }

        // 1. Ellenőrizzük, hogy van-e csatlakozó Hypa-ja a Tekton-hoz
        boolean hasHypa = false;
        for (Hypa hypa : connectedHypas) {
            if (hypa.getShroomer().equals(s)) {
                hasHypa = true;
                break;  // Ha van csatlakozó Hypa, akkor továbblépünk
            }
        }

        if (!hasHypa) {
            return false;  // Ha nincs csatlakozó Hypa, akkor nem nőhet gomba
        }

        // 2. Ellenőrizzük, hogy van-e legalább három spórája a Tekton-on a Shroomer-nek
        int sporeCount = 0;
        for (Spore spore : this.storedSpores) {
            if (spore.getShroomer().equals(s)) {
                sporeCount++;
            }
        }

        // Ha legalább három spóra van, akkor visszaadjuk, hogy nőhet gomba
        if(sporeCount>=3) {
            return true;
        }else{
            return false;
        }

    }

    /**
     * A Tekton törése: létrehoz egy új Tekton példányt,
     * a szomszédokat véletlenszerűen két részre ostja a régi és az új Tekton között. A szétválasztás
     * után a kapcsolódó fonalak elhalnak.
     */
    @Override
    public void breakTekton(long seed) {

        Peat newTekton = new Peat();
        distributeNeighbours(newTekton);
    }

}
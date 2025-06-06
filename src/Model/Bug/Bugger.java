package Model.Bug;

import Model.Bridge.GameBoard;
import Model.Bridge.Player;
import java.util.*;
import Model.Shroomer.Hypa;
import Model.Shroomer.Spore;
import Model.Tekton.TektonBase;

public class Bugger extends Player {

    private List<Bug> bugs;

    public Bugger(){
        GameBoard.addReferenceToMaps("bugger", this);
        bugs = new ArrayList<Bug>();
    }

    public List<Bug> getBugs(){
        return bugs;
    }

    public void addBug(Bug b){bugs.add(b); }

    public void removeBug(Bug b){

        if(bugs.contains(b)){
            bugs.remove(b);
            GameBoard.removeReferenceFromMaps(b);
        }
    }

    public boolean move(Bug b, TektonBase to){
        if(bugs.contains(b)){
            return b.move(to);
        }
        return false;
    }

    public boolean bite(Bug b, Hypa h){
        if(bugs.contains(b)){
            return b.bite(h);
        }
        return false;
    }

    public boolean eat(Bug b, Spore s){
        if(bugs.contains(b)){
            return b.eat(s);
        }
        return false;
    }

    /**
     * game felelossege hogy hivja korok vegen
     */
    public void endOfTurn(){
        for (Bug b : bugs){
            b.endOfTurn();
        }
    }




}

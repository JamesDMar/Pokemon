package battleGame;

public class Inventory {
    private int hyperPotion;
    private int superPotion;
    private int potion;
    private int antidote;
    private int rareCandy;
    public Inventory(){

    }
    public void addHyperPotion(int amount){
        this.hyperPotion+= amount;
    }
    public void addSuperPotion(int amount){
        this.superPotion+= amount;
    }
    public void addPotion(int amount){
        this.potion+= amount;
    }
    public void addAntidote(int amount){
        this.antidote+= amount;
    }
    public void addRareCandy(int amount){
        this.rareCandy+= amount;
    }
    public boolean useHyperPotion(int amount){
        if (this.hyperPotion>0) {
            this.hyperPotion--;
            return true;
        }
        return false;
    }
    public boolean useSuperPotion(int amount){
        this.superPotion--;
        return false;
    }
    public void usePotion(int amount){
        this.potion--;
    }
    public void useAntidote(int amount){
        antidote--;
    }
    public void useRareCandy(int amount){
        hyperPotion--;
    }


}

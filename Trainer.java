package battleGame;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Trainer {
    //<editor-fold desc="Init">
    private String name = null;
    private String gender = null;
    private int pokemonOut = 0;
    private int money = 500;
    private ArrayList<Pokemon> storage = new ArrayList<Pokemon>();
    private Pokemon[] party = new Pokemon[6];
    private HashMap<String, Integer> inventory = new HashMap<String, Integer>();
    //</editor-fold>

    /**
     * Constructor
     * @param name
     */
    public Trainer(String name){
        this.name = name;
        this.inventory.put("Pokeball", 0);
        this.inventory.put("Potion", 0);
        this.inventory.put("Gym Badge", 0);
        this.inventory.put("Hyper Potion", 0);

    }


    //************************GET METHODS AND SET METHODS START HERE*********************************************

    public Pokemon[] getParty() {
        return this.party;
    }

    public ArrayList<Pokemon> getStorage() {
        return storage;
    }

    public String getGender() {
        return this.gender;
    }

    public String getName() {
        return this.name;
    }

    public int getMoney(){
        return this.money;
    }

    public void giveMoney(int amount){
        this.money+=amount;
    }

    public void subtractMoney(int amount){
        this.money-=amount;
    }

    public String partyToString(){
        String party ="";
        for (int i = 1; i<=this.party.length;i++){
            if (this.party[i-1]!=null) {
                party += i + ". " + this.party[i - 1].getFullName() + "\n";
            }
        }
        return  party;
    }

    public void setParty(int location, Pokemon pokemon){
        this.party[location] = pokemon;
    }

    public void setPokemonOut(int pokemon){
        this.pokemonOut = pokemon-1;
    }

    public void addStorage(Pokemon pokemon){
        this.storage.add(pokemon);
    }

    public int getItemAmount(String item){
        return this.inventory.get(item);
    }

    public Pokemon getPokemonOut(){
        return this.party[this.pokemonOut];
    }

    //***********************GET METHODS AND SET METHODS END HERE **********************************************

    /**
     * Heals all pokemon within the party to full health
     */
    public void healParty(){
        for (int i = 0; i<this.party.length;i++){
            if (party[i]!=null){
                party[i].setHealth(party[i].getMaxHealth());
            }
        }
    }

    /**
     * adds specified amount of specified item
     * @param item
     * @param amount
     */
    public void addItem(String item, int amount){
        this.inventory.put(item, this.inventory.get(item)+amount);
    }

    /**
     * deletes specified amount of specified item
     * @param item
     * @param amount
     */
    public void deleteItem(String item, int amount){
        this.inventory.put(item, this.inventory.get(item)-amount);
    }

    /**
     * Checks to see if any pokemon in the party are alive
     * @return
     */
    public boolean isAlive(){
        for (int i = 0; i<party.length;i++){
            if (party[i]!=null) {
                if (party[i].getHealth() > 0) {
                    return true;
                }
            }
        }
        return false;
    }


}

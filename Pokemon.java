package battleGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Paths;
import java.util.Random;
import java.math.*;


/**
 * This class creates a pokemon which can be placed within storage, party or be used alone.
 */
public class Pokemon {

    //<editor-fold desc="Declaring Class Variables">
    private int health;
    private int maxHealth;
    private int healthChange;
    private int healthTotalIncrease;
    private int level;
    private int attack;
    private int attackChange;
    private int attackTotalIncrease;
    private int defence;
    private int defenceChange;
    private int defenceTotalIncrease;
    private int speed;
    private int speedChange;
    private int speedTotalIncrease;
    private int xPLimit = 100;
    private int xP;
    private int evoLevel;
    private int lineNum;
    private String nickname;
    private String name;
    private String type2;
    private String type1;
    private Move[] moves = new Move[4];
    private int status = 0;
    private String[][] pokemonList = new String[167][7];
    private static int count = 0;
    //</editor-fold>

    //<editor-fold desc="Constructors">

    /**
     * Pokemon Constructor
     * @param level
     */
    public Pokemon(int level){
        count++;
        this.level = Math.round(count/10);
        if (this.level==0){
            this.level=1;
        }
        this.getInfo(Math.round(this.level));

    }

    /**
     * Pokemon Constructor
     * @param pokemonName
     */
    public Pokemon(String pokemonName) {
        this.name = pokemonName;
        this.level = 1;
        this.nickname = pokemonName;
        this.getInfo();
    }

    /**
     * Pokemon Constructor
     * @param pokemonName
     * @param nickname
     */
    public Pokemon(String pokemonName, String nickname) {
        this.name = pokemonName;
        this.level = 1;
        this.nickname = nickname;
        this.getInfo();
    }

    /**
     * Pokemon Constructor
     * @param pokemonName
     * @param level
     */
    public Pokemon(String pokemonName, int level) {
        this.name = pokemonName;
        this.nickname = pokemonName;
        this.level = level;
        this.getInfo();
        this.levelAdjust();
    }

    /**
     * Pokemon Constructor
     * @param pokemonName
     * @param level
     * @param nickname
     */
    public Pokemon(String pokemonName, int level, String nickname) {
        this.name = pokemonName;
        this.nickname = nickname;
        this.level = level;
        this.getInfo();
        this.levelAdjust();
    }
    //</editor-fold>

    //<editor-fold desc="Get Methods">

    /**
     * GET AND SET METHODS
     */
    public int getMaxHealth(){
        return this.maxHealth;
    }

    public int getHealth() {
        return this.health;
    }

    public int getLevel() {
        return this.level;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefence() {
        return this.defence;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getStatus() {
        return this.status;
    }

    public String getName() {
        return this.name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getFullName(){
        if (this.nickname!=null&&!this.nickname.equals(this.name)) {
            return this.nickname + "(" + this.name + " lvl "+this.level+")";
        }
        return this.name+"(lvl "+this.level+")";
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public Move[] getMoves(){
        return this.moves;
    }

    public String movesToString(){
        String moveString ="";
        for (int i = 1; i<=this.moves.length;i++){
            if (this.moves[i-1]!=null) {
                moveString += i + ". " + this.moves[i - 1].getName() + "\n";
            }
        }
        return  moveString;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * END OF GET AND SET METHODS
     */
    //</editor-fold>

    /**
     * retrieves data from .csv file
     */
    private void getInfo() {
        Path pathToFile = Paths.get("Pokemon.csv");
        try {
            FileReader fr = new FileReader(String.valueOf(pathToFile));
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            for (int i = 1; i < 167 - 1; i++) {
                line = br.readLine();
                if (line.split(",")[0].equals(this.name)) {
                    if (!line.split(",")[2].equals("")) {
                        this.type2 = line.split(",")[2];
                    } else {
                        this.type2 = null;
                    }
                    this.type1 = line.split(",")[1];
                    this.maxHealth = Integer.parseInt(line.split(",")[3]);
                    this.health=this.maxHealth;
                    this.attack = Integer.parseInt(line.split(",")[4]);
                    this.defence = Integer.parseInt(line.split(",")[5]);
                    this.speed = Integer.parseInt(line.split(",")[6]);
                    this.evoLevel = Integer.parseInt(line.split(",")[7]);
                    this.lineNum = i;

                    this.moves[0] = new Move(this.type1,this.type2);
                    this.moves[1] = new Move(this.type1,this.type2);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.lang.NullPointerException e) {
            slowText("No Pokemon known as \"" + this.name + "\"");
        }
    }

    /**
     * retrieves data from .csv file
     * @param level
     */
    private void getInfo(int level) {
        Path pathToFile = Paths.get("Pokemon.csv");
        String line = null;
        Random rand = new Random();
        this.lineNum = rand.nextInt(167);
        try {
            FileReader fr = new FileReader(String.valueOf(pathToFile));
            BufferedReader br = new BufferedReader(fr);

            for (int i = 1; i < this.lineNum - 1; i++) {
                line = br.readLine();
                }
            }catch (IOException e) {
            e.printStackTrace();
        }
        if (!line.split(",")[2].equals("")) {
            this.type2 = line.split(",")[2];
        } else {
            this.type2 = null;
        }
        this.name = line.split(",")[0];
        this.type1 = line.split(",")[1];
        this.maxHealth = Integer.parseInt(line.split(",")[3]);
        this.health = this.maxHealth;
        this.attack = Integer.parseInt(line.split(",")[4]);
        this.defence = Integer.parseInt(line.split(",")[5]);
        this.speed = Integer.parseInt(line.split(",")[6]);
        this.evoLevel = Integer.parseInt(line.split(",")[7]);


        this.moves[0] = new Move(this.type1, this.type2);
        this.moves[1] = new Move(this.type1, this.type2);

        if (this.evoLevel<this.level){
            getInfo(this.level);
        }

    }

    /**
     * calculates the damage a move will do
     * @param move
     * @return damage
     */
    public int attack(int move, Pokemon enemy){
        return ((((this.level*2)/5+2)*this.moves[move].getDamage()*this.attack/enemy.defence)/50)+2;
    }

    /**
     * Prints out all info about the object in an organized fashion
     * @return String
     */
    public String toString() {
        return "Pokemon: " + this.name + "\nNickname: " + this.nickname + " \nLevel: " + this.level + " \nHealth: "
                + this.health + " \nAttack: " + this.defence + " \nDefence: " + this.defence + " \nSpeed: " + this.speed;
    }

    /**
     * adjusts the pokémon's stats according to their level(only used if the pokemon is created at a level higher than 1)
     */
    public void levelAdjust() {
        this.xPLimit *= 1.2 * this.level;
        this.maxHealth += 11 * this.level;
        this.speed += 6 * this.level;
        this.defence += 4 * this.level;
        this.attack += 3 * this.level;
        this.status = 1;
        //the pokemon learn new moves if above certain levels
        if (this.level>=5){
            this.moves[2] = new Move(this.type1,this.type2);
            if (this.level>=10) {
                this.moves[3] = new Move(this.type1, this.type2);
            }
        }
    }

    /**
     * Checks if a pokeball throw is successful
     * @return int number of shakes
     */
    public int capture(){
        Random rand = new Random();
        int chance = ((1-(this.health/this.maxHealth))*50)+rand.nextInt(100);
        if (chance>100){
            return 3;
        }else if (chance> 60){
            return 2;
        }else{
            return 1;
        }
    }

    /**
     * Heals the pokemon for a specified amount
     * @param amount
     */
    public void heal(int amount){
        this.health+=amount;
        if (this.health>this.maxHealth){
            this.health = this.maxHealth;
        }
    }

    /**
     * damages the pokemon based on the amount specified
     * @param amount
     * @return
     */
    public int damage(int amount){
        this.health-=(amount);
        return amount;
    }

    /**
     * changes the pokemons nickname based on param
     * @param newName
     */
    public void rename(String newName) {
        this.nickname = newName;
    }

    /**
     * adds specified amount of xp. levels up the pokemon if it exceeds level cap
     * @param xP
     */
    public void addXP(int xP) {
        this.xP += xP;
        if (this.xP >= this.xPLimit) {
            levelUp();
            return;
        }
        slowText(nickname + " gained " + xP + "EXP (" + this.xP + "/" + this.xPLimit + ")");
    }

    /**
     * increases pokemons stats due to level if level goes past evolution cap it calls this.evolve()
     */
    private void levelUp() {
        Random rand = new Random();
        this.xP -= this.xPLimit;
        this.xPLimit *= 1.2;
        this.level++;
        this.status = 1;
        slowText(nickname + " Leveled up! " + nickname + " is now level " + this.level + "!"
                + "(" + this.xP + "/" + this.xPLimit + ")");
        if (this.level == this.evoLevel) {
            slowText(this.name + " is beginning to evolve!");
            this.evolve();
        } else {
            this.healthChange = rand.nextInt(10) + 15;
            this.healthTotalIncrease += this.healthChange;
            this.maxHealth += healthChange;
            this.speedChange = rand.nextInt(3) + 1;
            this.speedTotalIncrease += this.speedChange;
            this.speed += speedChange;
            this.defenceChange = rand.nextInt(2) + 2;
            this.defenceTotalIncrease += this.defenceChange;
            this.defence += defenceChange;
            this.attackChange = rand.nextInt(2) + 3;
            this.attackTotalIncrease += this.attackChange;
            this.attack += attackChange;

            slowText("Stats Up! Health: " + this.maxHealth + "(" + (this.maxHealth - this.healthChange) + "+"
                    + this.healthChange + ")" + " Speed: " + this.speed + "(" + (this.speed - this.speedChange) + "+"
                    + this.speedChange + ")" + " Defence: " + this.defence + "(" + (this.defence - this.defenceChange)
                    + "+" + this.defenceChange + ")" + " Attack: " + this.attack + "(" + (this.attack - this.attackChange)
                    + "+" + this.attackChange + ")");
        }
        if (this.xP > this.xPLimit) {
            this.addXP(0);
        }
        if (this.level==5){
            this.moves[3] = new Move(this.type1,this.type2);
        }
        if (this.level==8){
            this.moves[4] = new Move(this.type1,this.type2);
        }
    }

    /**
     * evolves the pokemon grabbing new stats and name from .csv file
     */
    private void evolve() {
        Path pathToFile = Paths.get("Pokemon.csv");
        try {
            FileReader fr = new FileReader(String.valueOf(pathToFile));
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            for (int i = 1; i < this.lineNum + 1; i++) {
                line = br.readLine();
            }
            line = br.readLine();

            slowText("Stats Up! Health: " + (Integer.parseInt(line.split(",")[3]) + this.healthTotalIncrease) + "(" + (this.maxHealth) + "+"
                    + (Integer.parseInt(line.split(",")[3]) + this.healthTotalIncrease - this.maxHealth) + ")"
                    + " Speed: " + (Integer.parseInt(line.split(",")[6]) + this.speedTotalIncrease) + "(" + (this.speed) + "+"
                    + (Integer.parseInt(line.split(",")[6]) + this.speedTotalIncrease - this.speed) + ")"
                    + " Defence: " + (Integer.parseInt(line.split(",")[5]) + this.defenceTotalIncrease) + "(" + (this.defence)
                    + "+" + (Integer.parseInt(line.split(",")[5]) + this.defenceTotalIncrease - this.defence) + ")"
                    + " Attack: " + (Integer.parseInt(line.split(",")[4]) + this.attackTotalIncrease) + "(" + (this.attack)
                    + "+" + (Integer.parseInt(line.split(",")[4]) + this.attackTotalIncrease - this.attack) + ")");

            if (!line.split(",")[2].equals("")) {
                this.type2 = line.split(",")[2];
            } else {
                this.type2 = null;
            }
            slowText(line.split(",")[0]);
            this.nickname = line.split(",")[0];
            this.name = line.split(",")[0];
            this.type1 = line.split(",")[1];
            this.maxHealth = Integer.parseInt(line.split(",")[3]);
            this.attack = Integer.parseInt(line.split(",")[4]);
            this.defence = Integer.parseInt(line.split(",")[5]);
            this.speed = Integer.parseInt(line.split(",")[6]);
            this.evoLevel = Integer.parseInt(line.split(",")[7]);
            this.lineNum++;


        } catch (IOException e) {
            e.printStackTrace();
        }
        this.evolveAdjust();

    }

    /**
     * adjusts the newly evolved pokemon back to the level it is.
     */
    private void evolveAdjust() {
        this.maxHealth += this.healthTotalIncrease;
        this.defence += this.defenceTotalIncrease;
        this.attack += this.attackTotalIncrease;
        this.speed += this.speedTotalIncrease;

    }

    /**
     * slowly prints text to screen to make seem more like original pokemon.
     * @param text
     */
    public void slowText(String text){
        for (int i =0;i<text.length();i++){
            System.out.print(text.charAt(i));
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print("\n");
    }

}
package battleGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * This class contains all information for moves that pokemon may use.
 */
public class Move {
    private int damage;
    private String name;
    private String type;
    private double accuracy;
    private int pp;

    /**
     * Constructor for the Move class
     * @param type
     * @param type2
     */
    public Move(String type, String type2){
        getInfo(type,type2);
    }

    /**
     * Collects info for moves based on the pokemon types based in
     * @param type
     * @param type2
     */
    private void getInfo(String type, String type2) {
        Path pathToFile = Paths.get("Moves.csv");
        try {
            FileReader fr = new FileReader(String.valueOf(pathToFile));
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            Random rand = new Random();
            line = br.readLine();
            for (int i = 0; i < rand.nextInt(89); i++) {
                line = br.readLine();
            }
            this.name = line.split(",")[0];
            this.type = line.split(",")[1];
            this.damage = Integer.parseInt(line.split(",")[2]);
            this.pp = Integer.parseInt(line.split(",")[3]);
            this.accuracy = Integer.parseInt(line.split(",")[4]);


        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!this.type.equals(type)&&!this.type.equals(type2)&&!this.type.equals("Normal")){
            getInfo(type,type2);
        }
    }

    /**
     * Returns a String name
     * @return this.name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns int damage
     * @return this.damage
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Returns int value pp
     * @return pp
     */
    public int getPp() {
        return this.pp;
    }

    /**
     * returns accuracy
     * @return this.accuracy
     */
    public double getAccuracy() {
        return this.accuracy;
    }

    /**
     * retruns type
     * @return this.type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Subtracts 1 from the pp
     */
    public void use(){
        this.pp--;
    }
}

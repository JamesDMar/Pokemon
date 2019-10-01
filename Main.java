package battleGame;

import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.*;


public class Main {
    static boolean done = false;
    public static void main(String[] args) {
        Main game = new Main();
    }

    /**
     * Contains intro for the game (lots of slowText()) pick your starter pokemon etc...
     */
    public Main() {
        Scanner scanner = new Scanner(System.in);
        slowText("Hello! Welcome to the world of Pokémon! Please enter your name.");
        slowText("Name:");
        Trainer trainer = new Trainer(scanner.nextLine());
        slowText("Hey " + trainer.getName() + " you can have one of three starter Pokémon! \n 1. Bulbasaur\n 2. Squirtle\n 3. Charmander");
        int starter = 0;

        while (starter > 3 || starter < 1) {

            slowText(" Which Pokémon would you like? (1-3): ");
            try {
                starter = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine();
            }
        }

        if (starter == 3) {
            addPokemon(trainer,new Pokemon("Charmander",5));
        } else if (starter == 2) {
            addPokemon(trainer, new Pokemon("Squirtle",5));
        } else {
            addPokemon(trainer,new Pokemon("Bulbasaur",5));
        }
        this.renamePokemon(trainer.getParty()[0]);
        trainer.healParty();

        slowText(trainer.getParty()[0].getFullName() + " has been added to your party!\nHere is a backpack full of some essentials to get you started!");
        addTrainerItem("Pokeball", 3, trainer);
        addTrainerItem("Potion",5,trainer);
        addTrainerItem("Hyper Potion",1,trainer);

        slowText("Now that you are all set lets see how you do against a Bulbasaur!\n");
        timeOut();
        battle(trainer, new Pokemon("Bulbasaur"));

        slowText("Ok "+trainer.getName()+" now it is time for you to go out on your own and Catch them all! Between\n " +
                "battles your Pokemon will be fully healed and you will have the chance to go to the shop. Now, \n" +
                "stop standing around and start battling!");

        game(trainer);

    }

    /**
     * This loops for ever between shop and battle with random pokemon
     * @param trainer
     */
    public void game(Trainer trainer){
        boolean playing = true;
        while (playing){
            shopQuestion(trainer);
            battle(trainer, new Pokemon(1));
        }
    }

    /**
     * asks trainer if they would like to go to the shop
     * @param trainer
     */
    public void shopQuestion(Trainer trainer){
        slowText("Would you like to go to the shop before beginning your next battle?\n(Y/N):");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        if (answer.toUpperCase().equals("Y")) {
            shop(trainer);

        }
    }

    /**
     * asks trainer what they would like to buy at the shop
     * @param trainer
     */
    public void shop(Trainer trainer){
        slowText("What would you like to buy:\n1. Pokeball\n2. Potion\n3. Hyper Potion\n4. Leave\n(1-4): ");
        int answer = intInput();
        switch (answer){
            case 1: if (trainer.getMoney()>=100) {
                addTrainerItem("Pokeball", 1, trainer);
                trainer.subtractMoney(300);shop(trainer);
            }else{
                slowText("Sorry but you do not have enough money for this item.");
                shop(trainer);
                }
                break;
            case 2: if (trainer.getMoney()>=150) {
                addTrainerItem("Potion", 1, trainer);
                trainer.subtractMoney(300);shop(trainer);
            }else{
                slowText("Sorry but you do not have enough money for this item.");
                shop(trainer);
                }
                break;
            case 3: if (trainer.getMoney()>=300) {
                addTrainerItem("Hyper Potion", 1, trainer);
                trainer.subtractMoney(300);shop(trainer);
                }else{
                slowText("Sorry but you do not have enough money for this item.");
                shop(trainer);

                }
                break;
            case 4: return;
            default: shop(trainer);break;
        }
    }

    /**
     * asks trainer if they would like to rename their pokemon and if so what to rename it to
     * @param pokemon
     */
    public void renamePokemon(Pokemon pokemon){
        Scanner scanner = new Scanner(System.in);
        slowText("\nWould you like to give " + pokemon.getName() + " a nickname?\n(Y/N): ");
        String answer = scanner.nextLine();

        if (answer.toUpperCase().equals("Y")) {
            slowText("Please enter a new name: ");
            pokemon.rename(scanner.nextLine());
        }
    }

    /**
     * adds pokemon to trainers party/storage depending (Storage not fully implemented users cannot take from storage :(
     * sorry Mr. Troung)
     * @param trainer
     * @param newPokemon
     */
    public void addPokemon(Trainer trainer, Pokemon newPokemon){
        for (int i = 0; i<trainer.getParty().length;i++){
            if(trainer.getParty()[i]==null){
                trainer.getParty()[i] = newPokemon;
                return;
            }
        }
        slowText("Your party is currently full where would you like to put "+newPokemon.getFullName()+"? \n 1. Storage \n 2. Party");
        slowText("Option 1 or 2: ");

        int answer = intInput();
        if (answer==1){
            trainer.addStorage(newPokemon);
            slowText(newPokemon.getNickname()+" has been added to your storage!");
        } else if (answer==2){
            slowText("Which Pokémon from your party do you wish to move to storage?");
            slowText(trainer.partyToString());
            answer = this.intInput();
            trainer.addStorage(trainer.getParty()[answer-1]);
            trainer.setParty(answer-1,newPokemon);
            slowText(trainer.getParty()[answer-1].getFullName()+" has replaced "+trainer.getStorage().get(trainer.getStorage().size()-1).getFullName()+" in your party.");
        }
    }

    /**
     * easy way of taking input
     * @return input
     */
    public int intInput(){
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextInt();
        }catch (java.util.InputMismatchException e){
            slowText("Please enter a valid option: ");
            return this.intInput();
        }

    }

    /**
     * adds items to trainers inventory and print it out nicely
     * @param item
     * @param amount
     * @param trainer
     */
    public void addTrainerItem(String item, int amount, Trainer trainer){
        if (amount==1) {
            slowText("A " + item + " has been added to your inventory!");
            trainer.addItem(item, amount);
        }else{
            slowText(amount+" "+item+"s have been added to your inventory!");
            trainer.addItem(item,amount);
        }


    }

    /**
     * asks which item the user would like to use
     * @param trainer
     * @param wild
     */
    public void useItem(Trainer trainer, Pokemon wild){
        slowText("Which item would you like to use:\n1. Pokeball("+trainer.getItemAmount("Pokeball")+")\n" +
                "2. Potion("+trainer.getItemAmount("Potion")+")\n" +
                "3. Hyper Potion("+trainer.getItemAmount("Hyper Potion")+")\n" +
                "4. Back\nPlease enter your choice: " );
        int choice = intInput();
        switch(choice){
            case 1: done = capture(trainer, wild);break;
            case 2: usePotion(trainer,wild);break;
            case 3: useHyperPotion(trainer,wild);break;
            case 4: takeTurn(trainer,wild);break;
            default: useItem(trainer,wild);

        }

    }

    /**
     * asks which pokemon to use the potion on
     * @param trainer
     * @param wild
     */
    public void usePotion(Trainer trainer,Pokemon wild){
        if (trainer.getItemAmount("Potion")<=0){
            slowText("Sorry you are out of Potions.");
            useItem(trainer,wild);
            return;
        }
        slowText("Which pokemon would you like to use a potion on:\n"+trainer.partyToString());
        int choice = intInput();
        if (choice>6||choice<1||trainer.getParty()[choice-1]==null){
            usePotion(trainer,wild);
            return;
        }
        trainer.getParty()[choice-1].heal(100);
    }

    /**
     * asks which pokemon to use the hyper potion on
     * @param trainer
     * @param wild
     */
    public void useHyperPotion(Trainer trainer,Pokemon wild){
        if (trainer.getItemAmount("Hyper Potion")<=0){
            slowText("Sorry you are out of Hyper Potions.");
            useItem(trainer,wild);
            return;
        }
        slowText("Which pokemon would you like to use a hyper potion on:\n"+trainer.partyToString());
        int choice = intInput();
        if (choice>6||choice<1||trainer.getParty()[choice-1]==null){
            useHyperPotion(trainer,wild);
            return;
        }
        trainer.getParty()[choice-1].heal(200);
    }

    /**
     * trys to capture the wild pokemon and displays result to screen (Including shakes of the pokeball!)
     * @param trainer
     * @param wild
     * @return
     */
    public boolean capture(Trainer trainer,Pokemon wild){
        int ballShakes = wild.capture();
        slowText("You threw a pokeball at "+wild.getName()+"!");
        for (int i = 1;i<ballShakes+1;i++){
            slowText(i+"...");
            timeOut();
        }
        if (ballShakes==3){
            slowText(wild.getFullName()+" has been caught!");
            addPokemon(trainer, wild);

            return true;
        } else {
            slowText(wild.getFullName()+" has escaped the Pokeball!");
            return false;
        }
    }

    /**
     * Checks if the battle should keep going. all pokemon fainting or wild pokemon fainting or wild pokemon being caught
     * end the battle
     * @param trainer
     * @param wild
     * @return bool
     */
    public boolean checkBattle(Trainer trainer, Pokemon wild){
        if (trainer.isAlive() && wild.getHealth()>0){
            return true;
        }
        return false;
    }

    /**
     * loops player and wild pokemon turns
     * @param trainer
     * @param wild
     */
    public void battle(Trainer trainer, Pokemon wild){

        slowText("You have encountered a wild "+wild.getName()+"!");
        while(trainer.isAlive() && wild.getHealth()>0 && done==false){
            if (trainer.getPokemonOut().getSpeed()>wild.getSpeed()){
                slowText("\nIt is now your turn.\nYour Pokemon: "+trainer.getPokemonOut().getHealth()+"HP\n" +
                        "Wild Pokemon: "+wild.getHealth()+"HP\n");
                timeOut();
                takeTurn(trainer, wild);
                if (checkBattle(trainer,wild)&&!done) {
                    slowText("\nIt is now the wild " + wild.getName() + "'s turn.\nYour Pokemon: " + trainer.getPokemonOut().getHealth() + "HP\n" +
                            "Wild Pokemon: " + wild.getHealth() + "HP\n");
                    timeOut();
                    wildTurn(wild, trainer);
                }
            }else {
                slowText("\nIt is now the wild "+wild.getName()+"'s turn.\nYour Pokemon: "+trainer.getPokemonOut().getHealth()+"HP\n" +
                        "Wild Pokemon: "+wild.getHealth()+"HP\n");
                timeOut();
                wildTurn(wild, trainer);
                if (checkBattle(trainer, wild)&&!done) {
                    slowText("\nIt is now your turn.\nYour Pokemon: " + trainer.getPokemonOut().getHealth() + "HP\n" +
                            "Wild Pokemon: " + wild.getHealth() + "HP\n");
                    timeOut();
                    takeTurn(trainer, wild);
                }
            }
        }
        if (wild.getHealth()<=0){
            slowText("The wild "+wild.getName()+" has fainted!");
            trainer.getPokemonOut().addXP(25*wild.getLevel());
            trainer.giveMoney(25*wild.getLevel());
        } else if (!trainer.isAlive()){
            slowText("All of your Pokemon have fainted! They have been brought to the nearest Pokemon Center to be healed. \n" +
                    "You gotta be more careful out there! ");
        }
        trainer.healParty();
    }

    /**
     * asks what the trainer would like to do for their turn
     * @param trainer
     * @param wild
     */
    public void takeTurn(Trainer trainer,  Pokemon wild){
        slowText("What would you like to do:\n1. Moves\n2. Items\n3. Switch Pokemon");
        switch (intInput()){
            case 1: chooseMove(trainer, wild);break;
            case 2: useItem(trainer, wild);break;
            case 3: switchPokemon(trainer,wild);break;
            default:takeTurn(trainer,wild);break;
        }
    }

    /**
     * asks the trainer which pokemon they wish to switch to
     * @param trainer
     * @param wild
     */
    private void switchPokemon(Trainer trainer, Pokemon wild) {
        slowText("\nWhich Pokemon would you like to switch to?\n"+trainer.partyToString()+"7. Back\nPlease choose an option:");
        int choice = intInput();
        if (choice == 7){
            takeTurn(trainer,wild);
            return;
        }
        if (choice>6||choice<1||trainer.getParty()[choice-1]==null){
            switchPokemon(trainer, wild);
            return;
        }
 
        trainer.setPokemonOut(choice);

    }

    /**
     * allows the trainer to pick which move to use in battle
     * @param trainer
     * @param wild
     */
    public void chooseMove(Trainer trainer, Pokemon wild){
        slowText("What move would you like to use:\n"+trainer.getPokemonOut().movesToString()+"5. Back\nPlease select an option: ");
        int choice = intInput();
        if (choice==5){
            takeTurn(trainer,wild);
            return;
        }
        if (choice>4 || choice<1||trainer.getPokemonOut().getMoves()[choice-1]==null){
            chooseMove(trainer,wild);
            return;
        }
        slowText("\n"+trainer.getPokemonOut().getName()+" uses "+trainer.getPokemonOut().getMoves()[choice-1].getName()+". It inflicts "+wild.damage(trainer.getPokemonOut().attack(choice-1,wild))+" damage!");
    }

    /**
     * randomly picks a move for the wild pokemon to perform on their turn
     * @param wild
     * @param trainer
     */
    public void wildTurn(Pokemon wild, Trainer trainer){
        Random rand = new Random();
        int choice = rand.nextInt(3);
        if (wild.getMoves()[choice]==null){
            wildTurn(wild,trainer);
            return;
        }
        slowText("\nThe wild "+wild.getName()+" uses "+wild.getMoves()[choice].getName()+". It inflicts "+trainer.getPokemonOut().damage(wild.attack(choice, wild))+" damage!");
    }

    /**
     * puts small 0.5 second pause in program
     */
    public void timeOut(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * all printing uses this instead of System.out.print/println to slowly text print so the user can read as it prints
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


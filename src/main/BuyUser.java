package main;

import java.util.ArrayList;
/** This is a buy type user that extends the Abstract User class. A Buy type user cannot sell games, only buy. And
 * it cannot create or delete users.
 *
 */
public class BuyUser extends AbstractUser {

//    public BuyUser(String username) {
//        super(username);
//    }


//    public BuyUser(String username, Double credit) {
//        super(username);
//        this.accountBalance = credit;
//        this.type = "BS";
//    }

    private BuyUser(BuyUserBuilder builder) {
        this.username = builder.username;
        this.accountBalance = builder.accountBalance;
        this.type = "BS";
        this.inventory = builder.inventory;

    }

    public String getUsername(){
        return this.username;
    }

    public String getType(){
        return this.type;
    }

    public ArrayList<Game> getInventory(){
        return this.inventory;
    }

    public double getAccountBalance(){
        return this.accountBalance;
    }

    public ArrayList<String> getTransactionHistory(){
        return this.transactionHistory;
    }


    /** Prints that the user cannot sell a game.
     *
     * @param game game to be sold
     * @param market Marketplace game will be sold on
     */
    //THIS DOES NOT FOLLOW THE RIGHT FORMAT
    @Override
    public void sell(Game game, Marketplace market){
        System.out.println("ERROR: \\ < Failed Constraint: "+ this.username + " does not have the ability to sell games.");
    }


    /**
     * Sends Games to a User if they can accept this Game
     *
     * @param game a Game to be gifted
     * @param reciever person who will be recieving the Gift
     * @param market the current Market
     */
    @Override
    public void gift(Game game, AbstractUser reciever, Marketplace market){

        // Reciever is a Sell user
        if (reciever instanceof SellUser){
            System.out.println("ERROR: \\< Failed Constraint: Sell User can not accept any gifts. >//");
        }
        else{
            String gameName = game.getName();
            // check if the Receiver has the game in their inventory
            boolean inRecInv = !reciever.gameInInventory(game);
            // Check if the Receiver has the game up for Sale on the Market
            boolean inRecMar = !market.checkSellerSellingGame(reciever.getUsername(), gameName);
            // if the User can accept the game then check if the sender can send the game
            if(inRecInv && inRecMar){
                boolean inSenInv = this.gameInInventory(game);
                // User can send the gift, game is added to the Receiver's inventory
                if (inSenInv){
                    // Game needs to be removed from the sender's inventory
                    this.removeFromInventory(gameName);
                    this.inventory.add(game);
                }
                // Sender doesn't have the game
                else{
                    System.out.println("ERROR: \\" + this.username + " does not have the " + gameName +
                            ".\n Gift transaction failed.");
                }
            }
            // Reciever already has the game
            else{
                System.out.println("ERROR: \\" + reciever.getUsername() + " already has " +gameName+
                        ".\n Gift transaction failed.");

            }
        }
    }



    /**
     * Checks and removes the game for the User
     *
     * @param game The game being removed
     * @param market The current market
     */
    @Override
    public void removegame(Game game, Marketplace market){
        String currGame = game.getName();
        // check if the User has the game
        boolean inMyInv = this.gameInInventory(game);
        // remove from inventory
        if (inMyInv){
            this.removeFromInventory(currGame);
            System.out.println(game.getName()+ " was removed from the User's inventory.");
        }
        else{
            System.out.println(game.getName()+ " was not found in the User's inventory.");
        }
    }

    public static class BuyUserBuilder {

        private String username; // required
        //        public String type;
        public double accountBalance;
        public ArrayList<Game> inventory;
        public double newFunds;
        public ArrayList<String> transactionHistory;

        public BuyUserBuilder(String name) {
            this.username = name;
            this.accountBalance = 0.00;
            this.transactionHistory = new ArrayList<>();
        }

        public BuyUserBuilder balance(double accountBalance){
            this.accountBalance = accountBalance;
            return this;
        }

        public UserBuilder inventoryGames(ArrayList<Game> inventory){
            this.inventory.addAll(inventory);
            return this;
        }

        public BuyUserBuilder newFunds(double newFunds){
            this.newFunds = newFunds;
            return this;
        }

        public BuyUserBuilder transactionHistory(ArrayList<String> transactions){
            this.transactionHistory.addAll(transactions);
            return this;
        }

        public BuyUser build() {
            BuyUser user = new BuyUser(this);
            return new BuyUser(this);
        }

    }

}
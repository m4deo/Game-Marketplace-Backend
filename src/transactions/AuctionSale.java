package transactions;

import main.AbstractUser;
import main.Game;
import main.Marketplace;

import java.util.ArrayList;

public class AuctionSale implements Transaction {

    String username;
    String type;
    float funds;

    public AuctionSale(String u, String t, String f) {
        this.username = u;
        this.type = t;
        this.funds = Float.parseFloat(f);
    }

    @Override
    public String execute(ArrayList<AbstractUser> users, ArrayList<Game> games, Marketplace market, String login) {
        return login;
    }
}

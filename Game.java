public class Game{
	player[] group;
	int[] sizes;
	Card[] available;

    public static void main(String[] args) {
    	int num_players = 1
        this.group = new Player[num_players];
        this.sizes = new int[16];
        this.available = new Card[16];
        int i = 0;
        while (i < 10)
        {
        	sizes[i] = 10;
        	i++;
        }
        int currentPlayer = 0;
        while (this.notOver())
        {
        	this.group[currentPlayer].play();
        	currentPlayer++;
        	if (currentPlayer >= num_players)
        	{
        		currentPlayer = 0;
        	}
        }
    }
    
    private boolean notOver()
    {
    	return true;
    }
    
	public class Card {
		public boolean isAction;
		public int treasure;
		public int points;
		public String name;
		public int cost;
		public Card()
		{
			this.isAction = false;
			this.treasure = 0;
			this.points = 0;
			this.name = "";
			this.cost = 0;
		}
		public void play(Player person);
	}	
	
	public class Copper extends Card{
		public Copper()
		{
			super();
			this.treasure = 1;
			this.name = "Copper";
		}
	}

	public class Village extends Card{
		public Village()
		{
			super();
			this.isAction = true;
			this.name = "Village";
			this.cost = 3;
		}
		
		public void play(Player person)
		{
			person.draw();
			person.actions += 2;
		}
	}
	
	public class Player {
		private ArrayList<Card> hand;
		private ArrayList<Card> deck;
		private ArrayList<Card> discard;
		public int actions;
		public int gold;
		public int buys;
		
		public Player()
		{	
			this.hand = new ArrayList(5);
			this.deck = new ArrayList(10);
			this.discard = new ArrayList(10);
			this.discard.add(new Copper());
			this.discard.add(new Copper());
			this.discard.add(new Copper());
			this.discard.add(new Copper());
			this.discard.add(new Copper());
			this.discard.add(new Copper());
			this.discard.add(new Estate());
			this.discard.add(new Estate());
			this.discard.add(new Estate());
			this.addDiscard();
		}
		
		private void addDiscard()
		{
			Random shuffle = new Random();
			while (!this.discard.isEmpty())
			{
				int i = shuffle.newInt(this.discard.size())
				{
					this.deck.add(this.discard[i]);
					this.discard.remove(i);
				}
			}
		}
		
		public void draw()
		{
			if (this.deck.isEmpty())
			{
				if (!this.discard.isEmpty())
				{
					this.addDiscard();
				}
			}
			else
			{
				this.hand.add(deck(0));
				this.deck.remove(0);
			}
		}
		
		private void printHand()
		{
			int i = 0;
			while (i < this.hand.size())
			{
				System.Out.println(this.hand[i].name + ", ");
				i++;
			}
		}
		
		public static void play()
		{
			this.actions = 1;
			buys = 1;
			this.gold = 0;
			this.printHand();
			String answer;
			int index;
			Scanner in = new Scanner(System.in);
			while (actions > 1)
			{
				System.out.println("Would you like to play an action? Y/N");
				answer = in.nextLine();
				if (answer == "Y")
				{
					this.printHand();
					System.out.println("Which action would you like to play? (Type index, 0-based)");
					index = in.nextInt();
					if (index >= 0 && index < this.hand.length)
					{
						this.hand[index].play(this);
						actions--;
					}
					else
					{
						this.actions = 0;
					}
				}
				else
				{
					this.action = 0;
				}
			}
			index = 0;
			while (index < this.hand.length)
			{
				this.gold += this.hand[index].treasure;
			}
			Game.this.printAvailable();
			while (buys > 0)
			{
				System.out.println("Which card would you like to buy? (Type index, 0-based, -1 to not buy anything)");
				index = in.nextInt();
				if (index > -1 && index < 16)
				{
					if (Game.this.available[index].cost <= gold && Game.this.sizes[index]>0)
					{
						this.discard.add(Game.this.available[index]);
						this.gold -= Game.this.available[index].cost;
						buys--;
					}
					else
					{
						System.out.println("Not a valid buy, try again");
					}
				}
				else
				{
					buys = 0;
				}
			}
		}
	}
}


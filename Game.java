import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game
{
	public static void main(String[] args)
	{
		Instance now = new Instance();
		now.play();
	}
	public static class Instance
	{
		Player[] group;
		int[] sizes;
		Card[] available;
		int num_players;
		
		public Instance()
		{
			this.sizes = new int[16];
			this.available = new Card[16];
			this.num_players = 3;
			this.group = new Player[this.num_players];
			int i = 0;
			while (i < num_players)
			{
				group[i] = new Player();
				i++;
			}
		}
		
		public void play() {
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
	
		public void printAvailable()
		{
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
			public void play(Player person)
			{
			}
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

		public class Estate extends Card{
			public Estate()
			{
				super();
				this.points = 1;
				this.name = "Estate";
				this.cost = 2;
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
					int i = shuffle.nextInt(this.discard.size());
					{
						this.deck.add(this.discard.get(0));
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
					this.hand.add(deck.get(0));
					this.deck.remove(0);
				}
			}
		
			private void printHand()
			{
				int i = 0;
				while (i < this.hand.size())
				{
					System.out.println(this.hand.get(i).name + ", ");
					i++;
				}
			}
		
			public void play()
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
						if (index >= 0 && index < this.hand.size())
						{
							this.hand.get(index).play(this);
							actions--;
						}
						else
						{
							this.actions = 0;
						}
					}
					else
					{
						this.actions = 0;
					}
				}
				index = 0;
				while (index < this.hand.size())
				{
					this.gold += this.hand.get(index).treasure;
				}
				Instance.this.printAvailable();
				while (buys > 0)
				{
					System.out.println("Which card would you like to buy? (Type index, 0-based, -1 to not buy anything)");
					index = in.nextInt();
					if (index > -1 && index < 16)
					{
						if (Instance.this.available[index].cost <= gold && Instance.this.sizes[index]>0)
						{
							this.discard.add(Instance.this.available[index]);
							this.gold -= Instance.this.available[index].cost;
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
}


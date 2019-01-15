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
		Player[] group;   //an array of all the players
		int[] sizes;      //how many of each card type is available
		Card[] available; //what card types are available
		int num_players;  //how many players are in the game
		int num_cards = 16;
		
		public Instance()
		{
			this.sizes = new int[this.num_cards];
			this.available = new Card[this.num_cards];
			this.num_players = 2;
			this.group = new Player[this.num_players];
			int i = 0;
			while (i < num_players)
			{
				group[i] = new Player(i);
				i++;
			}
			this.sizes[0] = 50;
			this.available[0] = new Copper();
			this.sizes[1] = 50;
			this.available[1] = new Silver();
			this.sizes[2] = 50;
			this.available[2] = new Gold();
			this.sizes[3] = 50;
			this.available[3] = new Estate();
			this.sizes[4] = 50;
			this.available[4] = new Duchy();
			this.sizes[5] = 12;
			this.available[5] = new Province();
			this.sizes[6] = 10;
			this.available[6] = new Village();
			this.sizes[7] = 10;
			this.available[7] = new Woodcutter();
			this.sizes[8] = 10;
			this.available[8] = new Smithy();
			this.sizes[9] = 10;
			this.available[9] = new Throne_Room();
			this.sizes[10] = 10;
			this.available[10] = new Library();
			this.sizes[11] = 10;
			this.available[11] = new Moneylender();
			this.sizes[12] = 10;
			this.available[12] = new Mine();
			this.sizes[13] = 10;
			this.available[13] = new Festival();
			this.sizes[14] = 10;
			this.available[14] = new Market();
			this.sizes[15] = 10;
			this.available[15] = new Council_Room();
		}
		
		public void play() {
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
			int winner = 0;
			int winnerScore = this.group[0].score();
			int index = 1;
			while (index < num_players)
			{
				if (this.group[index].score() > winnerScore)
				{
					winnerScore = this.group[index].score();
					winner = index;
				}
				index++;
			}
			String text = "Player " + index + " wins!";
			System.out.println(text);
		}
	
		//checks if any of the end conditions of the game are over/
		//there are two:
		//    there are no provinces left to buy,
		//    there are three different cards for which there are none left to buy.
		private boolean notOver()
		{
			if (sizes[5] < 1)
			{
				return false;
			}
			int i = 0;
			int out = 0;
			while (i < num_cards)
			{
				if (sizes[i] < 1)
				{
					out++;
				}
				i++;
			}
			return (out < 3);
		}
	
		//prints all cards available to buy
		public void printAvailable()
		{
			int i = 0;
			while (i < this.num_cards)
			{
				if (sizes[i] > 0)
				{
					String text = i + ". " + available[i].name + "(" + available[i].cost + "): " + sizes[i];
					System.out.println(text);
				}
				i++;
			}
		}
	
		public abstract class Card {
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
			public abstract void play(Player person);
		}	
	
		public class Copper extends Card{
			public Copper()
			{
				super();
				this.treasure = 1;
				this.name = "Copper";
			}
			
			public void play(Player Person)
			{
				System.out.println("Copper is not an action");
			}
		}	
	
		public class Silver extends Card{
			public Silver()
			{
				super();
				this.treasure = 2;
				this.name = "Silver";
				this.cost = 3;
			}
			
			public void play(Player Person)
			{
				System.out.println("Silver is not an action");
			}
		}	
	
		public class Gold extends Card{
			public Gold()
			{
				super();
				this.treasure = 3;
				this.name = "Gold";
				this.cost = 6;
			}
			
			public void play(Player Person)
			{
				System.out.println("Gold is not an action");
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

		public class Woodcutter extends Card{
			public Woodcutter()
			{
				super();
				this.isAction = true;
				this.name = "Woodcutter";
				this.cost = 3;
			}
		
			public void play(Player person)
			{
				System.out.println("Pick a card costing up to 4? (Type index, 0-based, invalid indices will lead to no card being gained)");
				Instance.this.printAvailable();
				Scanner in = new Scanner(System.in);
				int index = in.nextInt();
				in.nextLine();
				if (index > -1 && index < 16)
				{
					if (Instance.this.available[index].cost <= 4 && Instance.this.sizes[index]>0)
					{
						person.discard.add(Instance.this.available[index]);
						Instance.this.sizes[index]--;
					}
				}
			}
		}

		public class Smithy extends Card{
			public Smithy()
			{
				super();
				this.isAction = true;
				this.name = "Smithy";
				this.cost = 4;
			}
		
			public void play(Player person)
			{
				person.draw();
				person.draw();
				person.draw();
			}
		}

		public class Throne_Room extends Card{
			public Throne_Room()
			{
				super();
				this.isAction = true;
				this.name = "Throne Room";
				this.cost = 4;
			}
		
			public void play(Player person)
			{
				person.printHand();
				System.out.println("Which action would you like to double play? (Type index, 0-based, invalid indices will lead to no action being played)");
				Scanner in = new Scanner(System.in);
				int index = in.nextInt();
				in.nextLine();
				if (index >= 0 && index < person.hand.size())
				{
					Card played = person.hand.get(index);
					person.play.add(played);
					person.hand.remove(index);
					played.play(person);
					played.play(person);
				}
			}
		}

		public class Moneylender extends Card{
			public Moneylender()
			{
				super();
				this.isAction = true;
				this.name = "Moneylender";
				this.cost = 4;
			}
		
			public void play(Player person)
			{
				person.printHand();
				System.out.println("Which copper would you like to trash? (Type index, 0-based, invalid indices will lead to no copper being trashed)");
				Scanner in = new Scanner(System.in);
				int index = in.nextInt();
				in.nextLine();
				if (index >= 0 && index < person.hand.size())
				{
					if (person.hand.get(index).name == "Copper")
					{
						person.hand.remove(index);
						person.gold += 3;
					}
				}
			}
		}

		public class Library extends Card{
			public Library()
			{
				super();
				this.isAction = true;
				this.name = "Library";
				this.cost = 5;
			}
		
			public void play(Player person)
			{
				Scanner in = new Scanner(System.in);
				while (person.hand.size() < 7)
				{
					if (person.deck.isEmpty())
					{
						if (person.discard.isEmpty())
						{
							break;
						}
						person.addDiscard();
					}
					else
					{
						Card revealed = person.deck.get(0);
						person.deck.remove(0);
						if (revealed.isAction)
						{
							String text = "Do you want to draw a " + revealed.name + "(Y/N)";
							System.out.println(text);
							String answer = in.nextLine();
							if (answer.equals("Y"))
							{
								person.hand.add(revealed);
							}
							else
							{
								person.discard.add(revealed);
							}
						}
						else
						{
							person.hand.add(revealed);
						}
					}
				}
			}
		}

		public class Market extends Card{
			public Market()
			{
				super();
				this.isAction = true;
				this.name = "Market";
				this.cost = 5;
			}
		
			public void play(Player person)
			{
				person.draw();
				person.actions += 1;
				person.gold++;
				person.buys++;
			}
		}

		public class Festival extends Card{
			public Festival()
			{
				super();
				this.isAction = true;
				this.name = "Festival";
				this.cost = 5;
			}
		
			public void play(Player person)
			{
				person.actions += 2;
				person.gold += 2;
				person.buys++;
			}
		}

		public class Council_Room extends Card{
			public Council_Room()
			{
				super();
				this.isAction = true;
				this.name = "Council Room";
				this.cost = 5;
			}
		
			public void play(Player person)
			{
				person.draw();
				person.draw();
				person.draw();
				person.draw();
				person.buys++;
				int i = 0;
				while (i < Instance.this.num_players)
				{
					if (i != person.id)
					{
						Instance.this.group[i].draw();
					}
					i++;
				}
			}
		}

		public class Mine extends Card{
			public Mine()
			{
				super();
				this.isAction = true;
				this.name = "Mine";
				this.cost = 5;
			}
		
			public void play(Player person)
			{
				person.printHand();
				System.out.println("Which treasure would you like to upgrade? (Type index, 0-based, invalid indices will lead to no treasure being upgraded)");
				Scanner in = new Scanner(System.in);
				int index = in.nextInt();
				in.nextLine();
				if (index >= 0 && index < person.hand.size())
				{
					if (person.hand.get(index).name == "Copper")
					{
						person.hand.remove(index);
						person.hand.add(new Silver());
						Instance.this.sizes[1]--;
					}
					else if (person.hand.get(index).name == "Silver")
					{
						person.hand.remove(index);
						person.hand.add(new Gold());
						Instance.this.sizes[2]--;
					}
					else if (person.hand.get(index).name == "Gold")
					{
						Instance.this.sizes[2]--;
					}
				}
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
			
			public void play(Player Person)
			{
				System.out.println("Estate is not an action");
			}
		}

		public class Duchy extends Card{
			public Duchy()
			{
				super();
				this.points = 3;
				this.name = "Duchy";
				this.cost = 5;
			}
			
			public void play(Player Person)
			{
				System.out.println("Duchy is not an action");
			}
		}

		public class Province extends Card{
			public Province()
			{
				super();
				this.points = 6;
				this.name = "Province";
				this.cost = 8;
			}
			
			public void play(Player Person)
			{
				System.out.println("Province is not an action");
			}
		}
	
		public class Player {
			private ArrayList<Card> hand;
			private ArrayList<Card> deck;
			private ArrayList<Card> discard;
			private ArrayList<Card> play;
			public int actions;
			public int gold;
			public int buys;
			public int id;
		
			public Player()
			{	
				this.id = -1;
				this.hand = new ArrayList(5);
				this.play = new ArrayList(5);
				this.deck = new ArrayList(10);
				this.discard = new ArrayList(10);
				this.discard.add(new Copper());
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
				this.draw();
				this.draw();
				this.draw();
				this.draw();
				this.draw();
			}
						
			public Player(int i)
			{
				this();
				this.id = i;
			}
			
			public int score()
			{
				int score = 0;
				int i = 0;
				while (i < this.hand.size())
				{
					score += this.hand.get(i).points;
					i++;
				}
				i = 0;
				while (i < this.discard.size())
				{
					score += this.discard.get(i).points;
					i++;
				}
				i = 0;
				while (i < this.deck.size())
				{
					score += this.deck.get(i).points;
					i++;
				}
				return score;
			}

			//Shuffles the discard and adds it to the bottom of the deck.
			//most commonly used when attempting to draw when there are no cards in the deck.
			private void addDiscard()
			{
				Random shuffle = new Random();
				while (!this.discard.isEmpty())
				{
					int i = shuffle.nextInt(this.discard.size());
					{
						this.deck.add(this.discard.get(i));
						this.discard.remove(i);
					}
				}
			}
		
			private void discardHand()
			{
				int i = 0;
				while (!this.hand.isEmpty())
				{
					this.discard.add(this.hand.get(0));
					this.hand.remove(0);
				}
			}
		
			//add the top card from the deck to the hand
			//if there are no cards in the deck, add the discard to it before drawing
			public void draw()
			{
				if (this.deck.isEmpty())
				{
					if (!this.discard.isEmpty())
					{
						this.addDiscard();
						this.draw();
					}
				}
				else
				{
					this.hand.add(deck.get(0));
					this.deck.remove(0);
				}
			}
		
			//prints out the cards in the hand
			private void printHand()
			{
				System.out.println("Your Hand:");
				int i = 0;
				while (i < this.hand.size())
				{
					String text = i+". "+this.hand.get(i).name;
					System.out.println(text);
					i++;
				}
			}
		
			public void play()
			{
				System.out.println("Starting turn for player " + this.id);
				this.actions = 1;
				buys = 1;
				this.gold = 0;
				String answer;
				int index;
				Scanner in = new Scanner(System.in);
				while (actions > 0) //a player may play 1 action card on their turn, but some actions allow you to play more
				{
					this.printHand();
					String text = "Which action would you like to play? (Type index, 0-based, -1 to not play)";
					if (this.actions > 1)
					{
						text = "You have " + this.actions + " actions." + text;
					}
					System.out.println(text);
					index = in.nextInt();
					in.nextLine();
					if (index >= 0 && index < this.hand.size())
					{
						Card played = this.hand.get(index);
						this.play.add(played);
						this.hand.remove(index);
						played.play(this);
						actions--;
					}
					else if (index == -1)
					{
						this.actions = 0;
					}
					else
					{
						System.out.println("That is outside the range of the hand");
					}
				}
				index = 0;
				while (index < this.hand.size())
				{
					this.gold += this.hand.get(index).treasure;
					index++;
				}
				Instance.this.printAvailable();
				while (buys > 0) //a player may buy 1 card, but some actions allow them to buy more
				{
					String text = "You have " + this.gold + " money";
					if (this.buys > 1)
					{
						text += " and " + this.buys + " buys.";
					}
					System.out.println(text);
					System.out.println("Which card would you like to buy? (Type index, 0-based, -1 to not buy anything)");
					index = in.nextInt();
					in.nextLine();
					if (index > -1 && index < 16)
					{
						if (Instance.this.available[index].cost <= gold && Instance.this.sizes[index]>0)
						{
							this.discard.add(Instance.this.available[index]);
							this.gold -= Instance.this.available[index].cost;
							buys--;
							Instance.this.sizes[index]--;
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
				this.discardHand();
				this.draw();
				this.draw();
				this.draw();
				this.draw();
				this.draw();
				this.discard.addAll(this.play);
				this.play.clear();
			}
		}
	}
}


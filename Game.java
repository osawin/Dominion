public class Game{
    public static void main(String[] args) {
        
    }
}

public class card {
	public boolean isAction();
	public int treasure();
	public String name();
	public void play(player person);
}

public class player {
	private card[] hand;
	private card[] deck;
	private card[] discard;
	private card[] 
	public static void play()
	{
		int action = 1;
		int gold = 0;
		printHand();
		String answer;
		int index;
		Scanner in = new Scanner(System.in);
		while (action > 1)
		{
			System.out.println("Would you like to play an action? Y/N");
			answer = in.nextLine();
			if (answer == "Y")
			{
				printHand();
				System.out.println("Which action would you like to play? (Type index, 0-based)");
				index = in.nextInt();
				if (index >= 0 && index < hand.length)
				{
					hand[index].play(this);
					action--;
				}
				else
				{
					action = 0;
				}
			}
			else
			{
				action = 0;
			}
		}
		index = 0;
		while (index < hand.length)
		{
			gold += hand[index].treasure();
		}
	}
}
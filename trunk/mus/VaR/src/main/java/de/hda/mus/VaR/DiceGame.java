package de.hda.mus.VaR;

public class DiceGame {
	
	private int[] currentGame;
	private int numOfExperiments;
	private int numOfDice;
	
	public DiceGame (){
		
	}
	
	private int tossDice(){
		return (int) ((Math.random()*6)+1);
	}
	
	public void play(int numExperiments, int numDice) {
		numOfExperiments = numExperiments;
		numOfDice = numDice;
		currentGame = new int[numDice*6];
		
		for(int i=0;i<currentGame.length;i++){
			currentGame[i] = 0;
		}
		
		for(int i=0;i<numExperiments;i++){
			int pips = 0;
			for(int j=0;j<numDice;j++){
				pips += tossDice();
			}
			currentGame[pips-1] += 1;
		}
		
	}
	
	public static void main(String[] args) {
		DiceGame game = new DiceGame();
		
		
		//Experiment 500, 1.000 und 10.000
		//a) Setzen Sie f�r das W�rfelspiel f�r jedes Experiment einen W�rfel ein.
		game.play(500, 1);
		System.out.println(game);
		
		game.play(1000, 1);
		System.out.println(game);
		
		game.play(10000, 1);
		System.out.println(game);
		//b) Setzen Sie f�r das W�rfelspiel f�r jedes Experiment zwei W�rfel ein.
		game.play(500, 2);
		System.out.println(game);
		
		game.play(1000, 2);
		System.out.println(game);
		
		game.play(10000, 2);
		System.out.println(game);
		//c) Setzen Sie f�r das W�rfelspiel f�r jedes Experiment zehn W�rfel ein.
		game.play(500, 10);
		System.out.println(game);
		
		game.play(1000, 10);
		System.out.println(game);
		
		game.play(10000, 10);
		System.out.println(game);
		

	}

	public String toString(){
		StringBuffer display = new StringBuffer();
		display.append("------"+numOfExperiments+"/"+numOfDice+"---------\n");
		for(int i=1;i<=currentGame.length;i++){
			if(i<numOfDice)
				continue;
			display.append(i+": "+currentGame[i-1]+ "\n");
		}
		display.append("----------------------\n");
		return display.toString();
	}
}

package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    private StringBuilder sb = new StringBuilder();

	public String getOutput() {
		return sb.toString();
	}

	int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		
		
	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;

		sb.append("\n");
		sb.append(playerName + " was added");
		sb.append("\n");
		sb.append("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		sb.append("\n");
		sb.append(players.get(currentPlayer) + " is the current player");

		sb.append("\n");
		sb.append("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

				sb.append("\n");
				sb.append(players.get(currentPlayer) + " is getting out of the penalty box");
				places[currentPlayer] = places[currentPlayer] + roll;
				if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

				sb.append("\n");
				sb.append(players.get(currentPlayer)
					+ "'s new location is "
					+ places[currentPlayer]);
				sb.append("\n");
				sb.append("The category is " + currentCategory());

				askQuestion();
			} else {
				sb.append("\n");
				sb.append(players.get(currentPlayer) + " is not getting out of the penalty box");

				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			places[currentPlayer] = places[currentPlayer] + roll;
			if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

			sb.append("\n");
			sb.append(players.get(currentPlayer)
				+ "'s new location is "
				+ places[currentPlayer]);
			sb.append("\n");
			sb.append("The category is " + currentCategory());

			askQuestion();
		}
		
	}

	private void askQuestion() {
		if (currentCategory() == "Pop") {
			sb.append("\n");
			sb.append(popQuestions.removeFirst());
		}
		if (currentCategory() == "Science") {
			sb.append("\n");
			sb.append(scienceQuestions.removeFirst());
		}
		if (currentCategory() == "Sports") {
			sb.append("\n");
			sb.append(sportsQuestions.removeFirst());
		}
		if (currentCategory() == "Rock") {
			sb.append("\n");
			sb.append(rockQuestions.removeFirst());
		}
	}
	
	
	private String currentCategory() {
		if (places[currentPlayer] == 0) return "Pop";
		if (places[currentPlayer] == 4) return "Pop";
		if (places[currentPlayer] == 8) return "Pop";
		if (places[currentPlayer] == 1) return "Science";
		if (places[currentPlayer] == 5) return "Science";
		if (places[currentPlayer] == 9) return "Science";
		if (places[currentPlayer] == 2) return "Sports";
		if (places[currentPlayer] == 6) return "Sports";
		if (places[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				sb.append("\n");
				sb.append("Answer was correct!!!!");

				purses[currentPlayer]++;
				sb.append("\n");
				sb.append(players.get(currentPlayer)
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");
				
				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				
				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {

			sb.append("\n");
			sb.append("Answer was corrent!!!!");
			purses[currentPlayer]++;
			sb.append("\n");
			sb.append(players.get(currentPlayer)
				+ " now has "
				+ purses[currentPlayer]
				+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		sb.append("\n");
		sb.append("Question was incorrectly answered");

		sb.append("\n");
		sb.append(players.get(currentPlayer) + " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}

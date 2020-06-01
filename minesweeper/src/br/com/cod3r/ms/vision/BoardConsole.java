package br.com.cod3r.ms.vision;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.cod3r.ms.exception.DetonationException;
import br.com.cod3r.ms.exception.ExceptionExit;
import br.com.cod3r.ms.model.Board;

public class BoardConsole {
	
	private Board board;
	private Scanner input = new Scanner(System.in);
	
	public BoardConsole(Board board) {
		this.board = board;
	
	runGame();
	}

	private void runGame() {
		try {
			boolean goOn = true;
			while(goOn) {
				gameCycle();
				
				System.out.println("Another game? (S/n) ");
				String answer = input.nextLine();
				
				if("n".equalsIgnoreCase(answer)) {
					goOn = false;
				}else {
					board.restart();
				}
			}
		} catch(ExceptionExit e ) {
			System.out.println("bye");
		}finally {
			input.close();
		}
	}

	private void gameCycle() {
		try {
			
			while(!board.targetFinal()) {
				
				System.out.println(board);
				
				String typed = capturingType("Type lines and columns (x, y): ");
				
				Iterator<Integer> xy = Arrays.stream(typed.split(","))
				.map(e -> Integer.parseInt(e.trim())).iterator();
				
				typed = capturingType("1 - open or 2 - (off) mark");
				
				if("1".contentEquals(typed)) {
					board.open(xy.next(), xy.next());
				}else if("2".contentEquals(typed)) {
					board.switchMarking(xy.next(), xy.next());
				}
			}
			System.out.println(board);
			System.out.println("Congratulations");
		}catch(DetonationException e) {
			System.out.println(board);
			System.out.println("Game Over");
		}
	}
	private String capturingType( String text) {
		System.out.print(text);
		String typed = input.nextLine();
		
		if("get out".equalsIgnoreCase(typed)){
			throw new ExceptionExit();		
			}
		return typed;
	}
	
	
}

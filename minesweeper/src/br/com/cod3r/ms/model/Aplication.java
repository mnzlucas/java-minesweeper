package br.com.cod3r.ms.model;

import br.com.cod3r.ms.vision.BoardConsole;

public class Aplication {
	
	public static void main(String[] args) {
		
		Board board = new Board(6, 6, 2);
				
		new BoardConsole(board);
		}

}

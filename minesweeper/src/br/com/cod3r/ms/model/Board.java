package br.com.cod3r.ms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.cod3r.ms.exception.DetonationException;
public class Board {

	private int lines;
	private int columns;
	private int mines;
	
	private final List<Field> fields = new ArrayList<>();

	public Board(int lines, int columns, int mines) {
		this.lines = lines;
		this.columns = columns;
		this.mines = mines;
	
		createFields();
		associateNeighbors();
		draftMines();
	   }
	public void open(int line, int column ) {
		try {
			fields.parallelStream()
		      .filter(c -> c.getxAxis() == line && c.getyAxis() == column)
		      .findFirst()
		      .ifPresent( c -> c.open());
			}catch (DetonationException e) {
				fields.forEach(c -> c.setOpen(true));
				throw e;
			}
	}
	
	public void switchMarking(int line, int column ) {
		fields.parallelStream()
		.filter(c -> c.getxAxis() == line && c.getyAxis() == column)
		.findFirst()
		.ifPresent( c -> c.switchMarking());
	}
	
	
	
	private void createFields() {
		for (int l = 0; l < lines; l++) {
			for (int c = 0; c < columns; c++) {
					fields.add(new Field(l, c));
			}
			
		}
		
	}
	
	private void associateNeighbors() {
		for(Field f1: fields) {
			for(Field f2: fields) {
				f1.addNeighbors(f2);
			}
		}
	}
	private void draftMines() {
		long armedMines = 0;
		
		Predicate<Field> mine = f -> f.isMined();
		
		do {
			int aleatory =(int) (Math.random() * fields.size());
			fields.get(aleatory).undermine();
			armedMines = fields.stream().filter(mine).count();
		}while(armedMines < mines);
		
	}
	public boolean targetFinal() {
		return fields.stream().allMatch(f -> f.targetFinal()); 
	}
	public void restart() {
		fields.stream().forEach(f -> f.restart());
		draftMines();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("  ");
		for (int c = 0; c < columns; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		
		sb.append("\n");
		
		int i = 0;
		
		for (int l = 0; l < lines ; l++) {
			sb.append(l);
			sb.append(" ");
		for (int c = 0; c < columns ; c++) {
			sb.append(" ");
			sb.append(fields.get(i));
			sb.append(" ");
			i++;
		}
		sb.append("\n");
		
	}
		return sb.toString();
   }

}

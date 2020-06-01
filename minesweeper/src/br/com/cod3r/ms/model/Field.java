package br.com.cod3r.ms.model;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.ms.exception.DetonationException;

public class Field {
	
	private final int xAxis; //line
	private final int yAxis; //column
	
	
	private boolean open = false;
	private boolean mine = false;
	private boolean marked = false;
	
	private List<Field> neighbors = new ArrayList<>();
	
	Field(int xAxis , int yAxis){
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		
	}
	boolean addNeighbors(Field neighbor) {
		boolean diferentxAxis = xAxis != neighbor.xAxis;
		boolean diferentyAxis = yAxis != neighbor.yAxis;
		
		boolean diagonal = diferentxAxis && diferentyAxis;
		
		int deltaX = Math.abs(xAxis - neighbor.xAxis);
		int deltaY = Math.abs(yAxis - neighbor.yAxis);
		int deltaG = deltaX + deltaY;
		
		if(deltaG == 1 && !diagonal) {
			neighbors.add(neighbor);	
			return true;
		}else if(deltaG == 2 && diagonal) {
			neighbors.add(neighbor);
			return true;
		}else {
			return false;
		}
	}
	void switchMarking() {
		if(!open) {
			marked = !marked;
		}
	}
	
	boolean open() {
		if(!open && !marked) {
			open = true;
			if(mine) {
				throw new DetonationException();
			}
			if(safeNeighbors()) {
				neighbors.forEach( v -> v.open());
			}
			return true;
		}else {
			return false;
			
		}
	}
	
	boolean safeNeighbors() {
		return neighbors.stream().noneMatch(v -> v.mine);
	}
	public boolean isMined() {
		return mine;
	}
	
	public boolean isMarked() {
		return marked;
	}
	void undermine() {
		mine = true;
		
	}
	
	 void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isOpen() {
		return open;
	}
	public boolean isClosed() {
		return !isOpen();
	}
	public int getxAxis() {
		return xAxis;
	}
	public int getyAxis() {
		return yAxis;
	}
	
	 boolean targetFinal() {
		boolean unveil = !mine && open;
		boolean secure = mine && marked;
		return unveil || secure;
	}
	long minesInNeighborhood() {
		return neighbors.stream().filter(v -> v.mine).count();
		}
	void restart() {
		open = false;
		mine = false;
		marked = false;
	}
	public String toString() {
		if(marked) {
			return "x";
		}else if(open && mine) {
			return "*";
		}else if(open && minesInNeighborhood()> 0) {
			return Long.toString(minesInNeighborhood());
		}else if(open) {
			return " ";
		}else {
			return "?";
		}
	}
}

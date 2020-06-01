package br.com.cod3r.ms.model;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.ms.exception.DetonationException;

public class TestField {

	private Field field; 
			
	@BeforeEach
    void startField() {
       field = new Field(3, 3);
}
	
	
	@Test
	void testNeighbor1() {
		Field neighbor = new Field(3,2);
		boolean result = field.addNeighbors(neighbor);
		assertTrue(result);
		
	}
	@Test
	void testNeighbor2() {
		Field neighbor = new Field(3,4);
		boolean result = field.addNeighbors(neighbor);
		assertTrue(result);
		
	}
	@Test
	void testNeighbor3() {
		Field neighbor = new Field(2,3);
		boolean result = field.addNeighbors(neighbor);
		assertTrue(result);
		
	}
	@Test
	void testNeighbor4() {
		Field neighbor = new Field(4,3);
		boolean result = field.addNeighbors(neighbor);
		assertTrue(result);
		
	}
	@Test
	void testNeighborDiag() {
		Field neighbor = new Field(2,2);
		boolean result = field.addNeighbors(neighbor);
		assertTrue(result);
		
	}
	@Test
	void testNotNeighbor() {
		Field neighbor = new Field(1,1);
		boolean result = field.addNeighbors(neighbor);
		assertFalse(result);
		
	}
	@Test
	void defaultTest() {
		assertFalse(field.isMarked());
	}
	@Test
	void switchMarkedtest() {
		field.switchMarking();
		assertTrue(field.isMarked());
	}
	@Test
		void switchMarkedtestTwice() {
			field.switchMarking();
			field.switchMarking();
			assertFalse(field.isMarked());
	}
	@Test
	void testOpenNotMinedNotMarked() {
		
		assertTrue(field.open());
	}
	@Test
	void testOpenNotMinedMarked() {
		field.switchMarking();
		assertFalse(field.open());
	}
	@Test
	void testOpenMinedMarked() {
		field.switchMarking();
		field.undermine();
		assertFalse(field.open());
	}
	@Test
	void testOpenMinedNotMarked() {
		field.undermine();
		assertThrows(DetonationException.class, () ->{
		field.open();
		});
	}
	@Test
	void testOpenWithNeighbors1() {
			
			Field field11 = new Field(1,1);
			Field field22 = new Field(2,2);
			
			field22.addNeighbors(field11);
			
			field.addNeighbors(field22);
			field.open();
			
			assertTrue(field22.isOpen() && field11.isOpen());
	}
	@Test
	void testOpenWithNeighbors2() {
		
		Field field11 = new Field(1,1);
		Field field12 = new Field(1,2);
		field12.undermine();
		
		
		Field field22 = new Field(2,2);
		field22.addNeighbors(field11);
		field22.addNeighbors(field12);
		
		
		field.addNeighbors(field22);
		field.open();
		
		assertTrue(field22.isOpen() && field11.isClosed());
	}
	
}

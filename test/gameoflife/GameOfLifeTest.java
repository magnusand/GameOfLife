/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.awt.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Magnus Andersen, Vebjørn Grønhaug
 */
public class GameOfLifeTest {
    
    public GameOfLifeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of start method, of class GameOfLife.
     */
    @Test
    public void testSetAllCellsDead() {

        // Setter opp testen
        GameOfLife instance = new GameOfLife();

        Cell[][] cells = new Cell[2][2];
        
        cells[0][0] = new Cell(new Point(), 0, 0);
        cells[1][0] = new Cell(new Point(), 1, 0);
        cells[0][1] = new Cell(new Point(), 0, 1);
        cells[1][1] = new Cell(new Point(), 1, 1);
        
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j].setAlive(true);
            }
        }

        // Sjekker at testen er satt opp riktig
        assertTrue(cells[0][0].alive);
        assertTrue(cells[0][1].alive);
        assertTrue(cells[1][0].alive);
        assertTrue(cells[1][1].alive);

        // Tilordner cellene til game of life instansen
        instance.cells = cells;
        instance.setAllCellsDead();
        
        // Sjekker at setAllCellsDead gjør det den skal
        assertFalse(instance.cells[0][0].alive);
        assertFalse(instance.cells[0][1].alive);
        assertFalse(instance.cells[1][0].alive);
        assertFalse(instance.cells[1][1].alive);
    }

   
}

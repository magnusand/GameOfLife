/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 
 * Vebjørn had the responsibillity for making the test.
 *
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
 * The Game Of Life, GameOfLifeTest class
 * 
 * @author Magnus Andersen, Vebjørn Grønhaug
 * @version 1.0
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
     * Test of SetAllCellsDead method, of class GameOfLife.
     */
    @Test
    public void testSetAllCellsDead() {

        /**
         * Sets up an instance of game of life
         */
        GameOfLife instance = new GameOfLife();
        
        Cell[][] cells = new Cell[2][2];
        
        /**
         * sets a point with coordinates to each cell in the instance
         */
        cells[0][0] = new Cell(new Point(), 0, 0);
        cells[1][0] = new Cell(new Point(), 1, 0);
        cells[0][1] = new Cell(new Point(), 0, 1);
        cells[1][1] = new Cell(new Point(), 1, 1);
        
        /**
         * loops through the board and sets all cells alive state
         */
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j].setAlive(true);
            }
        }
        
        /**
         * checks that all cells actually is at alive state
         */
        assertTrue(cells[0][0].alive);
        assertTrue(cells[0][1].alive);
        assertTrue(cells[1][0].alive);
        assertTrue(cells[1][1].alive);

        /**
         * Assigns cells to the game of life instance
         * and sets all cells in the instance to dead with
         * the setAllCellsDead method
         */
        instance.cells = cells;
        instance.setAllCellsDead();
        
        /**
         * Finally check that setAllCellsDead method does 
         * what it's supposed to do
         */
        assertFalse(instance.cells[0][0].alive);
        assertFalse(instance.cells[0][1].alive);
        assertFalse(instance.cells[1][0].alive);
        assertFalse(instance.cells[1][1].alive);
    }
}

package gameoflife;

import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The Game Of Life, Cell class
 *
 * @author Magnus Andersen, Vebjørn Grønhaug
 * @version 1.0
 */

public class Cell {
    /**
     * @cellWidth decides how wide each cell is in pixels
     */
    static int cellWidth = 10;
    
    /**
     * @cellHeight decides how tall each cell is in pixels
     */
    static int cellHeight = 10;
    
    // Position er x og y kordinat på skjerm
    
    /**
     * position is x and y's coordinate in the 2d array
     */
    private Point position;
    private int x;
    private int y;
    /**
     * a positions state(alive or dead)
     */
    boolean alive;
    
    /**
     * aliveNeighboursCount is a int with number of living neighbours to a cell/position
     */
    int aliveNeighboursCount = 0;
    /**
     * 
     * @param position
     * @param x is the coordinate on the x-axis
     * @param y is the coordinate on the y-axis
     * 
     * sets a cells position on the screen and in the array
     * boolean alive starts as false
     */
    Cell(Point position,int x,int y){
        this.position = position;
        this.x = x;
        this.y = y;
        alive = false;
    }
    
    /**
     * 
     * @param x is the coordinate on the x-axis
     * @param y is the coordinate on the y-axis
     * @return returns the result of the cell interference
     * 
     * boolean click is set to false
     * 
     * checks if x is greater than or equal to the start position on the x-axis
     * and if x is less than or equal to the start position on the x-axis
     * 
     * if these requirements are met it continues with the nested if statement
     * 
     * checks if y is greater than or equal to the start position on the y-axis
     * and if y is less than or equal to the start position on the y-axis
     * 
     * if requirements on each of the nested if statements are met
     * boolean click is set to true
     * if the cell that is clicked already is alive, it will be set to dead
     * and if the cell that is clicked is dead, it will be set to alive
     * 
     * Magnus had the responsibillity to make the animation, included the clicked function
     * countLivingCells and changeState
     * 
     */
    boolean clicked(int x, int y){
        boolean click = false;
        
        if(x >= position.getX() && x <= position.getX()+cellWidth){
            if(y >= position.getY() && y <= position.getY()+cellHeight){
                click = true;
                if(alive) alive = false;
                else alive = true;
            }
        }
        return click;
    }
    /**
     * @param a is a boolean that is equal to the alive boolean
     */
    public void setAlive(boolean a){
        alive = a;
    }
    
    /**
     * 
     * @return returns a cells state(dead or alive)
     */
    public boolean getAlive(){
        return alive;
    }
    
    /**
     * this method counts how many neighbours of an living cell that is alive as well
     * @param cells is the 2d array with cells 
     * @param gol makes it possible to count neighbours to the end of x and y's axis
     * aliveNeighboursCount is set to 0
     * checks if surrounding neighbours is dead or alive and 
     * adds 1 to aliveNeighboursCount for each living neighbour
     */
    void countLivingCells(Cell[][] cells, GameOfLife gol){
   
      aliveNeighboursCount = 0;
        
        if(y!=0 && x!=0){
            if(cells[y-1][x-1].getAlive()) aliveNeighboursCount++;
        }
        
        if(y!=0){
            if(cells[y-1][x].getAlive()) aliveNeighboursCount++;
        }
        
        if(y !=0 && x != gol.numberOfCellsOnXAxis-1){
            if(cells[y-1][x+1].getAlive()) aliveNeighboursCount++;
        }
        
        if(x!=0){
            if(cells[y][x-1].getAlive()) aliveNeighboursCount++;
        }
        
        if(x != gol.numberOfCellsOnXAxis-1){
         if(cells[y][x+1].getAlive()) aliveNeighboursCount++;
        }
        
        if(x !=0 && y != gol.numberOfCellsOnYAxis-1){
            if(cells[y+1][x-1].getAlive()) aliveNeighboursCount++;
        }
        if(y != gol.numberOfCellsOnYAxis-1){
         if(cells[y+1][x].getAlive()) aliveNeighboursCount++;
        }
        if(y != gol.numberOfCellsOnYAxis-1 && x != gol.numberOfCellsOnXAxis-1){
         if(cells[y+1][x+1].getAlive()) aliveNeighboursCount++;
        }     
    }
    
    /**
     * changes the state of a cell between alive and dead
     * dependent on that number of aliveNeighboursCount and 
     * the state of the cell itself
     */
    void changeState(){
        
        if(!alive && aliveNeighboursCount == 3){
            alive = true;
        
        }else if(alive && (aliveNeighboursCount == 2 || aliveNeighboursCount == 3)){
            alive = true;
        
        }else{
            alive = false;
        }
    }
   
    
    /**
     * 
     * @param graphicsContext makes it possible to draw on the board
     * by getting a interferenced cells position it
     * fills the living cells with the color black 
     * and the dead cell with white
     */
    public void render(GraphicsContext graphicsContext){
        if(alive){
            graphicsContext.setFill(Color.BLACK);
        }
        else{
            graphicsContext.setFill(Color.WHITE);
        }
        graphicsContext.fillRect(position.getX(), position.getY(), cellWidth, cellHeight);
    }
       void countLivingCells(Cell[][] cells) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }
}
    
    
 
  
  
 
  
 
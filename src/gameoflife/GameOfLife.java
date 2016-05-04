package gameoflife;

import java.awt.Point;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import static javafx.application.Application.launch;

/**
 * The Game of Life
 * 
 * @author Magnus Andersen, Vebjørn Grønhaug
 * @version 1.0
 */
public class GameOfLife extends Application {
    
     /**
     * graphicsContext is an objet of the GraphicsContext class
     */
    GraphicsContext graphicContext;
    
    /**
     * startPause is the button that start and stops the game
     */
    Button playPause;
    
    /**
     * input is the button that alows the user to load files
     */
    Button input;
    
    /**
     * reset is the button that resets the grid
     */
    Button reset;
     
    
    /**
     * startTime sets the game speed
     */
    long startTime;
    
    /**
     * boardWidth sets the width to our grid
     */
    static int boardWidth = 600;
    
    /**
     * boardHeight sets the height to our grid 
     */
    static int boardHeight = 600;
    
    /**
     * numberOfCellsOnXAxis decides the size of the cell on the X axis
     */
    final int numberOfCellsOnXAxis = boardWidth/(Cell.cellWidth+2);
    
    /**
     * numberOfCellsOnYAxis decides the size of the cell on the Y axis
     */
    final int numberOfCellsOnYAxis = boardHeight/(Cell.cellHeight+2);
    
    /**
     * cells is a 2D array that creates the grid
     */
    Cell[][] cells = new Cell[numberOfCellsOnYAxis][numberOfCellsOnXAxis];
    
    /**
     * running is a boolean thats determines the running state of the game
     */
    boolean running = false;
    
    /**
     * @param primaryStage is the stage that surroundes the game
     */
    @Override
    public void start(Stage primaryStage){
        Canvas canvas = new Canvas(boardWidth,boardHeight);
        playPause = new Button("Play");
        input = new Button("Select files");
        reset = new Button("Reset");
        
        /**
         * decides what playPause button does when clicked and makes button change between play and pause mode
         * Switch statement checks the state of the button to decide what to do
         */
        playPause.setOnAction((ActionEvent e) -> {
            switch (playPause.getText()) {
                case "Play":
                    {
                        running = true;
                        playPause.setText("Pause");
                        break;
                    }
                case "Pause":
                    {
                        running = false;
                        playPause.setText("Play");
                        break;
                    }
            }
        });
        
        /**
         * decides what input button does when clicked
         * fileChooser makes it possible to choose file from folder, gets the filname and saves it in the inFile object
         * if inFile not like null, readFile method will be called
         */
        input.setOnAction((ActionEvent e) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select input file");
            fileChooser.setInitialFileName("in.rle");
            ExtensionFilter ef = new ExtensionFilter("Run Length Encoded files (*.rle)", "*.rle");
            fileChooser.setSelectedExtensionFilter(ef);
            File inFile = fileChooser.showOpenDialog(null);
            if(inFile != null){
                readFile(inFile.getAbsolutePath());
                
            }
        });
        
        /**
         * decides what reset button does when clicked
         * puts all cells to dead with the setAllCellsDead method
         */
        reset.setOnAction((ActionEvent e) -> {
            setAllCellsDead();
        });
        
        /**
         * creates a VBox object that takes in 4 parameters
         * @param canvas placement of canvas width table
         * @param playPause placement of button that plays and stops game
         * @param input placement of button that allows loading of pattern files
         * @param reset placement of button that resets table
         */
        
        /**
         * sets up all the screen elements inside the stage
         */
        VBox root = new VBox(4,canvas,playPause,input, reset);
        primaryStage.setTitle("Game Of Life");
        primaryStage.setScene(new Scene(root));
        graphicContext = canvas.getGraphicsContext2D();
        renderGrid();
        
        /**
         * put cells into the 2d array and makes a 2 pixel gap between each cell
         */
        int x = 0;
        int y = 0;
        
        for(int i = 0; i < cells.length;i++){
            for(int j = 0; j < cells[0].length;j++){
                 cells[i][j] = new Cell(new Point(y*(Cell.cellHeight+2),x*(Cell.cellWidth+2)),j,i);
                x++; 
                
            }
             y++;
            x = 0;
         }
        
        /**
         * makes it possible to draw by click
         */
        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent mouseEvent) -> {
            for(int i = 0; i < cells.length;i++){
                for(int j = 0; j < cells[0].length;j++){
                    if(cells[i][j].clicked((int)mouseEvent.getX(),(int)mouseEvent.getY())){
                        return;
                    }
                }
            }
        });
        
        /**
         * makes it possible to draw by click and drag mouse
         */
        canvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, (MouseEvent mouseEvent) -> {
            for(int i = 0; i < cells.length;i++){
                for(int j = 0; j < cells[0].length;j++){
                    if(cells[i][j].clicked((int)mouseEvent.getX(),(int)mouseEvent.getY())){ 
                        return;
                    }
                }
            }
        });
                
        /**
         * Makes an anamation timer that handles how fast the animation updates
         */
        startTime = System.nanoTime(); 
        new AnimationTimer(){
            public void handle(long now){
                long endTime = (long)((now-startTime)/100000000);
                if((endTime > 0.8) && running){
                    update();
                    startTime = System.nanoTime();
                }
                render();
            }
         }.start();
         primaryStage.show();
    }
    
    /**
    * setAllCellsDead method loops through the cells and sets all cells dead
    */
    public void setAllCellsDead() {
        for(int i = 0; i < cells.length;i++){
            for(int j = 0; j < cells.length;j++){
                cells[i][j].setAlive(false);
            }
        }
    }
    
    /**
     * draw the cells to board, white if its dead and black if its alive
     */
    public void render(){
        renderGrid();
        for(int i = 0; i < cells.length;i++){
            for(int j = 0; j < cells[0].length;j++){
                 cells[i][j].render(graphicContext);
            }
        }    
    }
    
    /**
     * the update method have two nested for loops. 
     * first it counts living cells in the board,
     * before it changes the state of cells dependent on the rules
     */
    void update(){
        for(int i = 0; i < cells.length;i++){
           for(int j = 0; j < cells.length;j++){
                cells[i][j].countLivingCells(cells,this);
           }
        }
        for(int i = 0;  i < cells.length;i++){
           for(int j = 0; j < cells.length;j++){
                cells[i][j].changeState();
           }
        }
    }
    
    /**
     * says how the program should read the file
     * @param fileName is the imported file
     * readFile method reads the imported .RLE file and looks for "x" and "y"
     * x and y says where the drawing will start, and follows some preset rules
     * the letter "o" is a alive cell and the letter "b" is a dead cell
     * it draws one vertical row at the time and moves one step to the right if "$" is found
     * a number in front of a letter decides how many dead or alive cells it should draw
     * when a "!" is found the program stops reading the .RLE file.
     */
    void readFile(String fileName){
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            
            try {
                String in = br.readLine();
                
                int x = 0;
                int y = 0;
                int countX = 0;
                boolean found = false;
                String num = "";
                
                while(in != null){
                    if(in.charAt(0) == 'x'){
                       String[]a = in.split(",");
                       
                       String temp = a[0];
                       temp = temp.substring(4, temp.length());
                       temp = temp.trim();
                       x = Integer.parseInt(temp);
                       temp = a[1];
                       temp = temp.substring(5, temp.length());
                       temp = temp.trim();
                       y = Integer.parseInt(temp);
                       countX = x;
                       found = true;
                    }
                    else if(found){
                        //System.out.println(in);
                        for(int i = 0; i < in.length();i++){
                            char a = in.charAt(i);
                            
                            if(a >= '0' && a <= '9'){
                                num+=a;
                                int c = i+1;
                                
                                while(in.length() > i+1){
                                   a = in.charAt(c);
                                   if(a >= '0' && a <= '9'){
                                       num+=in.charAt(c);
                                       c++;
                                       i++;
                                   }else{
                                       break;
                                   }
                                       
                                }
                            }else{
                                if(a != '$'){
                                    int ant = countX+1;
                                    
                                    if(num.length()>0){
                                        ant = Integer.parseInt(num)+countX;
                                   
                                    }
                                    
                                    if(a == '!')return;
                                    boolean alive;
                                    //if(a == '!') return;
                                    
                                    if(a == 'b') alive = false;
                                    
                                    else   alive = true;
                                    
                                    for(int j = countX; j < ant;j++){
                                        cells[y][j].setAlive(alive);
                                        countX++;
                                    }
                                    
                                    num = "";
                                }else{
                                    countX = x;
                                    y++;
                                }
                            }
                            
                        }
                    }
                    in = br.readLine();
                }
                
            } catch (IOException ex) {
                Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("done");
    }
    
    /**
     * Sets the background color which makes it look like 
     * a grid because of the empty space between the cells
     */
    void renderGrid(){
     graphicContext.setFill(Color.GREY);
     graphicContext.fillRect(0, 0, boardWidth, boardHeight);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
    
}
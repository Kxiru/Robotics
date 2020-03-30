// imports required (javafx and java)
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;
import java.util.Scanner;
import java.util.Random;

// block class
class Block {
    private String name;
    private double width, height, startX, startY;

    // constructor for block
    public Block(String name, double height, double width, double startX, double startY) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
    }
    
    // getter methods for block
    public String getName() { return this.name; }
    public double getWidth() { return this.width; }
    public double getHeight() { return this.height; }
    public double getStartX() { return this.startX; }
    public double getStartY() { return this.startY; }
}

/* The final javaFX implementation to visualise the robot map was to present it as a chart
so that the coordinates can be identified; where the blocks are added into the chart. This is
useful for the navigation of the robot (to realise where it is on the map) as well as recognition
of the exact locations of the obstacles on the map*/

public class BuildMap extends Application {

    @Override
    
    public void start(Stage primaryStage) {
        try {
            final double offset = 50;
            double mapWidth = 1800;
            double mapHeight = 1800;

            primaryStage.setTitle("ROBOT MAP");
            
            // axis required for line chart
            final NumberAxis xAxis = new NumberAxis(0, mapWidth, 10);
            final NumberAxis yAxis = new NumberAxis(0, mapHeight, 10);
            // set labels of axis
            xAxis.setLabel("X Distance (mm)");
            yAxis.setLabel("Y Distance (mm)");
            // instantiate new line chart with axis as xAxis and yAxis
            final LineChart<Number, Number> chart = new LineChart <Number,Number>(xAxis, yAxis);
            // set title of chart
            chart.setTitle("ROBOT MAP");
            
            /* array of blocks based on the actual information of the blocks that will be used
            for QR code reading */
            Block[] blocksArray = new Block[6];
            XYChart.Series[] boundaries = new XYChart.Series[6];
            
            /* loop to assign the name, dimensions, location of the blocks on the map, by calling createNewBoundary method
            and passing the map width, map height, the blocks array and its index as the parameters */
            System.out.println("------ Generation of boundaries for blocks");
            for (int i = 0; i < blocksArray.length; i++) {
                boundaries[i] = createNewBoundary(mapWidth, mapHeight, blocksArray, i);
            }
            
            // set no sorting policy (unordered by X or Y axis) to allow the line graph to be presented as a free polygon
            chart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
            Scene scene = new Scene(chart, mapWidth, mapHeight);
            // add both series to the line chart

            for (int i = 0; i < boundaries.length; i++) {
                chart.getData().add(boundaries[i]);
            }
            // show the legend of the graph
            chart.setLegendVisible(true);
            
            // specify the scene that will be used for this stage
            primaryStage.setScene(scene);
            // show the window by making it visible
            primaryStage.show();

        } catch (Exception e) {
            // catch any exceptions which occur
            e.printStackTrace();
        }
    }

    public static XYChart.Series createNewBoundary(double mapWidth, double mapHeight, Block[] blocksArray, int indexNumber) {
        // input the name, dimensions and location of the block that will be instantiated 
        String name = "";
        double width = 0;
        double height = 0;
        double startX = 0;
        double startY = 0;
        Scanner setter = new Scanner(System.in);
        Scanner nameSetter = new Scanner(System.in);

        boolean isInsideMap = false;
        XYChart.Series newBoundary = null;

        // ensure all points of the block are within the map
        // CONDITION: re-iterate the loop until all four points of the block lie inside the map
        System.out.println("------ Creating boundary for Block " + (indexNumber + 1));
        while (!isInsideMap) {
            // assigns the name of the block
            System.out.println("Set Name of block: ");
            name = nameSetter.nextLine();
            // assigns the width of the block; magnitude thus must be positive
            System.out.println("Set Width of block (mm): ");
            width = Math.abs(setter.nextDouble());
            // assigns the height of the block; magnitude thus must be positive
            System.out.println("Set Height of block (mm): ");
            height = Math.abs(setter.nextDouble());
            // assigns the X coordinate of the top-left corner of the block
            System.out.println("Set X of top-left corner of block: ");
            startX = setter.nextDouble();
            // assigns the Y coordinate of the top-left corner of the block
            System.out.println("Set Y of top-left corner of block: ");
            startY = setter.nextDouble();
            
            // logical evaluation of if all four points of the block lie inside the map
            isInsideMap = ( (startX >= 0 && startX <= mapWidth) && (startY >= 0 && startY <= mapHeight)
            && (startX + width >= 0 && startX + width <= mapWidth)
            && (startY - height >= 0 && startY - height <= mapHeight));

            if (isInsideMap) {
                Block newBlock = new Block(name, width, height, startX, startY);
                blocksArray[indexNumber]= newBlock;
                System.out.println("--- Block created");
                System.out.println("Name: " + newBlock.getName());
                System.out.println("Width (mm): " + newBlock.getWidth());
                System.out.println("Height (mm): " + newBlock.getHeight());
                System.out.println("X co-ordinate: " + newBlock.getStartX());
                System.out.println("Y co-ordinate: " + newBlock.getStartY());

                // create series for the new block and assign a name to it.
                newBoundary = new XYChart.Series();
                newBoundary.setName(newBlock.getName());

                // plot points of recentangle onto graph
                newBoundary.getData().add(new XYChart.Data(startX, startY));
                newBoundary.getData().add(new XYChart.Data(startX + width, startY));
                newBoundary.getData().add(new XYChart.Data(startX + width, startY - height));
                newBoundary.getData().add(new XYChart.Data(startX, startY - height));
                newBoundary.getData().add(new XYChart.Data(startX, startY));
            } else {
                System.out.println("---INVALID: Block lies outside of map");
            }
        }

        return newBoundary;
    }
    
    public static void main (String[] args) {
        // lauch the application
        launch(args);
    }
}

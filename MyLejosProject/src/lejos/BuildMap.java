// imports required (javafx and java)
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;
import java.util.Scanner;

import java.util.Random;

class Block {
    private String name;
    private double length, width;

    public Block(String name, double length, double width) {
        this.name = name;
        this.length = length;
        this.width = width;
    }

    public String getName() { return this.name; }
    public double getLength() { return this.length; }
    public double getWidth() { return this.width; }
}

public class BuildMap extends Application {

    @Override

    public void start(Stage primaryStage) {
        try {
            final double offset = 50;
            //double width = getDimension("Width: ");
            //double height = getDimension("Height: ");
            double width = 2000;
            double height = 2000;

            primaryStage.setTitle("ROBOT MAP");
            
            // axis required for line chart
            final NumberAxis xAxis = new NumberAxis(0, width, 10);
            final NumberAxis yAxis = new NumberAxis(0, height, 10);
            // set labels of axis
            xAxis.setLabel("X Distance (mm)");
            yAxis.setLabel("Y Distance (mm)");
            // instantiate new line chart with axis as xAxis and yAxis
            final LineChart<Number, Number> chart = new LineChart <Number,Number>(xAxis, yAxis);
            // set title of chart
            chart.setTitle("ROBOT MAP");

            // create boundary blocks
            Block bill = new Block("Bill", 71, 32);
            Block becky =  new Block("Becky", 95, 63);
            Block daquan =  new Block("Daquan", 79, 63);
            Block natalie = new Block("Natalie", 31, 31); 
            Block keisha = new Block("Keisha", 49, 31); 
            Block jamal = new Block("Jamal", 63, 31); 

            Block[] blocks = new Block[] {bill, becky, daquan, natalie, keisha, jamal};
            XYChart.Series[] boundaries = new XYChart.Series[6];
            

            System.out.println("Random placement of blocks...");
            for (int i = 0; i < blocks.length; i++) {
                boundaries[i] = createNewBoundary(blocks[i], width, height);

            }
            
            // set no sorting policy (unordered by X or Y axis) to allow the line graph to be presented as a free polygon
            chart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
            Scene scene = new Scene(chart, width, height);
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

    public static XYChart.Series createNewBoundary(Block block, double mapWidth, double mapHeight) {
        double width = block.getWidth();
        double height = block.getLength();
        //Scanner setter = new Scanner(System.in);
        System.out.println("-- Create boundary for " + block.getName() + " --");
        /*System.out.println("Set X: ");
        double startX = setter.nextDouble();
        System.out.println("Set Y: ");
        double startY = setter.nextDouble();*/

        boolean isInsideMap = false;
        double startX = 0;
        double startY = 0;

        // ensure all points of the block are within the map
        while (!isInsideMap) {
            Random Wr = new Random();
            startX = 0 + (mapWidth - 0) * Wr.nextDouble();

            Random Hr = new Random();
            startY = 0 + (mapHeight - 0) * Hr.nextDouble();

            isInsideMap = ( (startX >= 0 && startX <= mapWidth) && (startY >= 0 && startY <= mapHeight)
            && (startX + width >= 0 && startX + width <= mapWidth)
            && (startY - height >= 0 && startY - height <= mapHeight));
        }

        XYChart.Series newBoundary = new XYChart.Series();
        newBoundary.setName(block.getName());

        // plot points of recentangle onto graph
        newBoundary.getData().add(new XYChart.Data(startX, startY));
        newBoundary.getData().add(new XYChart.Data(startX + width, startY));
        newBoundary.getData().add(new XYChart.Data(startX + width, startY - height));
        newBoundary.getData().add(new XYChart.Data(startX, startY - height));
        newBoundary.getData().add(new XYChart.Data(startX, startY));

        return newBoundary;

    }

    public static double getDimension(String prompt) {
        double dimension;
        Scanner in = new Scanner(System.in);

        System.out.println(prompt);
        dimension = in.nextDouble();
        return dimension;
    }
    
    public static void main (String[] args) {
        launch(args);
    }
}
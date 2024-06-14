/*
@ASSESSME.USERID: nm4680
@ASSESSME.AUTHOR: Nadja Matkovic
@ASSESSME.LANGUAGE: JAVA
@ASSESSME.DESCRIPTION: ASS91
@ASSESSME.ANALYZE: YES
*/


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Displays data in a table format allowing the user to sort the table by the
 * data in any column by clicking on the column header.
 */
public class Filters extends Application {

    private List<String[]> data;
    private List<List<Label>> labels;

    @Override
    public void start (Stage stage) throws Exception {
        // The filename will be passed through as a command line parameter
        List<String> args = getParameters ().getRaw ();
        FileReader file = new FileReader (args.get (0));
        BufferedReader fin = new BufferedReader (file);

        // If the data is too big, add scroll bars
        ScrollPane scroller = new ScrollPane ();
        scroller.setMaxSize (1000, 600);
        
        GridPane pane = new GridPane ();
        data = new ArrayList<> ();
        labels = new ArrayList<> ();

        // Use the header to create the first row as buttons.
        List<String> lines = fin.lines().collect(Collectors.toList());
        String[] header = lines.get(0).split(",");
        for (int i = 0; i < header.length; i++) {
            int column = i;

            Button button = new Button(header[i]);
            button.setOnAction(e -> {
                System.out.println("Button " + header[column] + " pressed.");
                data.sort((a, b) -> a[column].compareTo(b[column]));
                update();
            });

            pane.add(button, i, 0);
        }

        for (int i = 0; i < lines.size; i++) {
            String[] record = lines.get(i).split(",");
            data.add(record);
            labels.add(new ArrayList<>());
            for (int j = 0; j < record.length; j++) {
                Label label = new Label(record[j]);
                labels.get(i-1).add(label);
                pane.add(label,j,i);
            }
        }
        fin.close();

        scroller.setContent(pane);
        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.show();
            
        }

    /**
     * Helper funciton used to update all the labels based on the 
     * data. It should be called whenever the data changes.
     */
    private void update () {
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).length; j++) {
                labels.get(i).get(j).setText(data.get(i)[j]);
            }
        }
    }

    public static void main (String[] args) {
        // Example of hard coding the args, useful for debugging but
        // should be removed to test using command line arguments.
        args = new String[] {"data/grades_010.csv"};
        launch (args);
    }
    
}

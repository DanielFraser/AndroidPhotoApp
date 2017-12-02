package com.peter.daniel.photosapp;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Daniel Fraser
 * @author Peter Laskai
 * 
 * The Class drawingController.
 */
public class drawingController 
{
	
	/** The canvas. */
	@FXML
	Canvas canvas;
	
	/** The cp. */
	@FXML
	ColorPicker cp;
	
	@FXML
	Slider sizeS;
	
	/**
	 * Initialize.
	 */
	public void initialize()
	{
		GraphicsContext gc = canvas.getGraphicsContext2D();
		sizeS.setMin(1);
		sizeS.setMax(100);
		sizeS.setValue(5);
		sizeS.setShowTickLabels(true);
		sizeS.setShowTickMarks(true);
		sizeS.setMajorTickUnit(50);
		sizeS.setMinorTickCount(5);
		sizeS.setBlockIncrement(10);
		
		canvas.setOnMouseDragged(e -> {
			double size = sizeS.getValue();
			double x = e.getX() - size/2;
			double y = e.getY() - size/2;
			
			gc.setFill(cp.getValue());
			gc.fillRect(x, y, size, size);
		});
	}

	/**
	 * Start.
	 *
	 * @param stage the stage
	 */
	public void start(Stage stage) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Close.
	 * @throws IOException 
	 */
	@FXML
	private void close() throws IOException {
		Stage stage = (Stage) canvas.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Save.
	 */
	@FXML
	private void save() {
		try
		{
			FileChooser fc = new FileChooser();
			fc.setTitle("save image");
			fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image (*.png)", "*.png"));
			File file = fc.showSaveDialog((Stage) canvas.getScene().getWindow());
			Image screenshot = canvas.snapshot(null, null);
			if (file != null) {
                try {
                	if(!file.getName().endsWith(".png"))
                		file = new File(file.getAbsolutePath() + ".png");
                    ImageIO.write(SwingFXUtils.fromFXImage(screenshot, null), "png", file);
                } catch (IOException ex) {
                	Alert alert = new Alert(AlertType.ERROR);
        			alert.setTitle("Error Dialog");
        			alert.setContentText(ex.getMessage());
        			alert.show();
                }
            }
		}
		catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setContentText("failed to save image!\nError: " + e);
			alert.show();
		}
	}
}

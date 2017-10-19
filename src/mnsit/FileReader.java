package mnsit;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReader {

    private String labelFileName;
    private String imageFileName;

    /** the following constants are defined as per the values described at http://yann.lecun.com/exdb/mnist/ **/

    private static final int MAGIC_OFFSET = 0;
    private static final int OFFSET_SIZE = 4; //in bytes

    private static final int LABEL_MAGIC = 2049;
    private static final int IMAGE_MAGIC = 2051;

    private static final int NUMBER_ITEMS_OFFSET = 4;
    private static final int ITEMS_SIZE = 4;

    private static final int NUMBER_OF_ROWS_OFFSET = 8;
    private static final int ROWS_SIZE = 4;
    public static final int ROWS = 28;

    private static final int NUMBER_OF_COLUMNS_OFFSET = 12;
    private static final int COLUMNS_SIZE = 4;
    public static final int COLUMNS = 28;

    private static final int IMAGE_OFFSET = 16;
    private static final int IMAGE_SIZE = ROWS * COLUMNS;

    public double[][] inputData;
    public double[][] outputData;
    public FileReader(String labelFileName, String imageFileName) {
        this.labelFileName = labelFileName;
        this.imageFileName = imageFileName;
    }
    
    public double[][] getInputData () {
    	return inputData;
    }
    
    public double[][] getOutputData () {
    	return outputData;
    }

    public void loadDigitImages() throws IOException {

        ByteArrayOutputStream labelBuffer = new ByteArrayOutputStream();
        ByteArrayOutputStream imageBuffer = new ByteArrayOutputStream();

        InputStream labelInputStream = new FileInputStream(labelFileName);
        InputStream imageInputStream = new FileInputStream(imageFileName);
        int read;
        byte[] buffer = new byte[16384];

        while((read = labelInputStream.read(buffer, 0, buffer.length)) != -1) {
           labelBuffer.write(buffer, 0, read);
        }

        labelBuffer.flush();

        while((read = imageInputStream.read(buffer, 0, buffer.length)) != -1) {
            imageBuffer.write(buffer, 0, read);
        }

        imageBuffer.flush();

        byte[] labelBytes = labelBuffer.toByteArray();
        byte[] imageBytes = imageBuffer.toByteArray();

        byte[] labelMagic = Arrays.copyOfRange(labelBytes, 0, OFFSET_SIZE);
        byte[] imageMagic = Arrays.copyOfRange(imageBytes, 0, OFFSET_SIZE);

        if(ByteBuffer.wrap(labelMagic).getInt() != LABEL_MAGIC)  {
            throw new IOException("Bad magic number in label file!");
        }

        if(ByteBuffer.wrap(imageMagic).getInt() != IMAGE_MAGIC) {
            throw new IOException("Bad magic number in image file!");
        }

        int numberOfLabels = ByteBuffer.wrap(Arrays.copyOfRange(labelBytes, NUMBER_ITEMS_OFFSET, NUMBER_ITEMS_OFFSET + ITEMS_SIZE)).getInt();
        int numberOfImages = ByteBuffer.wrap(Arrays.copyOfRange(imageBytes, NUMBER_ITEMS_OFFSET, NUMBER_ITEMS_OFFSET + ITEMS_SIZE)).getInt();

        if(numberOfImages != numberOfLabels) {
            throw new IOException("The number of labels and images do not match!");
        }

        int numRows = ByteBuffer.wrap(Arrays.copyOfRange(imageBytes, NUMBER_OF_ROWS_OFFSET, NUMBER_OF_ROWS_OFFSET + ROWS_SIZE)).getInt();
        int numCols = ByteBuffer.wrap(Arrays.copyOfRange(imageBytes, NUMBER_OF_COLUMNS_OFFSET, NUMBER_OF_COLUMNS_OFFSET + COLUMNS_SIZE)).getInt();

        if(numRows != ROWS && numRows != COLUMNS) {
            throw new IOException("Bad image. Rows and columns do not equal " + ROWS + "x" + COLUMNS);
        }
        double[][] inputData = new double[numberOfLabels][784];
        double[][] outputData = new double[numberOfLabels][10];
        for(int i = 0; i < numberOfLabels; i++) {
            int label = labelBytes[OFFSET_SIZE + ITEMS_SIZE + i];
            if (i == 1)
        		System.out.println(label);
            for (int j = 0; j < 10; j++) {
            	outputData[i][j] = (label == j) ? 1 : 0;
            }
            byte[] imageData = Arrays.copyOfRange(imageBytes, (i * IMAGE_SIZE) + IMAGE_OFFSET, (i * IMAGE_SIZE) + IMAGE_OFFSET + IMAGE_SIZE);	
            for (int j = 0; j < 784; j++) {
            	inputData[i][j] = imageData[j];
            }
            //images.add(new DigitImage(label, imageData));
        }
        this.inputData = inputData;
        this.outputData = outputData;
        for (int i = 0 ; i < 10; i++) {
        	System.out.println(outputData[1][i]);
        }
     

        //return images;
    }
}

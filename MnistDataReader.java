import java.io.*;

public class MnistDataReader {

    public PixelImage[] readData(String dataFilePath, String labelFilePath) throws IOException {

        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFilePath)));
        int magicNumber = dataInputStream.readInt();
        int numberOfItems = dataInputStream.readInt();
        int nRows = dataInputStream.readInt();
        int nCols = dataInputStream.readInt();

//        System.out.println("magic number: " + magicNumber);
//        System.out.println("# of items: " + numberOfItems);
//        System.out.println("# of rows: " + nRows);
//        System.out.println("# of cols: " + nCols);

        DataInputStream labelInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(labelFilePath)));
        int labelMagicNumber = labelInputStream.readInt();
        int numberOfLabels = labelInputStream.readInt();

//        System.out.println("labels magic number: " + labelMagicNumber);
//        System.out.println("# of labels: " + numberOfLabels);

        PixelImage[] data = new PixelImage[numberOfItems];

        assert numberOfItems == numberOfLabels;

        for (int i = 0; i < numberOfItems; i++) {
            PixelImage mnistMatrix = new PixelImage(nRows*nCols);
            mnistMatrix.setLabel(labelInputStream.readUnsignedByte());
            for (int index = 0; index < mnistMatrix.getLength(); index++) {
                mnistMatrix.setValue(index, dataInputStream.readUnsignedByte());
            }
            data[i] = mnistMatrix;
        }

        dataInputStream.close();
        labelInputStream.close();

        return data;
    }
}
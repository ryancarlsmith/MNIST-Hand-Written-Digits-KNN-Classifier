import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HandWrittenDigitsKNN {

    public static class ImageComparator implements Comparator<PixelImage> {

        private PixelImage testImage;

        private String func;

        private boolean thresh;

        public ImageComparator(PixelImage testImage, String func, boolean thresh1) {
            this.testImage = testImage;
            this.func = func;
            this.thresh = thresh1;
        }

        public int compare(PixelImage trainImage1, PixelImage trainImage2) {
            return Double.compare(testImage.compare(trainImage1, this.func, this.thresh),
                    testImage.compare(trainImage2, this.func, this.thresh));
        }
    }

    public static int knn(PixelImage[] trainingData, PixelImage testImage, int k, String func, boolean thresholded) {
        PriorityQueue<PixelImage>  pq = new PriorityQueue<>(k, new ImageComparator(testImage, func, thresholded));
        int[] guesses = new int[k];
//        System.out.println("//k: " + k);

        for (PixelImage image : trainingData) {
            pq.add(image);
        }

        for (int i = 0; i < k; i++) {
            PixelImage pixelImage = pq.remove();
            guesses[i] = pixelImage.getLabel();
        }

        return mode(guesses);
    }

    public static void main(String[] args) throws IOException {
        int iterations = 10;
        boolean verbose = false;
        String func = "L0";
        int K = 3;
        boolean threshholded = true;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-iter":
                    iterations = Integer.parseInt(args[i + 1]);
                    break;
                case "-verbose":
                    verbose = true;
                    break;
                case "-function":
                    switch (args[i+1]){
                        case "L0":
                            func = "L0";
                            break;
                        case "L1":
                            func = "L1";
                            break;
                        case "L2":
                            func = "L2";
                            break;
                        case "L3":
                            func = "L3";
                            break;
                        default:
                            break;
                    }
                    break;
                case "-K":
                    K = Integer.parseInt(args[i + 1]);
                    break;
                case "-thresh":
                    threshholded = true;
                    break;
            }
        }

        PixelImage[] trainDataReader = new MnistDataReader().readData("train-images.idx3-ubyte", "train-labels.idx1-ubyte");
        PixelImage[] testDataReader = new MnistDataReader().readData("t10k-images.idx3-ubyte", "t10k-labels.idx1-ubyte");
//        System.out.println("function: " + func);
//        System.out.println("k: " + K);
        int correctValues = 0;
        for (int i = 0; i < iterations; i++) {
            if (verbose){
                printMnistMatrix(testDataReader[i]);
            }
            int answer = knn(trainDataReader, testDataReader[i], K, func, threshholded);
            if (answer == testDataReader[i].getLabel()) {
                correctValues++;
            }
        }
        System.out.println((double) (correctValues)/ (double) (iterations));
    }

    public static int mode(int a[] ) {
        int maxValue = 0, maxCount = 0, i, j;

        for (i = 0; i < a.length; ++i) {
            int count = 0;
            for (j = 0; j < a.length; ++j) {
                if (a[j] == a[i])
                    ++count;
            }

            if (count > maxCount) {
                maxCount = count;
                maxValue = a[i];
            }
        }
        return maxValue;
    }


    private static void printMnistMatrix(final PixelImage matrix) {
        System.out.println("label: " + matrix.getLabel());
        for (int i = 0; i < matrix.getLength(); i++) {
            System.out.print(matrix.getValue(i));
            if (i % 28 == 0 && i != 0){
                System.out.println();
            }
        }
        System.out.println();
    }
}
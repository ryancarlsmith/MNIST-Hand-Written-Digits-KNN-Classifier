
public class PixelImage {

    private final int[] data;

    private final int size;

    private int label;

    public PixelImage(int s) {
        this.size = s;
        data = new int[s];
    }

    public int getValue(int i) {
        return data[i];
    }

    public void setValue(int i, int value) {
        data[i] = value;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getLength() {
        return size;
    }

    public double compare(PixelImage image, String distance, boolean thresh) {
        double sum = 0;

        for (int i = 0; i < image.data.length; i++) {

            int trainValue = image.data[i];
            int testValue = this.data[i];

            if (thresh) {
                 trainValue = image.data[i] >= 128 ? 1 : 0;
                 testValue = data[i] >= 128 ? 1 : 0;
            }



            switch(distance) {
                case "L0":
                    if (thresh) {
                        //System.out.println("LO");
                        sum += (trainValue == testValue) ? 0 : 1;
                    }
                    else{
                        if (!((this.getValue(i) > (image.getValue(i) - 10))
                                && (this.getValue(i) < (image.getValue(i) + 10)))){
                            sum++;
                        }
                    }
                    break;
                case "L1":
//                    System.out.println("L1");
                    sum += Math.abs((image.data[i] - data[i]));
                    break;
                case "L2":
//                    System.out.println("L2");
                    sum += Math.pow(image.data[i] - data[i], 2);
                    break;
                case "L3":
//                    System.out.println("L3");
                    sum += Math.pow(Math.pow(image.data[i] - data[i], 3), 1/3);
            }

        }

        return sum;
    }

}

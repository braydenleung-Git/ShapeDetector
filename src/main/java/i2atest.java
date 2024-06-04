public class i2atest {
    public static void main(String[] args) {
        Image2Array.processImage(/*"./test 1.png"*/);
        int[][] array = Image2Array.getTranscodedArray();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
}

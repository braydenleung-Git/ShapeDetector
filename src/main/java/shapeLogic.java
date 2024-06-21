import java.util.*;
public class shapeLogic {

    public static void shapeL(int[][] pixels,boolean test){

    }

    public static void shapeL(int[][] pixels){
        int[] ar = findPixel(pixels);
        int[][] shape = returnOtherShape(pixels, ar);
        shape = trim(shape);
        fourShape(shape);
    }

    public static int[] findPixel(int[][] parray){
        for (int y = 0; y < parray.length; y ++){
            for (int x = 0; x < parray[y].length; x ++){//looping through every element in the array
                if (parray[y][x] == 1){//traverse from the black pixel
                    int[] coords = {y, x};
                    return coords;
                }
            }
        }
        int[] coords = {0, 0};
        return coords;
    }

    public static int[][] returnShape(int[][] parray, int[] coords){
        int maxx = 0;
        int maxy = 0;
        int emptyy = 0;
        for (int y = coords[0]; y < parray.length; y ++){//index 0 is y and index 1 is x
            int emptyx = 0;
            if (maxx == 0) {
                for (int x = coords[1]; x < parray[y].length; x++) {//looping through every element in the array
                    if (parray[y][x] == 1 & x == parray[y].length-1){
                        maxx = x;
                        break;
                    }
                    if (parray[y][x] == 0) {//traverse from the black pixel
                        emptyx += 1;
                    } else {
                        emptyx = 0;
                    }
                    if (emptyx == 1) {
                        maxx = x - 1;
                        break;
                    }
                }
            }
            if (parray[y][coords[1]] == 1 & y == parray.length-1){
                maxy = y;
                break;
            }
            if (parray[y][coords[1]] == 0) {//traverse from the black pixel
                emptyy += 1;
            } else {
                emptyy = 0;
            }
            if (emptyy == 1) {
                maxy = y - 1;
                break;
            }
        }
        System.out.println(maxy);
        System.out.println(maxx);
        int[][] smaller = new int[maxy-coords[0]+1][maxx-coords[1]+1];//needs +1 because maxx is 0 indexed and lengths are 1 indexed
        for (int y = coords[0]; y < maxy+1; y ++) {//index 0 is y and index 1 is x
            //looping through every element in the array
            if (maxx + 1 - coords[1] >= 0)
                System.arraycopy(parray[y], coords[1], smaller[y - coords[0]], 0, maxx + 1 - coords[1]);
        }
        for (int[] y : smaller){
            for (int x : y){
                System.out.print(x);
            }
            System.out.println();
        }
        return smaller;
    }

    public static int[][] returnMoreShape(int[][] parray, int[] coords) {//codium generated
        int minX = coords[1], maxX = coords[1];
        int minY = coords[0], maxY = coords[0];

        // Find the bounds of the shape
        for (int y = 0; y < parray.length; y++) {
            for (int x = 0; x < parray[y].length; x++) {
                if (parray[y][x] == 1) {
                    if (x < minX) minX = x;
                    if (x > maxX) maxX = x;
                    if (y < minY) minY = y;
                    if (y > maxY) maxY = y;
                }
            }
        }

        // Create the smaller array
        int[][] smaller = new int[maxY - minY + 1][maxX - minX + 1];
        for (int y = minY; y <= maxY; y++) {
            if (maxX + 1 - minX >= 0)
                System.arraycopy(parray[y], minX, smaller[y - minY], 0, maxX + 1 - minX);
        }

        // Print the smaller array


        return smaller;
    }

    public static int[][] returnConnectedShape(int[][] parray, int[] coords){
        int maxx = coords[1];
        int maxy = coords[0];
        ArrayList<Object> temp = new ArrayList<Object>();

        int[][] smaller = new int[parray.length][parray[0].length];//this is just easier than making nested arraylists that need to be converted back to an int 2d array because it's the max size and it can be trimmed
        boolean con = false;
        for (int y = coords[0]; y < parray.length; y ++){
            boolean locked = false;
            for (int x = coords[1]; x < parray[y].length; x++){
                for (int cases = 0; cases < 5; cases += 1) {
                    try {
                        switch (cases) {
                            case 1:
                                if (parray[y][x] == 1 & parray[y - 1][x] == 1){
                                    con = true;
                                }
                                break;
                            case 2:
                                if (parray[y][x] == 1 & parray[y + 1][x] == 1){
                                    con = true;
                                }
                                break;
                            case 3:
                                if (parray[y][x] == 1 & parray[y][x - 1] == 1){
                                    con = true;
                                    if (!locked){
                                        locked = true;
                                        for (int i = x; i >= 0; i --){
                                            if (parray[y][x] == 1){
                                                coords[1] = i;
                                            }
                                            else{
                                                break;
                                            }
                                        }
                                    }
                                }
                                break;
                            case 4:
                                if (parray[y][x] == 1 & parray[y][x + 1] == 1){
                                    con = true;
                                }
                                break;
                        }
                    }
                    catch (Exception e){
                        //cases += 1;//for loop already increments it so no need to inrement it again
                        //System.out.println(coords[1]);
                    }
                }
                if (con) smaller[y-coords[0]][x-coords[1]] = parray[y][x];
                //maxx += 1;
            }
            //maxy += 1;
        }

        return smaller;
    }

    public static int[][] returnOtherShape(int[][] parray, int[] coords){
        int maxx = coords[1];
        int maxy = coords[0];
        ArrayList<Object> temp = new ArrayList<Object>();

        int[][] smaller = new int[parray.length][parray[0].length];//this is just easier than making nested arraylists that need to be converted back to an int 2d array because it's the max size and it can be trimmed
        boolean con = false;
        for (int y = coords[0]; y < parray.length; y ++){

            boolean barrier = true;
            for (int j = coords[1]; j < parray[y].length; j ++){
                if (parray[y][j] == 1) {
                    barrier = false;
                    break;
                }
            }
            if (barrier){
                break;
            }

            boolean locked = false;
            for (int x = coords[1]; x < parray[y].length; x++){
                if (parray[y][x] == 1){
                    smaller[y-coords[0]][x-coords[1]] = parray[y][x];
                }
                //maxx += 1;
            }
            //maxy += 1;
        }

        return smaller;
    }

    public static void printArray(int[][] smaller){
        for (int[] row : smaller) {
            for (int val : row) {
                System.out.print(val);
            }
            System.out.println();
        }
    }

    public static int[][] trim(int[][] shape){
        for (int i = 0; i < shape.length; i ++){
            int count = 0;
            for (int n = 0; n < shape[i].length; n ++){
                if (shape[i][n] == 0){
                    count += 1;
                }
            }
            int[] arr = new int[shape[i].length-count];
            for (int j = 0; j < arr.length; j ++){
                arr[j] = 1;
            }
            shape[i] = arr;
        }
        int c = 0;
        for (int[] a : shape){
            if (a.length == 0){
                c ++;
            }
        }
        shape = Arrays.copyOfRange(shape, 0, shape.length-c);
        return shape;
    }

    public static void squareOrRectangle(int[][] shape){
        if (shape[0].length == shape.length){
            System.out.println("Square");
            outputGUI.squares++;
        }
        else{
            System.out.println("Rectangle");
            outputGUI.rectangles++;
        }
    }

    public static boolean triangle(int[][] shape){
        for (int i = 0; i < shape.length; i ++){
            if (i > 0) {
                if (shape[i].length > shape[i-1].length) {
                    return false;
                }
            }
            else{
                if (shape[0].length < shape[1].length){
                    //System.out.println(shape[0].length);
                    //System.out.println(shape[1].length);
                    return false;
                }
            }
        }
        return shape[0].length > shape[shape.length - 1].length;
    }

    public static void fourShape(int[][] shape){
        //System.out.println(shape[Math.ceilDiv(2, shape.length)].length);
        if (shape[0].length < shape[Math.ceilDiv(2, shape.length)].length & shape[Math.ceilDiv(2, shape.length)].length > shape[shape.length-1].length){
            System.out.println("Circle");
            outputGUI.circles++;
        }
        else if (triangle(shape)){
            System.out.println("Triangle");
            outputGUI.triangles++;
        }
        else{
            /*
            System.out.println(shape[0].length);
            System.out.println(shape.length);
            for (int y = 0; y < shape.length; y ++){
                for (int x = 0; x < shape[y].length; x ++){
                    System.out.print(shape[y][x]);
                }
                System.out.println();
            }*/
            squareOrRectangle(shape);
        }
    }

    private ArrayList<int[][]> separateShapes(int[][] original){
        //1st layer, point of origin
        //2nd layer
        ArrayList<int[][][]> shapes = new ArrayList<>();
        ArrayList<int[][]> output = new ArrayList<>();
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                //if it's black point, and no array has been added, add an array
                if(original[i][j] == 1 && shapes.isEmpty()){
                    int[][][] temp = new int[2][1][1];
                    temp[0][0][0] = i;//row cord
                    temp[0][1][0] = j;//col cord
                    temp[1][0][0] = original[i][j];
                    shapes.add(temp);
                }
                //if it is a black point, and has not been added to any of the array, then add it to the array else, create a new one
                if(original[i][j] == 1){
                    int positiveX = 0;
                    int negativeX = 0;
                    int positiveY = 0;
                    int negativeY= 0 ;
                    int counter = 0;
                    for (int[][][] shape : shapes) {
                        //gets the relative position of the black pixel
                        int deltaX = i - shape[0][0][0];
                        int deltaY = j - shape[0][1][0];
                        //the first array number means the layer, the second means the row number of that layer
                        //if the deltas are out of bound of the arrays
                        if (deltaX < 0 || deltaY < 0 || deltaX > shape[1].length || deltaY > shape[1][0].length) {
                            if (deltaX - shape[1].length == 1 && (deltaY == 0 || deltaY - shape[1][0].length == 1 || deltaY - shape[1][0].length == -1)) {
                                positiveX = 1;
                            }
                            else if (deltaX - shape[1].length == -1 && (deltaY == 0 || deltaY - shape[1][0].length == 1 || deltaY - shape[1][0].length == -1)) {
                                negativeX = 1;
                            }

                            if (deltaY - shape[1][0].length == 1 && (deltaX == 0 || deltaX - shape[1].length == 1 || deltaX - shape[1].length == -1)) {
                                positiveY = 1;
                            }
                            else if (deltaY - shape[1][0].length == -1 && (deltaX == 0 || deltaX - shape[1].length == 1 || deltaX - shape[1].length == -1)) {
                                negativeY = 1;
                            }
                        }
                        if (positiveX == 1 || negativeX == 1 || positiveY == 1 || negativeY == 1) {
                            int newX = shape[0][0][0] + positiveX - negativeX;
                            int newY = shape[0][1][0] + positiveY - negativeY;
                            int[][] newArray = new int[shape[1].length + positiveX + negativeY][shape[1][0].length + positiveY + negativeY];
                            //copies the old array to new array, and add the new pixel to the array
                            if (positiveX == 1 && negativeY == 1) {
                                for (int k = 0; k < shape[1].length; k++) {
                                    System.arraycopy(shape[1][k], 0, newArray[k], 1, shape[1][k].length);
                                }
                                newArray[shape[1].length][0] = 1;
                            }
                            else if (positiveX == 1 && positiveY == 1) {
                                for (int k = 0; k < shape[1].length; k++) {
                                    System.arraycopy(shape[1][k], 0, newArray[k], 0, shape[1][k].length);
                                }
                                newArray[shape[1].length][shape[1][0].length] = 1;
                            }
                            else if (negativeX == 1 && negativeY == 1) {
                                for (int k = 0; k < shape[1].length; k++) {
                                    System.arraycopy(shape[1][k], 0, newArray[k + 1], 1, shape[1][k].length);
                                }
                                newArray[0][0] = 1;
                            }
                            else if (negativeX == 1 && positiveY == 1) {
                                for (int k = 0; k < shape[1].length; k++) {
                                    System.arraycopy(shape[1][k], 0, newArray[k + 1], 0, shape[1][k].length);
                                }
                                newArray[0][shape[1][0].length] = 1;
                            }
                            else if (positiveX == 1) {
                                for (int k = 0; k < shape[1].length; k++) {
                                    System.arraycopy(shape[1][k], 0, newArray[k], 0, shape[1][k].length);
                                }
                                newArray[shape[1].length][deltaY] = 1;
                            }
                            else if (negativeX == 1) {
                                for (int k = 0; k < shape[1].length; k++) {
                                    System.arraycopy(shape[1][k], 0, newArray[k + 1], 0, shape[1][k].length);
                                }
                                newArray[0][deltaY] = 1;
                            }
                            else if (positiveY == 1) {
                                for (int k = 0; k < shape[1].length; k++) {
                                    System.arraycopy(shape[1][k], 0, newArray[k], 0, shape[1][k].length);
                                }
                                newArray[deltaX][shape[1][0].length] = 1;
                            }
                            else {
                                for (int k = 0; k < shape[1].length; k++) {
                                    System.arraycopy(shape[1][k], 0, newArray[k], 1, shape[1][k].length);
                                }
                                newArray[deltaX][0] = 1;
                            }
                            int[][][] temp = new int[2][newArray.length][newArray[0].length];
                            temp[0][0][0] = newX;
                            temp[0][1][0] = newY;
                            temp[1] = newArray;
                            shapes.set(counter, temp);
                        }
                    }//if the pixel is not near any arrays, then create new one
                    if(counter == shapes.size() - 1){
                        int[][][] temp = new int[2][1][1];
                        temp[0][0][0] = i;
                        temp[0][1][0] = j;
                        temp[1][0][0] = original[i][j];
                        shapes.add(temp);
                    }
                }
            }
        }
        for (int[][][] shape : shapes) {
            output.add(shape[1]);
        }
        return output;
    }
}

import java.util.*;
public class shapelogic {
    public static void main(String[] args){
        int[][] pixels = {//0 is for white and 1 is for black
            {0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0}
        };
        int[] ar = findpixel(pixels);
        System.out.println(Arrays.toString(ar));
        int[][] shape = returnShape(pixels, ar);

        System.out.println(rectangleorsquare(shape));
    }
    public static int[] findpixel(int[][] parray){
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
            for (int x = coords[1]; x < maxx+1; x++) {//looping through every element in the array
                smaller[y-coords[0]][x-coords[1]] = parray[y][x];
            }
        }
        for (int[] y : smaller){
            for (int x : y){
                System.out.print(x);
            }
            System.out.println();
        }

        return smaller;

    }

    public static String rectangleorsquare(int[][] shape){
        if (shape[0].length == shape.length){
            return "square";
        }
        else{
            return "rectangle";
        }
    }
}

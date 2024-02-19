import java.util.BitSet;

public class TestBitSet {
    public static void main(String[] args) {
        int[] arry = {8,1,2,3,4,5,6,7,9,10,11,12,13,21,12,1};
        BitSet bitMap = new BitSet();
        for (int i = 0; i < arry.length ; i++) {
            if (bitMap.get(arry[i])) {
                System.out.println(arry[i]);
            } else {
                bitMap.set(arry[i]);
            }
        }
    }
}

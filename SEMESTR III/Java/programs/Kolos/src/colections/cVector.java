package colections;

import java.util.Iterator;
import java.util.Vector;

public class cVector<E> {

    public void show(Vector<E> vecotr){

        for(E eleV : vecotr){
            System.out.print(eleV + " ");
        }
    }

    public void showIterator(Vector vecotr){

        for(Iterator it = vecotr.iterator(); it.hasNext();){
            System.out.print(it.next() + " ");
        }
    }

    public static void main(String[] args){

        cVector main = new cVector();
        Vector<Integer> vector = new Vector<Integer>();
        vector.add(6);
        vector.add(7);
        main.show(vector);
        vector.set(1, 3);
        main.showIterator(vector);
        vector.remove(1);
        main.show(vector);
    }
}

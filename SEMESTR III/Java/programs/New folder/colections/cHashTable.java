package colections;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class cHashTable<E,T> {

    public void show(Hashtable<E,T> hashtable){

        Enumeration<E> enumKey = hashtable.keys();
        while(enumKey.hasMoreElements()){
            E key = enumKey.nextElement();
            System.out.println(key + " : " + hashtable.get(key));
        }
    }

    public static void main(String[] args){

        cHashTable main = new cHashTable();
        Hashtable<Integer, String> hashtable = new Hashtable<Integer, String>();
        hashtable.put(0, "zero");
        hashtable.put(1, "jeden");
        hashtable.put(2, "dwa");
        main.show(hashtable);
        hashtable.put(1, "one");
        main.show(hashtable);
        hashtable.remove(1);
        main.show(hashtable);
    }
}

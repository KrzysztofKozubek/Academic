package test;

import java.util.*;

public class test {

    public static void main(String[] args){

        Set<Integer> set = new HashSet<Integer>();
        List<Integer> list = new ArrayList<Integer>();

        set.add(new Integer((2)));
        set.add(new Integer((2)));
        set.add(new Integer((1)));
        set.add(new Integer((2)));
        set.add(new Integer((1)));


        list.add(new Integer((2)));
        list.add(new Integer((2)));
        list.add(new Integer((1)));
        list.add(new Integer((2)));
        list.add(new Integer((1)));


        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            String element = iterator.next().toString();
            System.out.println(element);
        }
        System.out.println();
        for(Integer var : list){
            System.out.println(var);
        }

    }
}

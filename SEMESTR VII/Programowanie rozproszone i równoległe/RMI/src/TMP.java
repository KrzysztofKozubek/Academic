import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

/**
 * Created by kris on 26.12.16.
 */

public class TMP {

    public static void main(String[] args) {

        Map<Integer, Integer> actualStanOfWard = new HashMap<>();
        actualStanOfWard.put(1,1);
        actualStanOfWard.put(1,2);
        actualStanOfWard.put(1,1);
        actualStanOfWard.put(1,1);
        System.out.println(actualStanOfWard);


        try{

            Ward w = new Ward();
            Map<Integer, Integer> map = new HashMap<>();
            map.put(0, 5);
            map.put(1, 5);
            map.put(2, 5);
            map.put(3, 5);

            w.setLimits(map);

            List<Patient> list = new ArrayList<>();
            for(int i = 0; i < 6; i++) list.add(new Patient());

            System.out.println();
            System.out.println(w.getAdmissionHistory());
            System.out.println(w.getPatients());
            System.out.println(w.getRoomsState());

            for(int i =0;i <6;i++) w.patientAdmission(list.get(i), 0);
            for(int i =0;i <6;i++) w.patientDischarge(list.get(i));
            for(int i =0;i <6;i++) w.patientAdmission(list.get(i), 0);
            for(int i =0;i <6;i++) w.patientDischarge(list.get(i));
            for(int i =0;i <6;i++) w.patientAdmission(list.get(i), 0);
            for(int i =0;i <6;i++) w.patientDischarge(list.get(i));
            for(int i =0;i <6;i++) w.patientAdmission(list.get(i), 0);
            for(int i =0;i <6;i++) w.patientDischarge(list.get(i));
            for(int i =0;i <6;i++) w.patientAdmission(list.get(i), 2);
            for(int i =0;i <6;i++) w.patientDischarge(list.get(i));
            for(int i =0;i <6;i++) w.patientAdmission(list.get(i), 0);
            for(int i =0;i <6;i++) w.patientDischarge(list.get(i));
            for(int i =0;i <6;i++) w.patientAdmission(list.get(i), 1);
            for(int i =0;i <6;i++) w.patientDischarge(list.get(i));

            w.patientAdmission(list.get(0), 0);
            w.patientAdmission(list.get(1), 1);
            w.patientAdmission(list.get(2), 2);
            w.patientAdmission(list.get(3), 3);
            w.patientAdmission(list.get(0), 1);

            System.out.println();
            System.out.println(w.getAdmissionHistory());
            System.out.println(w.getAdmissionHistory().size());
            System.out.println(w.getPatients());
            System.out.println(w.getRoomsState());

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

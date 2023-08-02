import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import DataManager.DataHandler;
import DataManager.DataEntry;

public class TimeSync {
    private Map map;
    private int time = 0;
    private ArrayList<DataEntry> dataEntries = new ArrayList<>();

    public TimeSync(Map map, Species sp1, Species sp2, Species sp3) {
        this.map = map;
        this.map.insertSpecies(sp1, sp1.i, sp1.j);
        this.map.insertSpecies(sp2, sp2.i, sp2.j);
        this.map.insertSpecies(sp3, sp3.i, sp3.j);
    }

    public void begin(Map m) throws InterruptedException {
        DataHandler.truncateTable("animals");
        ConcurrentLinkedDeque<Species> allSpecies;
        //ExecutorService executor = Executors.newCachedThreadPool();
        while (m.getNum1() > 0 && m.getNum2() > 0 && m.getNum3() > 0) {
            allSpecies = m.getAllSpecies();
            ConcurrentLinkedDeque<Species> replicate = new ConcurrentLinkedDeque<>();
            replicate.addAll(allSpecies);
            map.evolve();
            for (Species sp : replicate) {
                sp.run();
                DataEntry d = sp.getDataEntry();
                d.addTime(time);
                dataEntries.add(d);
            }
            Thread.sleep(50);
            System.out.println(m.getName1() + ": " + map.getNum1());
            System.out.println(m.getName2() + ": " + map.getNum2());
            System.out.println(m.getName3() + ": " + map.getNum3());
            time++;
        }
        DataHandler.insertDataBatch(dataEntries);
        if (m.getNum1() > 0) {
            System.out.println(m.getName1());
        }
        if (m.getNum2() > 0) {
            System.out.println(m.getName2());
        }
        if (m.getNum3() > 0) {
            System.out.println(m.getName3());
        }
    }
}

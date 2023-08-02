import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Map m = new Map(60, 60, 1000, "spider", "snake", "lion", 8001);
        Species sp1 = new Prey("spider", 3, 15, m, new Random(222), 2, 13);
        Species sp2 = new Predator("snake", 3, 15, m, new Random(764757), 25, 17);
        Species sp3 = new Hunter("lion", 3, 15, m, new Random(2382), 2, 15);
        TimeSync t = new TimeSync(m, sp1, sp2, sp3);
        t.begin(m);
    }
}
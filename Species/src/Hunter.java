import java.util.Random;

public class Hunter extends Species {
    private String type = "level2";
    public Hunter(String name, int energyConsumption, int mortality, Map map, Random seed, int i, int j) {
        super(name, energyConsumption, mortality, map, seed, i, j);
    }

    @Override
    public int[] observeFromCenter(int radius) { //1 3 , 2 5 , 3 7,  4 9
        int[] array = new int[2];
        for (int m = i - radius; m < i + radius; m++) {
            if (map.ifHavePrey(m, j - radius, "level2")) {
                array[0] = m;
                array[1] = j - radius;
                return array;
            }
            if (map.ifHavePrey(m, j + radius, "level2")) {
                array[0] = m;
                array[1] = j + radius;
                return array;
            }
        }
        for (int p = j - radius; p < j + radius; p++) {
            if (map.ifHavePrey(i - radius, p, "level2")) {
                array[0] = i - radius;
                array[1] = p;
                return array;
            }
            if (map.ifHavePrey(i + radius, p, "level2")) {
                array[0] = i + radius;
                array[1] = p;
                return array;
            }
        }
        return observeFood(radius);
    }

    public int[] observeFood(int radius) { //1 3 , 2 5 , 3 7,  4 9
        int[] array = new int[2];
        for (int m = i - radius; m < i + radius; m++) {
            if (map.ifHaveFood(m, j - radius)) {
                array[0] = m;
                array[1] = j - radius;
                energy += 30;
                return array;
            }
            if (map.ifHaveFood(m, j + radius)) {
                array[0] = m;
                array[1] = j + radius;
                energy += 30;
                return array;
            }
        }
        for (int p = j - radius; p < j + radius; p++) {
            if (map.ifHaveFood(i - radius, p)) {
                array[0] = i - radius;
                array[1] = p;
                energy += 30;
                return array;
            }
            if (map.ifHaveFood(i + radius, p)) {
                array[0] = i + radius;
                array[1] = p;
                energy += 30;
                return array;
            }
        }
        return array;
    }

    @Override
    public void fertile(int len, int width) {
        if (mortality <= maxMortality && energy > 2 * energyConsumption && energy - maxEnergy >= 0) {
            Hunter newSp = new Hunter(name, energyConsumption, 5 * energyConsumption, map, seed, len, width);
            map.insertSpecies(newSp, len, width);
            energy -= newSp.energy - 2;
        }
    }
}

import DataManager.DataEntry;

import java.util.Arrays;
import java.util.Random;

public class Species implements Runnable {
    public int energyConsumption;
    /**higher energy consumption + perDistance + mortality,
     lower fertility between, value between 1-5
     calculating formula: perDistance = ec,
     fertility = 20 / ec,
     mortality = 5ec,
     */
    public double energy = 50;
    public double maxEnergy;
    private int perDistance;
    private int fertility;
    public int mortality;
    public int maxMortality;
    public Map map;
    private int len;
    private int width;
    public int i;
    public int j;
    public Random seed;
    public String name;
    public String type = "";
    private boolean markIfHavePrey = false;

    public Species(String name, int energyConsumption, int mortality, Map map, Random seed, int i, int j) {
        this.energyConsumption = energyConsumption;
        this.perDistance = energyConsumption;
        this.fertility = 20 / energyConsumption;
        this.mortality = mortality;
        this.maxMortality = this.mortality;
        this.maxEnergy = energy;
        this.len = map.getLen();
        this.width = map.getWidth();
        this.seed = seed;
        this.map = map;
        this.name = name;
        this.i = i;
        this.j = j;
    }

    public void run() {
        int[] foodLoc = observe();
        if (foodLoc != null) {
            map.deleteSpecies(this, i, j);
            i = foodLoc[0];
            j = foodLoc[1];
            map.insertSpecies(this, i, j);
            if (map.ifHaveFood(i, j)) {
                map.eatFood(i, j);
                energy += 15;
            }
        } else {
            int oldLen = i;
            int oldWidth = j;
            map.deleteSpecies(this, i, j);
            randomMove();
            energy -= (Math.abs(i - oldLen) + Math.abs(j - oldWidth));
        }
        mortality -= 2;
        if (energy < 0 || mortality < 0) {
            map.die(this);
        }
        fertile(i, j);
    }

    public DataEntry getDataEntry() {
        return new DataEntry(name, i, j, energy, energyConsumption);
    }

    public int[] observe() {
        int[] array;
        for (int radius = 1; radius < perDistance; radius++) {
            array = observeFromCenter(radius);
            if (!Arrays.equals(array, new int[] {0, 0})) {
                energy -= Math.pow(radius, 2);
                return array;
            }
        }
        return null;
    }

    public int[] observeFromCenter(int radius) { //1 3 , 2 5 , 3 7,  4 9
        int[] array = new int[2];
        for (int m = i - radius; m < i + radius; m++) {
            if (map.ifHaveFood(m, j - radius)) {
                array[0] = m;
                array[1] = j - radius;
                return array;
            }
            if (map.ifHaveFood(m, j + radius)) {
                array[0] = m;
                array[1] = j + radius;
                return array;
            }
        }
        for (int p = j - radius; p < j + radius; p++) {
            if (map.ifHaveFood(i - radius, p)) {
                array[0] = i - radius;
                array[1] = p;
                return array;
            }
            if (map.ifHaveFood(i + radius, p)) {
                array[0] = i + radius;
                array[1] = p;
                return array;
            }
        }
        return array;
    }

    public void fertile(int len, int width) {
        if (mortality <= maxMortality && energy > 2 * energyConsumption && energy - maxEnergy >= 0) {
            Species newSp = new Species(name, energyConsumption, 5 * energyConsumption, map, seed, len, width);
            map.insertSpecies(newSp, len, width);
            energy -= newSp.energy - 2;
        }
    }

    public void randomMove() {
        if (ifOnTheEdge()) {
            towardsCenter();
        } else {
            fourDirectionMove();
        }
    }

    public void towardsCenter() {
        double midWidth = len / 2;
        double midLen = width / 2;
        if (i <= midWidth && j <= midLen) {
            i += 1;
            j += 1;
            map.insertSpecies(this, i, j);
        } else if (i <= midWidth && j >= midLen) {
            i += 1;
            j -= 1;
            map.insertSpecies(this, i, j);
        } else if (i >= midWidth && j <= midLen) {
            i -= 1;
            j += 1;
            map.insertSpecies(this, i, j);
        } else if (i >= midWidth && j >= midLen) {
            i -= 1;
            j -= 1;
            map.insertSpecies(this, i, j);
        }
    }

    public boolean ifOnTheEdge() {
        if (i == 0 || j == 0 || i == len - 1 || j == width - 1) {
            return true;
        }
        return false;
    }

    public void fourDirectionMove() {
        if (getRemainder(4) == 0) {
            i += 1;
            map.insertSpecies(this, i, j);
        } else if (getRemainder(4) == 1) {
            i -= 1;
            map.insertSpecies(this, i, j);
        } else if (getRemainder(4) == 2) {
            j += 1;
            map.insertSpecies(this, i, j);
        } else if (getRemainder(4) == 3) {
            j -= 1;
            map.insertSpecies(this, i, j);
        }
    }

    public int getRemainder(int divider) {
        return Math.floorMod(seed.nextInt(), divider);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

}

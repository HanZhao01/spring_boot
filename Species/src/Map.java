import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Map {
    private HashSet<Species>[][] grid;
    private boolean[][] foodGrid;
    private Random seed;
    private int len;
    private int width;
    private int food;
    private int num1 = 0;
    private int num2 = 0;
    private int num3 = 0;
    private String name1;
    private String name2;
    private String name3;
    private ConcurrentLinkedDeque<Species> allSpecies;

    public Map(int len, int width, int food, String name1, String name2, String name3, int seed) {
        this.len = len;
        this.width = width;
        this.food = food;
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.seed = new Random(seed);
        grid = new HashSet[len][width];
        foodGrid = new boolean[len][width];
        gridConstructor();
        allSpecies = new ConcurrentLinkedDeque<Species>();
    }

    public void gridConstructor() {
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new HashSet<Species>();
                foodGrid[i][j] = false;
            }
        }
    }

    public void foodConstructor(int numOfFood) {
        while (numOfFood > 0) {
            int length = remainder(len);
            int wid = remainder(width);
            foodGrid[length][wid] = true;
            numOfFood--;
        }
    }

    public void foodEliminator() {
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < width; j++) {
                foodGrid[i][j] = false;
            }
        }
    }

    public int remainder(int len) {
        return Math.floorMod(seed.nextInt(), len);
    }

    public void evolve() {
        foodEliminator();
        foodConstructor(food);
    }

    public int getLen() {
        return len;
    }

    public int getWidth() {
        return width;
    }

    public ConcurrentLinkedDeque getAllSpecies() {
        return allSpecies;
    }

    public int getNum1() {
        return num1;
    }

    public int getNum2() {
        return num2;
    }

    public int getNum3() {
        return num3;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }
    public String getName3() {
        return name3;
    }


    public boolean ifHaveFood(int i, int j) {
        if (i < 0 || i > len - 1 || j < 0 || j > width - 1) {
            return false;
        }
        return foodGrid[i][j];
    }

    public boolean ifHavePrey(int i, int j, String prey) {
        if (i < 0 || i > len - 1 || j < 0 || j > width - 1) {
            return false;
        } else {
            HashSet<Species> h = new HashSet();
            h.addAll(grid[i][j]);
            for (Species s: h) {
                if (prey.equals("level2") && s instanceof Predator) {
                    deleteSpecies(s, i, j);
                    return true;
                } else if (prey.equals("level3") && s instanceof Hunter) {
                    deleteSpecies(s, i, j);
                    return true;
                } else if (prey.equals("level1") && s instanceof Prey) {
                    deleteSpecies(s, i, j);
                    return true;
                }
            }
        }
        return false;
    }


    public void eatFood(int i, int j) {
        foodGrid[i][j] = false;
    }

    public void insertSpecies(Species sp, int i, int j) {
        grid[i][j].add(sp);
        allSpecies.add(sp);
        if (sp.getName() == name1) {
            num1++;
        }
        if (sp.getName() == name2) {
            num2++;
        }
        if (sp.getName() == name3) {
            num3++;
        }
    }

    public void deleteSpecies(Species sp, int i, int j) {
        grid[i][j].remove(sp);
        allSpecies.remove(sp);
        if (sp.getName() == name1) {
            num1--;
        }
        if (sp.getName() == name2) {
            num2--;
        }
        if (sp.getName() == name3) {
            num3--;
        }
    }

    public void die(Species sp) {
        allSpecies.remove(sp);
        if (sp.getName() == name1) {
            num1--;
        }
        if (sp.getName() == name2) {
            num2--;
        }
        if (sp.getName() == name3) {
            num3--;
        }
    }
}

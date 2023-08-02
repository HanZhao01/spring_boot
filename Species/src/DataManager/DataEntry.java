package DataManager;

public class DataEntry {
    private int time;
    private String name;
    private int yaxis;
    private int xaxis;
    private double energy;
    private int energyConsumption;

    public DataEntry(String name, int yaxis, int xaxis, double energy, int energyConsumption) {
        this.name = name;
        this.yaxis = yaxis;
        this.xaxis = xaxis;
        this.energy = energy;
        this.energyConsumption = energyConsumption;
    }

    public void addTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public int getYaxis() {
        return yaxis;
    }

    public int getXaxis() {
        return xaxis;
    }

    public double getEnergy() {
        return energy;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }
}


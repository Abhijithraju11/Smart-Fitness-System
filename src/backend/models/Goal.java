package models;

public class Goal {
    private int goalID;
    private String description;
    private double targetValue;
    private double currentValue;
    private String status;

    public Goal(int goalID, String description, double targetValue, double currentValue, String status) {
        this.goalID = goalID;
        this.description = description;
        this.targetValue = targetValue;
        this.currentValue = currentValue;
        this.status = status;
    }

    public void setGoal() {
        System.out.println("Goal set: " + description + " target " + targetValue);
    }

    public void updateGoal(double newValue) {
        this.currentValue = newValue;
        System.out.println("Goal updated. Current value: " + currentValue);
    }

    public double checkProgress() {
        return ((targetValue - currentValue) / targetValue) * 100;
    }
}

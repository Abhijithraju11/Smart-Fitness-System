package models;

public class Workout {
    private int workoutID;
    private String type;
    private int duration;
    private int sets;
    private int reps;
    private double caloriesBurned;

    public Workout(int workoutID, String type, int duration, int sets, int reps, double caloriesBurned) {
        this.workoutID = workoutID;
        this.type = type;
        this.duration = duration;
        this.sets = sets;
        this.reps = reps;
        this.caloriesBurned = caloriesBurned;
    }

    public void addWorkout() {
        System.out.println("Workout added: " + type + " for " + duration + " mins");
    }

    public void editWorkout() {
        System.out.println("Workout " + workoutID + " updated.");
    }
}

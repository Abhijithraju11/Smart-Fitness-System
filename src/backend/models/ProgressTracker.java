package models;

public class ProgressTracker {
    private int progressID;
    private String workoutSummary;
    private String nutritionSummary;

    public ProgressTracker(int progressID, String workoutSummary, String nutritionSummary) {
        this.progressID = progressID;
        this.workoutSummary = workoutSummary;
        this.nutritionSummary = nutritionSummary;
    }

    public void generateReport() {
        System.out.println("Report Generated: \nWorkout: " + workoutSummary + "\nNutrition: " + nutritionSummary);
    }

    public void displayProgress() {
        System.out.println("Displaying progress for ID: " + progressID);
    }

    public String getWeeklySummary() {
        return "Weekly Summary: " + workoutSummary + " | " + nutritionSummary;
    }
}

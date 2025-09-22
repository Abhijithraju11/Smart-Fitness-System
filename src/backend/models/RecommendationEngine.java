package models;

public class RecommendationEngine {
    private int engineID;
    private String goalType;

    public RecommendationEngine(int engineID, String goalType) {
        this.engineID = engineID;
        this.goalType = goalType;
    }

    public String suggestWorkoutPlan() {
        String plan = "Suggested Workout: 30 mins Cardio + Strength Training";
        System.out.println(plan);
        return plan;
    }

    public String suggestDietPlan() {
        String plan = "Suggested Diet: High protein, low carb meals";
        System.out.println(plan);
        return plan;
    }

    public String analyzeProgress() {
        String analysis = "Progress Analysis: You are on track!";
        System.out.println(analysis);
        return analysis;
    }

    public void setGoalType(String type) {
        this.goalType = type;
        System.out.println("Goal type updated to: " + goalType);
    }
}

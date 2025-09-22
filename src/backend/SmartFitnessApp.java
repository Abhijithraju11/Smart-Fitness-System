import models.*;

public class SmartFitnessApp {
    public static void main(String[] args) {
        SmartFitnessApp app = new SmartFitnessApp();
        app.runApp();
    }

    public void runApp() {
        System.out.println("🏋️ Welcome to Smart Fitness App!");
        simulateInteraction();
    }

    public void simulateInteraction() {
        User user = new User(1, "John Doe", 25, "Male", 175.0, 70.0, "johndoe", "pass123");
        user.register();

        Workout workout = new Workout(1, "Cardio", 30, 3, 15, 200);
        workout.addWorkout();

        Nutrition meal = new Nutrition(1, "Chicken Salad", 350, 30, 20, 10);
        meal.addMeal();

        Goal goal = new Goal(1, "Lose Weight", 65.0, 70.0, "In Progress");
        goal.setGoal();

        ProgressTracker tracker = new ProgressTracker(1, "30 mins Cardio", "Chicken Salad");
        tracker.generateReport();

        RecommendationEngine engine = new RecommendationEngine(1, "Weight Loss");
        engine.suggestWorkoutPlan();
    }
}

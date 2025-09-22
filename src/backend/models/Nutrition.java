package models;

public class Nutrition {
    private int nutritionID;
    private String foodItem;
    private double calorieIntake;
    private double protein;
    private double carbs;
    private double fats;

    public Nutrition(int nutritionID, String foodItem, double calorieIntake,
                     double protein, double carbs, double fats) {
        this.nutritionID = nutritionID;
        this.foodItem = foodItem;
        this.calorieIntake = calorieIntake;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
    }

    public void addMeal() {
        System.out.println("Meal added: " + foodItem + " with " + calorieIntake + " kcal");
    }

    public double calculateTotalCalories() {
        return calorieIntake;
    }
}

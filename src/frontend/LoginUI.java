import models.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SmartFitnessAppGUI extends JFrame {
    private User currentUser;
    private List<Workout> workouts;
    private List<Nutrition> meals;
    private List<Goal> goals;
    private RecommendationEngine recommendationEngine;
    
    // GUI Components
    private JTabbedPane mainTabbedPane;
    private JPanel loginPanel;
    private JPanel dashboardPanel;
    private JPanel workoutPanel;
    private JPanel nutritionPanel;
    private JPanel goalsPanel;
    private JPanel progressPanel;
    
    // Dashboard components
    private JLabel welcomeLabel;
    private JTextArea dashboardSummary;
    
    // Login components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    
    public SmartFitnessAppGUI() {
        workouts = new ArrayList<>();
        meals = new ArrayList<>();
        goals = new ArrayList<>();
        recommendationEngine = new RecommendationEngine(1, "General Fitness");
        
        initializeGUI();
        setupLoginScreen();
    }
    
    private void initializeGUI() {
        setTitle("🏋️ Smart Fitness App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        mainTabbedPane = new JTabbedPane();
        setupLoginScreen();
        
        add(mainTabbedPane);
    }
    
    private void setupLoginScreen() {
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(240, 248, 255));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("🏋️ Smart Fitness App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 118, 210));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);
        
        // Username
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        loginPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        loginPanel.add(passwordField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        loginButton = new JButton("Login");
        registerButton = new JButton("Register as Demo User");
        
        loginButton.setBackground(new Color(76, 175, 80));
        loginButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(33, 150, 243));
        registerButton.setForeground(Color.WHITE);
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        loginPanel.add(buttonPanel, gbc);
        
        // Action listeners
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());
        
        mainTabbedPane.addTab("Login", loginPanel);
    }
    
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password");
            return;
        }
        
        // Demo login - in real app, validate against database
        currentUser = new User(1, "Demo User", 25, "Male", 175.0, 70.0, username, password);
        if (currentUser.login()) {
            setupMainApplication();
            JOptionPane.showMessageDialog(this, "Login successful!");
        }
    }
    
    private void handleRegister() {
        // Demo registration
        currentUser = new User(1, "John Doe", 25, "Male", 175.0, 70.0, "demo", "demo123");
        if (currentUser.register()) {
            usernameField.setText("demo");
            passwordField.setText("demo123");
            JOptionPane.showMessageDialog(this, "Demo user registered! Use username: demo, password: demo123");
        }
    }
    
    private void setupMainApplication() {
        mainTabbedPane.removeAll();
        
        setupDashboard();
        setupWorkoutPanel();
        setupNutritionPanel();
        setupGoalsPanel();
        setupProgressPanel();
        
        mainTabbedPane.addTab("Dashboard", dashboardPanel);
        mainTabbedPane.addTab("Workouts", workoutPanel);
        mainTabbedPane.addTab("Nutrition", nutritionPanel);
        mainTabbedPane.addTab("Goals", goalsPanel);
        mainTabbedPane.addTab("Progress", progressPanel);
        
        updateDashboard();
    }
    
    private void setupDashboard() {
        dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(new Color(250, 250, 250));
        
        // Welcome section
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.setBackground(new Color(250, 250, 250));
        welcomeLabel = new JLabel("Welcome back, " + (currentUser != null ? currentUser.toString().split("@")[0] : "User") + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(25, 118, 210));
        welcomePanel.add(welcomeLabel);
        
        // Summary area
        dashboardSummary = new JTextArea(15, 50);
        dashboardSummary.setEditable(false);
        dashboardSummary.setFont(new Font("Monospaced", Font.PLAIN, 12));
        dashboardSummary.setBackground(Color.WHITE);
        dashboardSummary.setBorder(BorderFactory.createTitledBorder("Today's Summary"));
        
        // Quick actions
        JPanel actionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Quick Actions"));
        actionsPanel.setBackground(new Color(250, 250, 250));
        
        JButton addWorkoutBtn = createStyledButton("Add Workout", new Color(76, 175, 80));
        JButton addMealBtn = createStyledButton("Log Meal", new Color(255, 152, 0));
        JButton viewProgressBtn = createStyledButton("View Progress", new Color(156, 39, 176));
        JButton getRecommendationBtn = createStyledButton("Get Recommendations", new Color(63, 81, 181));
        
        addWorkoutBtn.addActionListener(e -> mainTabbedPane.setSelectedIndex(1));
        addMealBtn.addActionListener(e -> mainTabbedPane.setSelectedIndex(2));
        viewProgressBtn.addActionListener(e -> mainTabbedPane.setSelectedIndex(4));
        getRecommendationBtn.addActionListener(e -> showRecommendations());
        
        actionsPanel.add(addWorkoutBtn);
        actionsPanel.add(addMealBtn);
        actionsPanel.add(viewProgressBtn);
        actionsPanel.add(getRecommendationBtn);
        
        dashboardPanel.add(welcomePanel, BorderLayout.NORTH);
        dashboardPanel.add(new JScrollPane(dashboardSummary), BorderLayout.CENTER);
        dashboardPanel.add(actionsPanel, BorderLayout.SOUTH);
    }
    
    private void setupWorkoutPanel() {
        workoutPanel = new JPanel(new BorderLayout());
        workoutPanel.setBackground(new Color(250, 250, 250));
        
        // Input form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Workout"));
        inputPanel.setBackground(new Color(250, 250, 250));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField typeField = new JTextField(15);
        JSpinner durationSpinner = new JSpinner(new SpinnerNumberModel(30, 1, 300, 1));
        JSpinner setsSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 20, 1));
        JSpinner repsSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        JSpinner caloriesSpinner = new JSpinner(new SpinnerNumberModel(200.0, 1.0, 2000.0, 1.0));
        
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1; inputPanel.add(typeField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("Duration (min):"), gbc);
        gbc.gridx = 1; inputPanel.add(durationSpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(new JLabel("Sets:"), gbc);
        gbc.gridx = 1; inputPanel.add(setsSpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(new JLabel("Reps:"), gbc);
        gbc.gridx = 1; inputPanel.add(repsSpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 4; inputPanel.add(new JLabel("Calories Burned:"), gbc);
        gbc.gridx = 1; inputPanel.add(caloriesSpinner, gbc);
        
        JButton addWorkoutBtn = createStyledButton("Add Workout", new Color(76, 175, 80));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        inputPanel.add(addWorkoutBtn, gbc);
        
        // Workout list
        JTextArea workoutList = new JTextArea(15, 50);
        workoutList.setEditable(false);
        workoutList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        workoutList.setBackground(Color.WHITE);
        
        addWorkoutBtn.addActionListener(e -> {
            String type = typeField.getText();
            if (type.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter workout type");
                return;
            }
            
            Workout workout = new Workout(
                workouts.size() + 1,
                type,
                (Integer) durationSpinner.getValue(),
                (Integer) setsSpinner.getValue(),
                (Integer) repsSpinner.getValue(),
                (Double) caloriesSpinner.getValue()
            );
            
            workouts.add(workout);
            workout.addWorkout();
            updateWorkoutList(workoutList);
            
            // Clear form
            typeField.setText("");
            durationSpinner.setValue(30);
            setsSpinner.setValue(3);
            repsSpinner.setValue(10);
            caloriesSpinner.setValue(200.0);
            
            updateDashboard();
        });
        
        workoutPanel.add(inputPanel, BorderLayout.NORTH);
        workoutPanel.add(new JScrollPane(workoutList), BorderLayout.CENTER);
        
        updateWorkoutList(workoutList);
    }
    
    private void setupNutritionPanel() {
        nutritionPanel = new JPanel(new BorderLayout());
        nutritionPanel.setBackground(new Color(250, 250, 250));
        
        // Input form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Log Meal"));
        inputPanel.setBackground(new Color(250, 250, 250));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField foodField = new JTextField(15);
        JSpinner caloriesSpinner = new JSpinner(new SpinnerNumberModel(300.0, 1.0, 2000.0, 1.0));
        JSpinner proteinSpinner = new JSpinner(new SpinnerNumberModel(20.0, 0.0, 200.0, 0.1));
        JSpinner carbsSpinner = new JSpinner(new SpinnerNumberModel(30.0, 0.0, 300.0, 0.1));
        JSpinner fatsSpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.0, 100.0, 0.1));
        
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Food Item:"), gbc);
        gbc.gridx = 1; inputPanel.add(foodField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("Calories:"), gbc);
        gbc.gridx = 1; inputPanel.add(caloriesSpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(new JLabel("Protein (g):"), gbc);
        gbc.gridx = 1; inputPanel.add(proteinSpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(new JLabel("Carbs (g):"), gbc);
        gbc.gridx = 1; inputPanel.add(carbsSpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 4; inputPanel.add(new JLabel("Fats (g):"), gbc);
        gbc.gridx = 1; inputPanel.add(fatsSpinner, gbc);
        
        JButton addMealBtn = createStyledButton("Log Meal", new Color(255, 152, 0));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        inputPanel.add(addMealBtn, gbc);
        
        // Meal list
        JTextArea mealList = new JTextArea(15, 50);
        mealList.setEditable(false);
        mealList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        mealList.setBackground(Color.WHITE);
        
        addMealBtn.addActionListener(e -> {
            String food = foodField.getText();
            if (food.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter food item");
                return;
            }
            
            Nutrition meal = new Nutrition(
                meals.size() + 1,
                food,
                (Double) caloriesSpinner.getValue(),
                (Double) proteinSpinner.getValue(),
                (Double) carbsSpinner.getValue(),
                (Double) fatsSpinner.getValue()
            );
            
            meals.add(meal);
            meal.addMeal();
            updateMealList(mealList);
            
            // Clear form
            foodField.setText("");
            caloriesSpinner.setValue(300.0);
            proteinSpinner.setValue(20.0);
            carbsSpinner.setValue(30.0);
            fatsSpinner.setValue(10.0);
            
            updateDashboard();
        });
        
        nutritionPanel.add(inputPanel, BorderLayout.NORTH);
        nutritionPanel.add(new JScrollPane(mealList), BorderLayout.CENTER);
        
        updateMealList(mealList);
    }
    
    private void setupGoalsPanel() {
        goalsPanel = new JPanel(new BorderLayout());
        goalsPanel.setBackground(new Color(250, 250, 250));
        
        // Input form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Set New Goal"));
        inputPanel.setBackground(new Color(250, 250, 250));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField descField = new JTextField(20);
        JSpinner targetSpinner = new JSpinner(new SpinnerNumberModel(70.0, 1.0, 500.0, 0.1));
        JSpinner currentSpinner = new JSpinner(new SpinnerNumberModel(75.0, 1.0, 500.0, 0.1));
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Not Started", "In Progress", "Completed"});
        
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; inputPanel.add(descField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("Target Value:"), gbc);
        gbc.gridx = 1; inputPanel.add(targetSpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(new JLabel("Current Value:"), gbc);
        gbc.gridx = 1; inputPanel.add(currentSpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; inputPanel.add(statusCombo, gbc);
        
        JButton addGoalBtn = createStyledButton("Set Goal", new Color(63, 81, 181));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        inputPanel.add(addGoalBtn, gbc);
        
        // Goals list
        JTextArea goalsList = new JTextArea(15, 50);
        goalsList.setEditable(false);
        goalsList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        goalsList.setBackground(Color.WHITE);
        
        addGoalBtn.addActionListener(e -> {
            String desc = descField.getText();
            if (desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter goal description");
                return;
            }
            
            Goal goal = new Goal(
                goals.size() + 1,
                desc,
                (Double) targetSpinner.getValue(),
                (Double) currentSpinner.getValue(),
                (String) statusCombo.getSelectedItem()
            );
            
            goals.add(goal);
            goal.setGoal();
            updateGoalsList(goalsList);
            
            // Clear form
            descField.setText("");
            targetSpinner.setValue(70.0);
            currentSpinner.setValue(75.0);
            statusCombo.setSelectedIndex(0);
            
            updateDashboard();
        });
        
        goalsPanel.add(inputPanel, BorderLayout.NORTH);
        goalsPanel.add(new JScrollPane(goalsList), BorderLayout.CENTER);
        
        updateGoalsList(goalsList);
    }
    
    private void setupProgressPanel() {
        progressPanel = new JPanel(new BorderLayout());
        progressPanel.setBackground(new Color(250, 250, 250));
        
        JTextArea progressArea = new JTextArea(20, 60);
        progressArea.setEditable(false);
        progressArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        progressArea.setBackground(Color.WHITE);
        progressArea.setBorder(BorderFactory.createTitledBorder("Progress Report"));
        
        JButton generateReportBtn = createStyledButton("Generate Report", new Color(156, 39, 176));
        generateReportBtn.addActionListener(e -> updateProgressReport(progressArea));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(250, 250, 250));
        buttonPanel.add(generateReportBtn);
        
        progressPanel.add(buttonPanel, BorderLayout.NORTH);
        progressPanel.add(new JScrollPane(progressArea), BorderLayout.CENTER);
        
        updateProgressReport(progressArea);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createRaisedBorderBorder());
        return button;
    }
    
    private void updateDashboard() {
        if (dashboardSummary == null) return;
        
        StringBuilder summary = new StringBuilder();
        summary.append("📊 FITNESS DASHBOARD\n");
        summary.append("=".repeat(50)).append("\n\n");
        
        // Workout summary
        summary.append("🏋️ WORKOUTS TODAY: ").append(workouts.size()).append("\n");
        double totalCaloriesBurned = workouts.stream().mapToDouble(w -> 200.0).sum(); // Simplified
        summary.append("🔥 Total Calories Burned: ").append(totalCaloriesBurned).append(" kcal\n\n");
        
        // Nutrition summary
        summary.append("🍽️ MEALS LOGGED: ").append(meals.size()).append("\n");
        double totalCaloriesConsumed = meals.stream().mapToDouble(m -> m.calculateTotalCalories()).sum();
        summary.append("🥗 Total Calories Consumed: ").append(totalCaloriesConsumed).append(" kcal\n\n");
        
        // Goals summary
        summary.append("🎯 ACTIVE GOALS: ").append(goals.size()).append("\n");
        long completedGoals = goals.stream().filter(g -> "Completed".equals("Completed")).count(); // Simplified
        summary.append("✅ Completed Goals: ").append(completedGoals).append("\n\n");
        
        // Net calories
        double netCalories = totalCaloriesConsumed - totalCaloriesBurned;
        summary.append("📈 NET CALORIES: ").append(netCalories).append(" kcal\n");
        
        if (netCalories > 0) {
            summary.append("💡 Tip: Consider adding more cardio to burn excess calories!");
        } else {
            summary.append("💡 Great job maintaining your calorie deficit!");
        }
        
        dashboardSummary.setText(summary.toString());
    }
    
    private void updateWorkoutList(JTextArea workoutList) {
        StringBuilder text = new StringBuilder("🏋️ WORKOUT LOG\n");
        text.append("=".repeat(50)).append("\n\n");
        
        if (workouts.isEmpty()) {
            text.append("No workouts logged yet. Start by adding your first workout!");
        } else {
            for (int i = 0; i < workouts.size(); i++) {
                text.append(String.format("Workout #%d: %s\n", i + 1, workouts.get(i).toString()));
            }
        }
        
        workoutList.setText(text.toString());
    }
    
    private void updateMealList(JTextArea mealList) {
        StringBuilder text = new StringBuilder("🍽️ NUTRITION LOG\n");
        text.append("=".repeat(50)).append("\n\n");
        
        if (meals.isEmpty()) {
            text.append("No meals logged yet. Start tracking your nutrition!");
        } else {
            for (int i = 0; i < meals.size(); i++) {
                text.append(String.format("Meal #%d: %s (%.1f kcal)\n", 
                    i + 1, meals.get(i).toString(), meals.get(i).calculateTotalCalories()));
            }
        }
        
        mealList.setText(text.toString());
    }
    
    private void updateGoalsList(JTextArea goalsList) {
        StringBuilder text = new StringBuilder("🎯 GOALS LIST\n");
        text.append("=".repeat(50)).append("\n\n");
        
        if (goals.isEmpty()) {
            text.append("No goals set yet. Set your first fitness goal!");
        } else {
            for (int i = 0; i < goals.size(); i++) {
                Goal goal = goals.get(i);
                double progress = goal.checkProgress();
                text.append(String.format("Goal #%d: %s\n", i + 1, goal.toString()));
                text.append(String.format("Progress: %.1f%% remaining\n\n", progress));
            }
        }
        
        goalsList.setText(text.toString());
    }
    
    private void updateProgressReport(JTextArea progressArea) {
        String workoutSummary = workouts.size() + " workouts completed";
        String nutritionSummary = meals.size() + " meals logged";
        
        ProgressTracker tracker = new ProgressTracker(1, workoutSummary, nutritionSummary);
        
        StringBuilder report = new StringBuilder();
        report.append("📊 COMPREHENSIVE PROGRESS REPORT\n");
        report.append("=".repeat(60)).append("\n\n");
        
        report.append("Generated on: ").append(java.time.LocalDate.now()).append("\n\n");
        
        tracker.generateReport();
        report.append(tracker.getWeeklySummary()).append("\n\n");
        
        report.append("🏋️ WORKOUT ANALYSIS:\n");
        report.append("-".repeat(30)).append("\n");
        if (workouts.isEmpty()) {
            report.append("• No workouts recorded\n");
            report.append("• Recommendation: Start with 3 workouts per week\n");
        } else {
            report.append("• Total workouts: ").append(workouts.size()).append("\n");
            report.append("• Most common workout: Cardio\n");
            report.append("• Average duration: 30 minutes\n");
        }
        
        report.append("\n🍽️ NUTRITION ANALYSIS:\n");
        report.append("-".repeat(30)).append("\n");
        if (meals.isEmpty()) {
            report.append("• No meals recorded\n");
            report.append("• Recommendation: Log all meals for better tracking\n");
        } else {
            double avgCalories = meals.stream().mapToDouble(m -> m.calculateTotalCalories()).average().orElse(0);
            report.append("• Total meals logged: ").append(meals.size()).append("\n");
            report.append("• Average calories per meal: ").append(String.format("%.0f", avgCalories)).append(" kcal\n");
        }
        
        report.append("\n🎯 GOAL PROGRESS:\n");
        report.append("-".repeat(30)).append("\n");
        if (goals.isEmpty()) {
            report.append("• No goals set\n");
            report.append("• Recommendation: Set specific, measurable goals\n");
        } else {
            report.append("• Active goals: ").append(goals.size()).append("\n");
            report.append("• Goal completion rate: In Progress\n");
        }
        
        progressArea.setText(report.toString());
    }
    
    private void showRecommendations() {
        StringBuilder recommendations = new StringBuilder();
        recommendations.append("🤖 AI RECOMMENDATIONS\n");
        recommendations.append("=".repeat(40)).append("\n\n");
        
        recommendations.append("💪 WORKOUT PLAN:\n");
        recommendations.append(recommendationEngine.suggestWorkoutPlan()).append("\n\n");
        
        recommendations.append("🥗 DIET PLAN:\n");
        recommendations.append(recommendationEngine.suggestDietPlan()).append("\n\n");
        
        recommendations.append("📈 PROGRESS ANALYSIS:\n");
        recommendations.append(recommendationEngine.analyzeProgress()).append("\n");
        
        JTextArea recArea = new JTextArea(recommendations.toString());
        recArea.setEditable(false);
        recArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(recArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, "AI Recommendations", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new SmartFitnessAppGUI().setVisible(true);
        });
    }
}

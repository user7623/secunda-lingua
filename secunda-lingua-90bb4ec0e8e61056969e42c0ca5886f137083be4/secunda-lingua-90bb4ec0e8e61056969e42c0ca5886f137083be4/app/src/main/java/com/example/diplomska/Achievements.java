package com.example.diplomska;

public class Achievements {

    public static String[] Achievement = {"Completed 3 exercises", "Completed more than 6 exercises", "More than 1000 points", "More than 1200 points", "All achievements!"};
    public static String[] AchievementDesc = {"Good Job!", "Started learning!", "Great success!", "Hardworking", "Knowing the basics!"};
    public static int[] AchievementImage = {R.drawable.ic_locked ,R.drawable.icon_achievements, R.drawable.ic_motivation, R.drawable.ic_book, R.drawable.play_sound_icon, R.drawable.icon_achievements};

    public static String[] getAchievement() {
        return Achievement;
    }

    public static String[] getAchievementDesc() {
        return AchievementDesc;
    }

    public static int[] getAchievementImage() {
        return AchievementImage;
    }

}

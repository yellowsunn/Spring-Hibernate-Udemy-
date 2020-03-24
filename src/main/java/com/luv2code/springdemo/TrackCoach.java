package com.luv2code.springdemo;

public class TrackCoach implements Coach {
    private FortuneService fortuneService;

    public TrackCoach() {
    }

    public TrackCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    public String getDailyWorkout() {
        return "Run a hard 5k";
    }

    public String getDailyFortune() {
        return "Just Do It: " + fortuneService.getFortune();
    }

    // add an init method
    public void doMyStartUpStuff() {
        System.out.println("TrackCoach: inside method doMyStartUpStuff");
    }

    // add a destroy method
    public void doMyCleanUpStuffYoYo() {
        System.out.println("TrackCoach: inside method doMyCleanUpStuffYoYo");
    }
}

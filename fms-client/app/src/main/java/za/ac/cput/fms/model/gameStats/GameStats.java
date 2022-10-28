package za.ac.cput.fms.model.gameStats;

public class GameStats {

    private String id;
    private String fixtureId;
    private String teamId;
    private String teamName;
    private int goalsScored;
    private int goalsConceded;
    private int corners;
    private int yellowCards;
    private int redCards;
    private int freeKicks;

    public GameStats(Builder builder) {
        this.id = builder.id;
        this.teamName = builder.teamName;
        this.fixtureId = builder.fixtureId;
        this.teamId = builder.teamId;
        this.goalsScored = builder.goalsScored;
        this.goalsConceded = builder.goalsConceded;
        this.corners = builder.corners;
        this.yellowCards = builder.yellowCards;
        this.redCards = builder.redCards;
        this.freeKicks = builder.freeKicks;
    }

    public GameStats() {

    }

    public String getId() {
        return id;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getFixtureId() {
        return fixtureId;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public int getCorners() {
        return corners;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public int getFreeKicks() {
        return freeKicks;
    }

    public static class Builder{
        private String id;
        private String fixtureId;
        private String teamId;
        private String teamName;
        private int goalsScored;
        private int goalsConceded;
        private int corners;
        private int yellowCards;
        private int redCards;
        private int freeKicks;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setFixtureId(String fixtureId) {
            this.fixtureId = fixtureId;
            return this;
        }

        public Builder setTeamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public Builder setTeamId(String teamId) {
            this.teamId = teamId;
            return this;
        }

        public Builder setGoalsScored(int goalsScored) {
            this.goalsScored = goalsScored;
            return this;
        }

        public Builder setGoalsConceded(int goalsConceded) {
            this.goalsConceded = goalsConceded;
            return this;
        }

        public Builder setCorners(int corners) {
            this.corners = corners;
            return this;
        }

        public Builder setYellowCards(int yellowCards) {
            this.yellowCards = yellowCards;
            return this;
        }

        public Builder setRedCards(int redCards) {
            this.redCards = redCards;
            return this;
        }

        public Builder setFreeKicks(int freeKicks) {
            this.freeKicks = freeKicks;
            return this;
        }

        public Builder copy(GameStats gs){
            this.id = gs.id;
            this.fixtureId = gs.fixtureId;
            this.teamName = gs.teamName;
            this.teamId = gs.teamId;
            this.goalsScored = gs.goalsScored;
            this.goalsConceded = gs.goalsConceded;
            this.corners = gs.corners;
            this.yellowCards = gs.yellowCards;
            this.redCards = gs.redCards;
            this.freeKicks = gs.freeKicks;
            return this;
        }

        public GameStats build(){
            return new GameStats(this);
        }
    }
}

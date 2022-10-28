package za.ac.cput.fms.model.playerStats;

public class PlayerStats {

    private String id;
    private String fixtureId;
    private String teamId;
    private String playerId;
    private String playerName;
    private int goalsScored;
    private int assistsMade;
    private int goalsSaved;

    public PlayerStats(Builder builder) {
        this.id = builder.id;
        this.playerName = builder.playerName;
        this.fixtureId = builder.fixtureId;
        this.teamId = builder.teamId;

        this.playerId = builder.playerId;
        this.goalsScored = builder.goalsScored;
        this.assistsMade = builder.assistsMade;
        this.goalsSaved = builder.goalsSaved;
    }

    public PlayerStats() {

    }

    public String getId() {
        return id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getFixtureId() {
        return fixtureId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getTeamId() {
        return teamId;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public int getAssistsMade() {
        return assistsMade;
    }

    public int getGoalsSaved() {
        return goalsSaved;
    }

    public static class Builder{

        private String id;
        private String fixtureId;
        private String teamId;
        private String playerName;
        private String playerId;
        private int goalsScored;
        private int assistsMade;
        private int goalsSaved;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setFixtureId(String fixtureId) {
            this.fixtureId = fixtureId;
            return this;
        }

        public Builder setPlayerName(String playerName) {
            this.playerName = playerName;
            return this;
        }

        public Builder setTeamId(String teamId) {
            this.teamId = teamId;
            return this;
        }

        public Builder setPlayerId(String playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder setGoalsScored(int goalsScored) {
            this.goalsScored = goalsScored;
            return this;
        }

        public Builder setAssistsMade(int assistsMade) {
            this.assistsMade = assistsMade;
            return this;
        }

        public Builder setGoalsSaved(int goalsSaved) {
            this.goalsSaved = goalsSaved;
            return this;
        }

        public Builder copy(PlayerStats playerStats){
            this.id = playerStats.id;
            this.fixtureId = playerStats.fixtureId;
            this.playerName = playerStats.playerName;
            this.teamId = playerStats.teamId;
            this.playerId = playerStats.playerId;
            this.goalsScored = playerStats.goalsScored;
            this.assistsMade = playerStats.assistsMade;
            this.goalsSaved = playerStats.goalsSaved;
            return this;
        }

        public PlayerStats build(){
            return new PlayerStats(this);
        }

    }
}
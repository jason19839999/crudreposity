package datacenter.crudreposity.entity;

public class RedisScoreValue {
    private String value;
    private double score;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

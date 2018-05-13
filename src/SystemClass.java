import java.time.Instant;

public class SystemClass {
private Instant actualTimer;
private long duration = 1800;                //number of seconds they have for the task as default if not set up in input file

    public void setActualTimer() {
        this.actualTimer = Instant.now();;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Instant getActualTimer() {
        return actualTimer;
    }

    public long getDuration() {
        return duration;
    }
}

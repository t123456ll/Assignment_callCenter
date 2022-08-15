import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Call {
    @NotNull(message = "call duration cannot be null")
    @Min(value = 0, message = "call duration cannot be negative")
    private int duration;

    public Call(int duration) {
        super();
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    public static Call buildACall(int maxDuration) {
        return new Call(ThreadLocalRandom.current().nextInt(1,maxDuration + 1));
    }

    public static List<Call> buildListOfRandomCalls(int listSize, int maxDuration){
        List<Call> callList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            callList.add(buildACall(maxDuration));
        }
        return callList;
    }
}

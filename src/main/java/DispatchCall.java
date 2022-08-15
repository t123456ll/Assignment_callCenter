import org.apache.commons.lang3.Validate;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DispatchCall implements Runnable{
    public static final Integer MAX_THREADS = 10;

    private Boolean activeFlag;
    private ExecutorService executorService;
    private ConcurrentLinkedDeque<Staffer> staffers;
    private CallHandler callHandler;
    private ConcurrentLinkedDeque<Call> incomingCalls;


    public DispatchCall(@NotNull List<Staffer> staffers, @NotNull CallHandler callHandler) {
        this.staffers = new ConcurrentLinkedDeque(staffers);
        this.callHandler= callHandler;
        this.incomingCalls = new ConcurrentLinkedDeque<>();
        this.executorService = Executors.newFixedThreadPool(MAX_THREADS);
    }

    public synchronized void receiveCall(Call call) {
        System.out.println("Receive a call with a duration of " + call.getDuration() + " seconds");
        this.incomingCalls.add(call);
    }

    public synchronized void start() {
        this.activeFlag = true;
        for (Staffer staffer : this.staffers) {
            this.executorService.execute(staffer);
        }
    }

    public synchronized void pause() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public synchronized void stop() {
        this.activeFlag = false;
        this.executorService.shutdown();
    }

    public synchronized Boolean getActiveFlag() {
        return activeFlag;
    }


    @Override
    public void run() {
        while (getActiveFlag()) {
            pause(); // add the interval to prevent generating too many logs
            if (!this.incomingCalls.isEmpty()){
                System.out.println("Trying to find someone to address the call");
                Staffer staffer = this.callHandler.findCallHandler(this.staffers);
                if (staffer == null) {
                    continue;
                }
                Call call = this.incomingCalls.poll();
                try {
                    staffer.receiveCall(call);
                } catch (Exception e) {
                    System.out.println("something wrong, please wait again");
                    this.incomingCalls.addFirst(call);
                }
            } else { // no call need to dispatch, will close this dispatcher
                System.out.println("------dispatched all calls-------");
                stop();
            }
        }
    }


}

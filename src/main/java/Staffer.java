import enums.StafferRank;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

public class Staffer implements Runnable{
    private String id;
    private StafferRank stafferRank;
    private boolean isFree;

    private ConcurrentLinkedDeque<Call> waitingCallsList;

    public Staffer(StafferRank stafferRank, String id) {
        super();
        this.id = id;
        this.stafferRank = stafferRank;
        this.isFree = true;
        this.waitingCallsList = new ConcurrentLinkedDeque<>();
    }

    public static String createId () {
        return RandomStringUtils.randomNumeric(3);
    }

    public static Staffer buildEmployee() {
        return new Staffer(StafferRank.EMPLOYEE, createId());
    }

    public static Staffer buildManager() {
        return new Staffer(StafferRank.SUPERVISOR, createId());
    }

    public static Staffer buildDirector() {
        return new Staffer(StafferRank.MANAGER, createId());
    }

    public StafferRank getRank() {
        return stafferRank;
    }

    public String getId() {
        return id;
    }

    // to handle the concurrency
    public synchronized Boolean getStatus() {
        return isFree;
    }

    public synchronized void setStafferStatus(boolean isFree) {
        System.out.println("ID: " + this.getId() + " " + this.getRank() + " change his availability to: " + isFree);
        this.isFree = isFree;
    }

    public synchronized void receiveCall (Call call) {
        System.out.println("ID: " + this.getId() + " " + this.getRank() + " is receiving a call");
        this.waitingCallsList.add(call);
    }

    @Override
    public void run() {
        System.out.println("ID: " + this.getId() + " " + this.getRank() + " is ready");
        while (true) {
            if (!this.waitingCallsList.isEmpty()) {
                this.setStafferStatus(false);
                Call call = this.waitingCallsList.poll();
                System.out.println("ID: " + this.getId() + " " + this.getRank() + " will need " + call.getDuration() + " seconds to address");
                try {
                    TimeUnit.SECONDS.sleep(call.getDuration());
                } catch (InterruptedException e) {
                    System.out.println("interrupted, cannot finish the call");
                } finally {
                    System.out.println("ID: " + this.getId() + " " + this.getRank() + " finish the call");
                    this.setStafferStatus(true);
                }
            }

        }

    }


}

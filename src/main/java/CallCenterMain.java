import java.util.ArrayList;
import java.util.List;

public class CallCenterMain {
    private static DispatchCall dispatchCall;
    private static ArrayList<Staffer> staffers = new ArrayList<>();

    public static final Integer NUM_CALLS = 11;
    public static final Integer MAX_CALL_DURATION = 15;
    public static final Integer NUM_EMPLOYEE = 5;


    public static void main(String[] args) throws InterruptedException {
        setupStaffers();
        dispatchCall = new DispatchCall(staffers, new CallHandler());
        List<Call> callList = Call.buildListOfRandomCalls(NUM_CALLS, MAX_CALL_DURATION);
        for (Call call: callList) {
            dispatchCall.receiveCall(call);
        }
        dispatchCall.start();
        Thread t = new Thread(dispatchCall, "dispatch Call thread");
        t.start();
        t.join();
        System.out.println("------finish dispatching------");
    }



    public static void setupStaffers(){
        for(int i=0; i<NUM_EMPLOYEE; i++){  // add five employees
            Staffer employee = Staffer.buildEmployee();
            staffers.add(employee);
        }
        Staffer director = Staffer.buildDirector(); // add one Director
        staffers.add(director);
        Staffer manger = Staffer.buildManager(); // add one Manger
        staffers.add(manger);
    }
}

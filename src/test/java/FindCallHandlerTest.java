import enums.StafferRank;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class FindCallHandlerTest extends BaseTest{
    protected static CallHandler callHandler = new CallHandler();

    @Test
    public void noStaffers_ShouldNoStaffers(){
        ArrayList<Staffer> staffers = setupStaffers(0, 0, 0);
        Staffer staffer = callHandler.findCallHandler(staffers);
        assertTrue(staffer == null);
    }

    @Test
    public void supervisorOnly_ShouldReturnSupervisor(){
        ArrayList<Staffer> staffers = setupStaffers(0, 5, 0);
        Staffer staffer = callHandler.findCallHandler(staffers);
        assertEquals(StafferRank.SUPERVISOR, staffer.getRank());
    }

    @Test
    public void managerOnly_ShouldReturnManager(){
        ArrayList<Staffer> staffers = setupStaffers(0, 0, 5);
        Staffer staffer = callHandler.findCallHandler(staffers);
        assertEquals(StafferRank.MANAGER, staffer.getRank());
    }

    @Test
    public void busyStaffers_ShouldNoOne(){
        ArrayList<Staffer> staffers = setupStaffers(5, 1, 1);
        staffers = setupBusyStaffers(staffers, true, true, true);
        Staffer staffer = callHandler.findCallHandler(staffers);
        assertTrue(staffer == null);
    }

    @Test
    public void busyEmployeesAndSupervisor_ShouldReturnManager(){
        ArrayList<Staffer> staffers = setupStaffers(5, 1, 1);
        staffers = setupBusyStaffers(staffers,true, true, false);
        Staffer staffer = callHandler.findCallHandler(staffers);
        assertEquals(StafferRank.MANAGER, staffer.getRank());
    }

    @Test
    public void busyEmployees_ShouldReturnSupervisor(){
        ArrayList<Staffer> staffers = setupStaffers(5, 1, 1);
        staffers = setupBusyStaffers(staffers, true, false, false);
        Staffer staffer = callHandler.findCallHandler(staffers);
        assertEquals(StafferRank.SUPERVISOR, staffer.getRank());
    }

    @Test(expected = NullPointerException.class)
    public void InvalidStaffers_ShouldThrowError(){
        callHandler.findCallHandler(null);
    }
}

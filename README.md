# Call Center

The main class is: CallCenterMain

The default setting is: 11 calls, 5 employees, 1 supervisor, 1 manager

 

To run unit test: 

```
mvn test
```

### Functionality for each class
Call: for generating one or a list of calls with random duration

CallHandler: for finding the available staffer to handle the call

DispatchCall: for receiving and dispatching calls. Its instance is a thread and can work parallel

Staffer: for creating each staffer, and mimic the real staffer work flow. 
receive call -> change availability -> answer call -> finish call -> change availability -> done.
Its instance is a thread and can work parallel

CallCenterMain: The main class to integrate all parts above. It set up staffers and initialize the 
dispatchCall thread.

### How to design

My goal in designing a call center is to keep the essential attributes and discard attributes that don't participate in the functionality. So for designing a staffer, the real world may need his gender, address and so on, but here I only set the attributes that help with the calculation. id and rank for knowing who he is; isFree to reflect his availability; waitingCallList for storing the call before addressing.

To handle the ambiguity, I need to make sure what is the size of this call center (How many employees, How many calls per day). Does any scaling issue need to be considered? According to your answer, this is quite small so no load and pressure issue will be considered and the question become to be more simple. Plus, I need to know the findCallHander strategy better, So I ask How to determine if they can handle the call or not? & How to handle a call if there is no one is free to answer it? To make sure is there any priority/risk level for each call like the call can only be addressed by the manager? The answer is said availability is the only thing that needs to be thought about, so the findCallHander only filters staffers by their availability.



### How to consider concurrency
1. Determine what is the resource and what is the thread: 

   Calls are resources because calls are the target for addressing

   Employees are threads because employees work parallel

   Dispatcher is thread as well because needing to allocate calls in the meantime

2. How to keep listening?
   Staffer: when the waitingCallList got a call, the staffer will start to address it. so using while (true) + if(!this.waitingCallsList.isEmpty()) to enter the handling loop

   DispatchCall: when the incomingCalls List got calls, the dispatcher will start to allocate calls. so using while (true) + if(!this.incomingCalls.isEmpty()) to enter the dispatching loop. But I don't want to get too many logs to burn the terminal. so adding a flag here to tell the thread when should start

3. How to start a thread

   We should have multiple threads for each staffer and I also don't want to create + start + terminate them one by one, it will cost too much. So I used thread pool to help with management


### Reference
1. crack the coding interview
2. 

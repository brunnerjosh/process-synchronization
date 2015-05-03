// -----------------------------------------------------------------------------
// ---------------------------- Written by Josh Brunner ------------------------
// ----------------------- for CSS 430 HW3/pt.1  Assignment --------------------
// -------------------------- Last modified: 5/14/2014 -------------------------
// ------------------------------- QueueNode.java ------------------------------
/*
 * PURPOSE OF FILE
 * This file is responsible for sleeping and waking threads based upon a certain
 * condition as per designated by the calling thread. It has a container to hold
 * their conditions, a Vector. Based upon the function mutating the Vector, 
 * conditions are added and removed during the syncronized block.
 *
 * ASSUMPTIONS
 * The user has all of the appropriate classes within the right location. This 
 * class is designed to work alongside SyncQueue.java. 
 */
import java.util.*;

public class QueueNode {
    
    private Vector<Integer> queue;
    
    // -------------------------------------------------------------------------
    // Constructor
    /*
     * SUMMARY
     * This constructor simply initializes the "queue" object with a new Vector
     */     
    public QueueNode(){ queue = new Vector<>(); }       //queue of thread IDs
    
    // -------------------------------------------------------------------------
    // sleep
    /*
     * SUMMARY
     * The sleep function is responsible for putting the current thread to sleep
     * using the "wait()" function. After an attempt at a wait() has been done, 
     * the condition is removed from the vector's condition list and returned.
     */     
    public synchronized int sleep( ) {
        if(queue.size() == 0){ 
            try {
                wait( );                                //put it to sleep

            } catch ( InterruptedException e ) {   
                SysLib.cerr( e.toString( ) + "\n" );    //print out the error
            }
            return queue.remove(0);                     //remove this from queue
        }
        return -1;
    }
    
    // -------------------------------------------------------------------------
    // wakeup
    /*
     * SUMMARY
     * The wakeup() function is responsible for enqueuing (adding) the current
     * thread's ID in this vector queue of conditions.
     */     
    public synchronized void wakeup(int tid){
        queue.add(tid);                                 //add this to the queue 
        notify();                                       //wake up this thread
    }
}

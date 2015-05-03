// -----------------------------------------------------------------------------
// ---------------------------- Written by Josh Brunner ------------------------
// ----------------------- for CSS 430 HW3/pt.1  Assignment --------------------
// -------------------------- Last modified: 5/14/2014 -------------------------
// ------------------------------- SyncQueue.java ------------------------------
/*
 * PURPOSE OF FILE
 * This file is responsible for enabling threads to be able to wait for one of
 * their child threads to be terminated before continuing execution. This file
 * MUST be used in conjunction with QueueNode.java so that threads can wait on 
 * a number of conditions. See QueueNode.java's source code for more details.
 *
 * BRIEF NOTE
 * Although the instructions say to increase X up to 3 or 4 to be able to 
 * observe the clear difference between the interruptible and busy-wait I/Os, 
 * I found that the best example of a speed increase is displayed when X is 
 * equal to greater than 6.
 *
 * ASSUMPTIONS
 * 1. This class must be used in conjunction with QueueNode.java
 * 2. The user understands that SyncQueue has two instructors; one takes no 
 *    arguments and sets the maximum amount of conditions a thread can wait on 
 *    to DEFAULT_CONDITION while the other constructor can take a custom amount 
 *    of conditions as per defined by its maxConditions variable.
 */
import java.util.*;

public class SyncQueue {
    private QueueNode[] queue;
    private static int DEFAULT_TID = 0;
    private static int DEFAULT_CONDITION = 10;
    
    // -------------------------------------------------------------------------
    // Constructors + SyncHelper
    /*
     * SUMMARY
     * These two constructors are responsbile for initializing a SyncQueue 
     * object upon being called. These constructors have two variations. One 
     * sets the QueueNode array to a variable set by DEFAULT_CONDITION. The 
     * other sets the QueueNode array to a specific size as per set by the 
     * calling function. They utilize a helper function to shorten the amount 
     * of code being processed.
     */    
    public SyncQueue(){ syncHelper(DEFAULT_CONDITION); }
    public SyncQueue(int maxConditions){ syncHelper(maxConditions); }

    public void syncHelper(int maxSize){
        queue = new QueueNode[maxSize];                 //set queue's array size 
        for(int i = 0; i < maxSize; i++){
            queue[i] = new QueueNode();                 //initialize a QueueNode
        }
    }

    // -------------------------------------------------------------------------
    // enqueueAndSleep
    /*
     * SUMMARY
     * This function is responsible for ensuring that we are staying within the 
     * boudns of the "queue" array. If so, it sleeps the thread at waiting at 
     * that condition location in the array.
     */  
    public int enqueueAndSleep(int condition){
        if(condition < queue.length){
            if(condition >= 0){
                return queue[condition].sleep();        //sleep this thread
            }
        }
        return -1;
    }

    // -------------------------------------------------------------------------
    // dequeueAndWakeup + wakeupHelper
    /*
     * SUMMARY
     * These two functions are responsible for waking up a sleeping thread from 
     * a certain location in an array of other conditions. This helper 
     * function's code checks the bounds of the array against the condition. If 
     * it meets the requirements, it hands in the thread ID variable "tid" to 
     * the wakeup() function. They both utilize a helper function called 
     * wakeupHelper to, once again, shorten the amount of repetitive code. 
     */  
    public void dequeueAndWakeup(int condition){
        wakeupHelper(condition, DEFAULT_TID);           //call the helper
    }
    
    public void dequeueAndWakeup(int condition, int tid){
        wakeupHelper(condition, tid);                   //call the helper
    }
    private void wakeupHelper(int condition, int tid){
        if(condition >= 0){ 
            if(condition < queue.length){
                queue[condition].wakeup(tid);           //wake up this thread
            }
        }
    }
}

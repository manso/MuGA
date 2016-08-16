/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.solver;

import com.evolutionary.solver.EAsolver;
import javax.swing.event.EventListenerList;

/**
 *
 * @author antoniomanso
 */
public class UiSolver implements Runnable {

    EAsolver mySolver; // solver / array of solvers
    private int timeToSleep; // thread sleep
    Thread autorun = null; // thread

    public EAsolver getMySolver() {
        return mySolver;
    }

    public void setMySolver(EAsolver mySolver) {
        this.mySolver = mySolver;
    }

    public EventListenerList getListenerList() {
        return listeners;
    }

    public void setListenerList(EventListenerList ListenerList) {
        this.listeners = ListenerList;
    }

    public void start() {
        if (autorun == null && mySolver != null) {
            autorun = new Thread(this);
            autorun.start();
        } else {
            stop();
        }
    }

    public void stop() {
        if (autorun != null) {
            autorun.interrupt();
            autorun = null;
        }
    }

    public void iterate() {
        //solver not done
        if (!mySolver.isDone()) {
            //next generation
            mySolver.iterate();
            //notify changes
            fireEvolutionChanges(mySolver);
            //solver done
            if (mySolver.isDone()) {
                //notify complete
                fireEvolutionComplete(mySolver);
            }
        }
    }

    @Override
    public String toString() {
        return mySolver.toString();
    }

    @Override
    public void run() {
        while (autorun != null && !mySolver.isDone()) {
            try {
                iterate();
                Thread.sleep(getTimeToSleep());
            } catch (Exception ex) {
                return; // thread abborted 
            }

        }
        //clean autorun
        autorun = null;
    }

    public boolean isRunning() {
        return autorun != null;
    }

    public boolean isStopped() {
        return mySolver == null || mySolver.stop.isDone(mySolver);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//:::::::::::::                E V E  N T   L I S T E N E R  ::::::::::::::::::
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
    /**
     * List of Listeners
     */
    protected EventListenerList listeners = new EventListenerList();

    /**
     * Add an event listener to the list.
     *
     * @param Listener the listener
     */
    public void addListener(EvolutionEventListener Listener) {
        listeners.add(EvolutionEventListener.class, Listener);
    }

    /**
     * Fire the event, tell everyone that something has happened.
     *
     * @param obj
     */
    public void fireEvolutionChanges(EAsolver obj) {
        Object[] Listeners = listeners.getListenerList();
        // Process each of the event listeners in turn.
        for (int i = 0; i < Listeners.length; i++) {
            if (Listeners[i] == EvolutionEventListener.class) {
                ((EvolutionEventListener) Listeners[i + 1]).onEvolutionChanges(obj);
            }
        }
    }

    /**
     * Fire the event, tell everyone that something has happened.
     *
     * @param obj
     */
    public void fireEvolutionComplete(EAsolver obj) {
        Object[] Listeners = listeners.getListenerList();
        // Process each of the event listeners in turn.
        for (int i = 0; i < Listeners.length; i++) {
            if (Listeners[i] == EvolutionEventListener.class) {
                ((EvolutionEventListener) Listeners[i + 1]).onEvolutionComplete(obj);
            }
        }
    }
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 

    /**
     * @return the timeToSleep
     */
    public int getTimeToSleep() {
        return timeToSleep;
    }

    /**
     * @param timeToSleep the timeToSleep to set
     */
    public void setTimeToSleep(int timeToSleep) {
        this.timeToSleep = timeToSleep;
    }

}

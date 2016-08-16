//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2016   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package zzz_test.events;

/**
 * Created on 14/mar/2016, 4:44:18
 *
 * @author zulu - computer
 */
// Import the required API classes.
import javax.swing.event.EventListenerList;
// An event class manages the event. It provides the
// means for adding and removing listeners and also
// firing the event when it happens.

public class SampleEventClass {
// Create a variable to hold a list of listeners,
// which are applications that want to know
// about the event.

    protected EventListenerList ListenerList = new EventListenerList();
// Add an event listener to the list.

    public void AddEventListener(MyEventListener Listener) {
        ListenerList.add(MyEventListener.class,
                Listener);
    }
// Remove an event listener from the list.

    public void RemoveEventListener(MyEventListener Listener) {
        ListenerList.remove(MyEventListener.class,
                Listener);
    }
// Fire the event, tell everyone that something has
// happened.

    public void FireEvent(MyEvent Event) {
// The event manager must tell each of the
// listeners in turn, so you need a list of
// listeners.
        Object[] Listeners = ListenerList.
                getListenerList();
// Process each of the event listeners in turn.
        for (int i = 0; i < Listeners.length; i += 2) {
// An event list could contain any number of
// events,so you need to ensure you
// process only the listeners that want to
// hear about this particular event.
            if (Listeners[i] == MyEventListener.class) {
// Fire the EventHappened method using the
// event argument supplied by the caller.
                ((MyEventListener) Listeners[i + 1]).
                        EventHappened(Event);
            }
        }
    }
}

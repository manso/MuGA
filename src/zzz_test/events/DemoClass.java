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
 * Created on 14/mar/2016, 4:47:07
 *
 * @author zulu - computer
 */
public class DemoClass {

// The event manager class isn't the same as an
// event manager. DoEvents is the object that
// actually manages the event for this class.
    private SampleEventClass DoEvents = new SampleEventClass();

    public DemoClass() {
// You must add each object created from this class
// to the event listener list. Each object will
// receive a separate notification.
        DoEvents.AddEventListener(new MyEventListener() {
            public void EventHappened(MyEvent Event) {
                if ((int) Event.getSource() >= 4) {
                    System.out.println("Number is too high!\n");
                }

                if ((int) Event.getSource() <= 0) {
                    System.out.println("Number is too low!\n");
                }
            }
        });
    }
// Create a field to manipulate for this class. The
// field could represent any data.
    private int Value = 0;
// Obtain the current value of the field.

    public int getValue() {
        return Value;
    }
// Set the value of the field directly.

    public void setValue(int Value) {
// Create an event object that contains
// the value that the caller attempted to
// use.
        MyEvent Event = new MyEvent(Value);
// When the caller tries to set the value too
// high or too low, the class must fire the
// event. Otherwise, it can change the contents
// of Value.
        if ((Value <= 3) & (Value > 0)) {
            this.Value = Value;
        } else {
            DoEvents.FireEvent(Event);
        }
    }

    public void AddOne() {
// Create an event object containing the
// current contents of Value.
        MyEvent Event = new MyEvent(Value);
// Make sure that Value isn't getting too
// high. If so, fire an event.
        if (Value <= 3) {
            Value++;
        } else {
            DoEvents.FireEvent(Event);
        }
    }

    public void SubtractOne() {
// Create an event object containing the
// current contents of Value.
        MyEvent Event = new MyEvent(Value);
// Make sure that Value isn't getting too
// low. If so, fire an event.
        if (Value > 0) {
            Value--;
        } else {
            DoEvents.FireEvent(Event);
        }
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603140447L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}

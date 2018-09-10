/*
 * EventBus.java
 * <daniel.stieger@modellwerkstatt.org>
 *
 *
 * VertX 3 EventBus client in plain java. This is the public API of this eventbus client.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.modellwerkstatt.javaxbus;


import mjson.Json;



public class EventBus {
    private Thread communicatorThread;
    private EventBusCom com;

    public EventBus(){

    }


    public void consumer(String address, ConsumerHandler<Json> handler) {
        if (com == null) {
            throw new IllegalStateException("Eventbus not initialized.");
        }
        com.registerHander(address, handler);
    }

    public void unregisgter(String address, ConsumerHandler<Json> handler) {
        if (com == null) {
            throw new IllegalStateException("Eventbus not initialized.");
        }
        com.unRegisterHander(address, handler);
    }

    public void send(String adr, Json obj){
        if (com == null) {
            throw new IllegalStateException("Eventbus not initialized.");
        }
        com.sendToStream(false, adr, obj, null);
    }
    public void send(String adr, Json obj, String reply){
        if (com == null) {
            throw new IllegalStateException("Eventbus not initialized.");
        }
        com.sendToStream(false, adr, obj, reply);
    }
    public void publish(String adr, Json obj){
        if (com == null) {
            throw new IllegalStateException("Eventbus not initialized.");
        }
        com.sendToStream(true, adr, obj, null);
    }
    public void publish(String adr, Json obj, String reply){
        if (com == null) {
            throw new IllegalStateException("Eventbus not initialized.");
        }
        com.sendToStream(true, adr, obj, reply);
    }

    private void init(String hostname, int port) {

        com = new EventBusCom();
        com.init(hostname, port);

        communicatorThread = new Thread(com);
        communicatorThread.setName("VertX EventBus Recv.");
        communicatorThread.setDaemon(true);
        communicatorThread.start();

    }


    static EventBus create (String hostname, int port) {
        EventBus bus = new EventBus();
        bus.init(hostname, port);
        return bus;
    }
}




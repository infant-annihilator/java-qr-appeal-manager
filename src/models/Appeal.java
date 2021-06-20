package models;

import java.io.Serializable;

/**
 * Domain model
 *
 * @author Julian Jupiter
 *
 */
public class Appeal implements Serializable {

    private static final long serialVersionUID = 3789909326487155148L;
    private int id;
    private String fioDeclarant;
    private String fioDirector;
    private String address;
    private String topic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFioDeclarant() {
        return fioDeclarant;
    }

    public void setFioDeclarant(String fioDeclarant) {
        this.fioDeclarant = fioDeclarant;
    }

    public String getFioDirector() {
        return fioDirector;
    }

    public void setFioDirector(String fioDirector) {
        this.fioDirector = fioDirector;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

}
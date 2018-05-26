package pl.iwa.mstokfisz.model.request;

public class AddMarkRequest {
    private String subject;
    private double value;
    private int weight;
//    private boolean isBeingEdited;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

//    public boolean isBeingEdited() {
//        return isBeingEdited;
//    }
//
//    public void setBeingEdited(boolean beingEdited) {
//        isBeingEdited = beingEdited;
//    }
}

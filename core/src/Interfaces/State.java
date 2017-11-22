package Interfaces;

public interface State {
    /*
    define state, can be used for aiPlayer as well
     */
    public void enter();
    public void execute();
    public void exit();
}

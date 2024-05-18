package main;

public class NodoTarea {
    private Tarea value;
    private NodoTarea left;
    private NodoTarea right;

    public NodoTarea(Tarea value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public Tarea getValue() {
        return value;
    }

    public void setValue(Tarea value) {
        this.value = value;
    }

    public NodoTarea getLeft() {
        return left;
    }

    public void setLeft(NodoTarea left) {
        this.left = left;
    }

    public NodoTarea getRight() {
        return right;
    }

    public void setRight(NodoTarea right) {
        this.right = right;
    }
}

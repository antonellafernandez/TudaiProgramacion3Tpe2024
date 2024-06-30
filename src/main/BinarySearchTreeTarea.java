package main;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTreeTarea {
    private NodoTarea root;

    public BinarySearchTreeTarea() {
        this.root = null;
    }

    public Tarea getRoot() {
        if (this.root == null) {
            return null;
        }

        return this.root.getValue();
    }

    // En el peor caso, la complejidad es O(h), donde h es la altura del árbol.
    public boolean hasElem(Tarea value) {
        return hasElem(this.root, value);
    }

    private boolean hasElem(NodoTarea node, Tarea value) {
        if (node == null) {
            return false;
        }

        if (node.getValue().equals(value)) {
            return true;
        }

        if (value.compareTo(node.getValue()) < 0) {
            return hasElem(node.getLeft(), value);
        } else {
            return hasElem(node.getRight(), value);
        }
    }

    // Complejidad O(1), simplemente verifica si la raíz es nula.
    public boolean isEmpty() {
        return this.root == null;
    }

    // En el peor caso, la complejidad es O(n).
    public void insert(Tarea value) {
        this.root = insert(this.root, value);
    }

    private NodoTarea insert(NodoTarea node, Tarea value) {
        if (node == null) {
            return new NodoTarea(value);
        }

        if (value.compareTo(node.getValue()) < 0) {
            node.setLeft(insert(node.getLeft(), value));
        } else if (value.compareTo(node.getValue()) > 0) {
            node.setRight(insert(node.getRight(), value));
        }

        return node;
    }

    public boolean delete(Tarea value) {
        if (value != null) {
            this.root = delete(this.root, value);
            return true;
        }

        return false;
    }

    // En el peor caso, la complejidad es O(n).
    private NodoTarea delete(NodoTarea node, Tarea value) {
        if (node == null) {
            return null;
        }

        if (value.compareTo(node.getValue()) < 0) {
            node.setLeft(delete(node.getLeft(), value));
        } else if (value.compareTo(node.getValue()) > 0) {
            node.setRight(delete(node.getRight(), value));
        } else {
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }

            NodoTarea minValueNode = findMinValueNode(node.getRight());
            node.setValue(minValueNode.getValue());
            node.setRight(delete(node.getRight(), minValueNode.getValue()));
        }

        return node;
    }

    private NodoTarea findMinValueNode(NodoTarea node) {
        NodoTarea current = node;

        while (current.getLeft() != null) {
            current = current.getLeft();
        }

        return current;
    }

    // La complejidad es O(n), ya que recorre todos los nodos del árbol.
    public int getHeight() {
        return getHeight(this.root);
    }

    private int getHeight(NodoTarea node) {
        if (node == null)
            return 0;
        else {
            int leftHeight = getHeight(node.getLeft());
            int rightHeight = getHeight(node.getRight());

            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    // La complejidad es O(n), ya que recorre todos los nodos del árbol.
    public void printPosOrder() {
        printPosOrder(this.root);
        System.out.println();
    }

    private void printPosOrder(NodoTarea node) {
        if (node != null) {
            printPosOrder(node.getLeft());
            printPosOrder(node.getRight());
            System.out.print(node.getValue() + " ");
        }
    }

    // La complejidad es O(n), ya que recorre todos los nodos del árbol.
    public void printPreOrder() {
        printPreOrder(this.root);
        System.out.println();
    }

    private void printPreOrder(NodoTarea node) {
        if (node != null) {
            System.out.print(node.getValue() + " ");
            printPreOrder(node.getLeft());
            printPreOrder(node.getRight());
        }
    }

    // La complejidad es O(n), ya que recorre todos los nodos del árbol.
    public void printInOrder() {
        printInOrder(this.root);
        System.out.println();
    }

    private void printInOrder(NodoTarea node) {
        if (node != null) {
            printInOrder(node.getLeft());
            System.out.print(node.getValue() + " ");
            printInOrder(node.getRight());
        }
    }

    // La complejidad es O(n), ya que recorre todos los nodos del árbol.
    public List<Tarea> getLongestBranch() {
        List<Tarea> longestBranch = new ArrayList<>();
        List<Tarea> currentBranch = new ArrayList<>();
        getLongestBranch(root, longestBranch, currentBranch, 0);
        return longestBranch;
    }

    private void getLongestBranch(NodoTarea node, List<Tarea> longestBranch, List<Tarea> currentBranch, int level) {
        if (node != null) {
            currentBranch.add(node.getValue());

            if (node.getLeft() == null && node.getRight() == null) {
                if (currentBranch.size() > longestBranch.size()) {
                    longestBranch.clear();
                    longestBranch.addAll(currentBranch);
                }
            } else {
                getLongestBranch(node.getLeft(), longestBranch, currentBranch, level + 1);
                getLongestBranch(node.getRight(), longestBranch, currentBranch, level + 1);
            }

            currentBranch.remove(currentBranch.size() - 1);
        }
    }

    // La complejidad es O(n), ya que recorre todos los nodos del árbol.
    public List<Tarea> getFrontera() {
        List<Tarea> frontera = new ArrayList<>();
        getFrontera(this.root, frontera);
        return frontera;
    }

    private void getFrontera(NodoTarea node, List<Tarea> frontera) {
        if (node != null) {
            if (node.getLeft() == null && node.getRight() == null) {
                frontera.add(node.getValue());
            }

            getFrontera(node.getLeft(), frontera);
            getFrontera(node.getRight(), frontera);
        }
    }

    // La complejidad es O(h) en el peor caso, ya que desciende por la rama derecha del árbol hasta
    // llegar al nodo más a la derecha.
    public Tarea getMaxElem() {
        if (this.root == null) {
            return null;
        }

        NodoTarea current = this.root;

        while (current.getRight() != null) {
            current = current.getRight();
        }

        return current.getValue();
    }

    // En el peor de los casos, la complejidad es O(n), ya que recorre todos los nodos del árbol
    // hasta encontrar el nivel dado.
    public List<Tarea> getElemAtLevel(int level) {
        List<Tarea> elements = new ArrayList<>();
        getElemAtLevel(this.root, level, elements, 1);
        return elements;
    }

    private void getElemAtLevel(NodoTarea node, int level, List<Tarea> elements, int currentLevel) {
        if (node != null) {
            if (currentLevel == level) {
                elements.add(node.getValue());
            } else {
                getElemAtLevel(node.getLeft(), level, elements, currentLevel + 1);
                getElemAtLevel(node.getRight(), level, elements, currentLevel + 1);
            }
        }
    }

    // En el peor de los casos, la complejidad es O(n)
    public List<Tarea> getTareasEnRango(int prioridadInferior, int prioridadSuperior) {
        List<Tarea> tareasEnRango = new ArrayList<>();
        getTareasEnRango(root, prioridadInferior, prioridadSuperior, tareasEnRango);
        return tareasEnRango;
    }

    private void getTareasEnRango(NodoTarea node, int prioridadInferior, int prioridadSuperior, List<Tarea> result) {
        if (node != null) {
            int prioridad = node.getValue().getPrioridad();

            // Añadir el nodo actual al resultado, si su prioridad está dentro del rango especificado
            if (prioridad >= prioridadInferior && prioridad <= prioridadSuperior) {
                result.add(node.getValue());
            }

            // Continuar buscando en el subárbol izquierdo, si la prioridad del nodo actual
            // es menor que la prioridad inferior del rango
            if (prioridad > prioridadInferior) {
                getTareasEnRango(node.getLeft(), prioridadInferior, prioridadSuperior, result);
            }

            // Continuar buscando en el subárbol derecho, si la prioridad del nodo actual
            // es mayor que la prioridad superior del rango
            if (prioridad < prioridadSuperior) {
                getTareasEnRango(node.getRight(), prioridadInferior, prioridadSuperior, result);
            }
        }
    }
}

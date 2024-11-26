/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kthsmallest;
import java.util.Scanner;

public class KthSmallest {
    public static void main(String[] args) {

        AVLTree<Double> tree = new AVLTree<>();
        Scanner input = new Scanner(System.in);


        System.out.print("Enter 15 numbers: ");
        for (int i = 0; i < 15; i++) {
            double number = input.nextDouble();
            tree.insert(number);
        }


        System.out.print("Enter k: ");
        int k = input.nextInt();


        Double kthSmallest = tree.find(k);
        if (kthSmallest != null) {
            System.out.println("The " + k + "th smallest number is " + kthSmallest);
        } else {
            System.out.println("Invalid value of k.");
        }

        input.close();
    }
}

class AVLTree<E extends Comparable<E>> {
    private AVLTreeNode<E> root;
    private int size = 0;


    private static class AVLTreeNode<E> {
        E element;
        AVLTreeNode<E> left;
        AVLTreeNode<E> right;
        int height;
        int size;

        AVLTreeNode(E element) {
            this.element = element;
            height = 0;
            size = 1;
        }
    }


    public void insert(E element) {
        root = insert(element, root);
    }


    private AVLTreeNode<E> insert(E element, AVLTreeNode<E> node) {
        if (node == null) {
            size++;
            return new AVLTreeNode<>(element);
        }

        int compareResult = element.compareTo(node.element);

        if (compareResult < 0) {
            node.left = insert(element, node.left);
        } else if (compareResult > 0) {
            node.right = insert(element, node.right);
        } else {

            return node;
        }


        updateHeight(node);
        updateSize(node);


        return balance(node);
    }


    public E find(int k) {
        if (k < 1 || k > size) {
            return null;
        }
        return find(k, root);
    }


    private E find(int k, AVLTreeNode<E> node) {
        if (node == null) {
            return null;
        }

        int leftSize = size(node.left);

        if (k <= leftSize) {
            return find(k, node.left);
        } else if (k == leftSize + 1) {
            return node.element;
        } else {
            return find(k - leftSize - 1, node.right);
        }
    }


    private void updateHeight(AVLTreeNode<E> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }


    private void updateSize(AVLTreeNode<E> node) {
        node.size = 1 + size(node.left) + size(node.right);
    }


    private int height(AVLTreeNode<E> node) {
        return node == null ? -1 : node.height;
    }


    private int size(AVLTreeNode<E> node) {
        return node == null ? 0 : node.size;
    }


    private AVLTreeNode<E> balance(AVLTreeNode<E> node) {
        int balanceFactor = height(node.left) - height(node.right);


        if (balanceFactor > 1) {
            if (height(node.left.left) >= height(node.left.right)) {
                node = rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        }

        else if (balanceFactor < -1) {
            if (height(node.right.right) >= height(node.right.left)) {
                node = rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        }

        return node;
    }


    private AVLTreeNode<E> rotateRight(AVLTreeNode<E> y) {
        AVLTreeNode<E> x = y.left;
        AVLTreeNode<E> T2 = x.right;


        x.right = y;
        y.left = T2;


        updateHeight(y);
        updateSize(y);
        updateHeight(x);
        updateSize(x);

        return x;
    }


    private AVLTreeNode<E> rotateLeft(AVLTreeNode<E> x) {
        AVLTreeNode<E> y = x.right;
        AVLTreeNode<E> T2 = y.left;


        y.left = x;
        x.right = T2;


        updateHeight(x);
        updateSize(x);
        updateHeight(y);
        updateSize(y);

        return y;
    }
}

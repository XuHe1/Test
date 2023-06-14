package top.lovelily.algrithm.redblack;


class Node {
    int key;
    boolean isRed;
    Node left, right;
    // constructor, getters and setters
    Node(int key, boolean isRed) {
        this.key = key;
        this.isRed = isRed;
    }
}


public class RedBlackTree {
    Node root;
    // constructor, other methods
    public void insert(int key) {
        Node newNode = new Node(key, true);
        root = insert(root, newNode);
        root.isRed = false;
    }
    private Node insert(Node node, Node newNode) {
        if (node == null) return newNode;
        if (newNode.key < node.key) {
            node.left = insert(node.left, newNode);
        } else if (newNode.key > node.key) {
            node.right = insert(node.right, newNode);
        }
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }
    public void delete(int key) {
        root = delete(root, key);
        if (root != null) root.isRed = false;
    }
    private Node delete(Node node, int key) {
        if (node == null) return null;
        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node temp = min(node.right);
            node.key = temp.key;
            node.right = delete(node.right, temp.key);
        }
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }
    // helper methods for finding minimum node and rotations
    boolean isRed(Node node) {
        if (node != null) {
            return node.isRed;
        }
        return  false;
    }

    private Node rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;
        leftChild.right = node;
        leftChild.isRed = node.isRed;
        node.isRed = true;
        return leftChild;
    }

    private Node rotateLeft(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;
        rightChild.left = node;
        rightChild.isRed = node.isRed;
        node.isRed = true;
        return rightChild;
    }

    private void flipColors(Node node) {
        node.isRed = !node.isRed;
        node.left.isRed = !node.left.isRed;
        node.right.isRed = !node.right.isRed;
    }

    public Node min(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public Node search(Node node, int key) {
        if (node == null || node.key == key) {
            return node;
        }
        if (key < node.key) {
            return search(node.left, key);
        } else {
            return search(node.right, key);
        }
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(15);
        tree.insert(25);
        tree.insert(5);
        tree.insert(35);
        tree.delete(20);
        Node node = tree.search(tree.root, 15);
        System.out.println(node.key);
    }
}
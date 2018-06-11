package com.company;

public class BinaryNode implements Comparable<BinaryNode> {
    private final int weight;
    private byte character;
    private BinaryNode left;
    private BinaryNode right;

    public BinaryNode(int frequency, byte character) {
        this.weight = frequency;
        this.character = character;
    }

    public BinaryNode(BinaryNode left, BinaryNode right) {
        weight = left.weight + right.weight;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return (left == null) && (right == null);
    }

    public int getWeight() {
        return weight;
    }

    public byte getCharacter() {
        return character;
    }

    public BinaryNode getLeft() {
        return left;
    }

    public BinaryNode getRight() {
        return right;
    }

    @Override
    public int compareTo(BinaryNode o) {
        return weight - o.getWeight();
    }
}

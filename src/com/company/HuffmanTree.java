package com.company;

import java.util.*;

public class HuffmanTree implements Comparable<HuffmanTree> {

    public static final int SIZE_ARRAY_FREQUENCY = 255;

    private Node root;
    private int[] frequency = new int[SIZE_ARRAY_FREQUENCY];
    private int sizeContent;

    private HuffmanTree() {
    }

    private HuffmanTree(Node root) {
        this.root = root;
    }

    public static HuffmanTree buildTree(byte[] content) {
        HuffmanTree huffmanTree = buildTree(calculateFrequency(content));
        huffmanTree.sizeContent = content.length;
        return huffmanTree;
    }

    public static HuffmanTree buildTree(int[] frequency) {
        Queue<HuffmanTree> huffmanQueue = new PriorityQueue<HuffmanTree>();
        for (int i = 0; i < frequency.length; i++) {
            if (frequency[i] != 0) {
                huffmanQueue.offer(new HuffmanTree(new Node(frequency[i], (byte) i)));
            }
        }
        while (huffmanQueue.size() != 1) {
            HuffmanTree childLeft = huffmanQueue.poll();
            HuffmanTree childRight = huffmanQueue.poll();
            huffmanQueue.offer(new HuffmanTree(new Node(childLeft, childRight)));
        }
        HuffmanTree huffmanTree = huffmanQueue.poll();
        huffmanTree.setFrequency(frequency);
        return huffmanTree;
    }

    private static int[] calculateFrequency(byte[] content) {
        int[] frequency = new int[SIZE_ARRAY_FREQUENCY];
        for (int i = 0; i < content.length; i++) {
            byte letter = content[i];
            frequency[letter]++;
        }
        return frequency;
    }

    @Override
    public int compareTo(HuffmanTree tree) {
        return root.frequency - tree.root.frequency;
    }

    public void setFrequency(int[] frequency) {
        this.frequency = frequency;
    }

    public int[] getFrequency() {
        return frequency;
    }

    public int getSizeContent() {
        return sizeContent;
    }

    public Node getRoot() {
        return root;
    }

    public static class Node {
        private int frequency;
        private byte character;
        private Node left;
        private Node right;

        public Node(int frequency, byte character) {
            this.frequency = frequency;
            this.character = character;
        }

        public Node(HuffmanTree left, HuffmanTree right) {
            frequency = left.root.frequency + right.root.frequency;
            this.left = left.root;
            this.right = right.root;
        }

        public boolean isLeaf() {
            return (left == null) && (right == null);
        }

        public int getFrequency() {
            return frequency;
        }

        public byte getCharacter() {
            return character;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }
    }
}

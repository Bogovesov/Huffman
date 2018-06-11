package com.company;

import java.util.*;

public class HuffmanTree {

    private HuffmanTree() {
    }

    public static BinaryNode buildTree(int[] frequency) {
        Queue<BinaryNode> nodes = new PriorityQueue<>();
        for (int i = 0; i < frequency.length; i++) {
            if (frequency[i] != 0) {
                nodes.offer(new BinaryNode(frequency[i], (byte) i));
            }
        }
        while (nodes.size() != 1) {
            BinaryNode leftChild = nodes.poll();
            BinaryNode rightChild = nodes.poll();
            nodes.offer(new BinaryNode(leftChild, rightChild));
        }
        return nodes.poll();
    }
}

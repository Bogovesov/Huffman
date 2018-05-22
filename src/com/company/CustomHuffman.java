package com.company;

import java.util.*;

public class CustomHuffman implements Comparable<CustomHuffman> {
    private Node root;

    public CustomHuffman(Node root) {
        this.root = root;
    }

    public static void buildTree(List<String> strings) {
        Map<Character, Integer> map = new HashMap();
        for (String string : strings) {
            for (int i = 0; i < string.length(); i++) {
                Character key = string.charAt(i);
                if (map.containsKey(key)) {
                    Integer pivot = map.get(key);
                    pivot++;
                    map.put(key, pivot);
                } else {
                    map.put(key, 1);
                }
            }
        }

        Queue<CustomHuffman> customHuffmanQueue = new PriorityQueue<CustomHuffman>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            customHuffmanQueue.offer(new CustomHuffman(new Node(entry.getValue(), entry.getKey())));
        }

        while (customHuffmanQueue.size() != 1) {
            CustomHuffman chilLeft = customHuffmanQueue.poll();
            CustomHuffman childRight = customHuffmanQueue.poll();
            customHuffmanQueue.offer(new CustomHuffman(new Node(chilLeft, childRight)));
        }
    }

    @Override
    public int compareTo(CustomHuffman tree) {
        return root.frequency - tree.root.frequency;
    }

    private static class Node {
        private Integer frequency;
        private Character character;
        private Node left;
        private Node right;

        public Node(Integer frequency, Character character) {
            this.frequency = frequency;
            this.character = character;
        }

        public Node(CustomHuffman left, CustomHuffman right) {
            frequency = left.root.frequency + right.root.frequency;
            this.left = left.root;
            this.right = right.root;
        }
    }
}

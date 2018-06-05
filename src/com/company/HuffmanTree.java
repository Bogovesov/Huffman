package com.company;

import java.util.*;

public class HuffmanTree implements Comparable<HuffmanTree> {

    public static final int SIZE_ARRAY_FREQUENCY = 255;

    /**
     * Корень дерева
     */
    private Node root;
    private int[] frequency = new int[SIZE_ARRAY_FREQUENCY];

    private HuffmanTree() {
    }

    private HuffmanTree(Node root) {
        this.root = root;
    }

    /**
     * Построение дерева Хаффмана
     *
     * @return дерево Хаффмана
     */
    public static HuffmanTree buildTree(byte[] content) {
        return buildTree(calculateFrequency(content));
    }

    /**
     * Формирование дерева
     * В последнем элементе будет корень дерева, который содержит ссылки на своих потомков
     */
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

    /**
     * Формирования таблицы повторяемости символов
     */
    public static int[] calculateFrequency(byte[] content) {
        int[] frequency = new int[SIZE_ARRAY_FREQUENCY];
        for (int i = 0; i < content.length; i++) {
            byte letter = content[i];
            frequency[letter]++;
        }
        return frequency;
    }

    public byte[] decode(byte[] content) {
        List<Byte> byteList = new ArrayList<>();
        Node node = root;
        char buf;

        for (int i = 0; i < content.length; i++) {
            buf = (char) content[i];
            for (int j = 0; j < 8; j++) {
                if ((buf & (1 << (7 - j))) != 0) {
                    node = node.right;
                } else {
                    node = node.left;
                }
                if (node.isLeaf()) {
                    byteList.add(node.character);
                    node = root;
                }
            }
        }
        return Bytes.valueOf(byteList);
    }

    /**
     * Формирование закодированной строки
     *
     * @return Закодированнная строка
     */
    public String code(byte[] content) {
        Map<Byte, String> codeTable = codeTable();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < content.length; i++) {
            if (codeTable.containsKey(content[i])) {
                result.append(codeTable.get(content[i]));
            }
        }
        return result.toString();
    }

    /**
     * Формирование кодовой таблицы
     */
    private Map<Byte, String> codeTable() {
        Map<Byte, String> codeTable = new HashMap();
        codeTable(root, new StringBuilder(), codeTable);
        return codeTable;
    }

    private void codeTable(Node node, StringBuilder code, Map<Byte, String> codeTable) {
        if (node.isLeaf()) {
            codeTable.put(node.character, code.toString());
            return;
        }
        codeTable(node.left, code.append('0'), codeTable);
        code.deleteCharAt(code.length() - 1);
        codeTable(node.right, code.append('1'), codeTable);
        code.deleteCharAt(code.length() - 1);
    }

    /**
     * Для сортировки очереди по необходимому признаку
     */
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

    private static class Node {
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
    }
}

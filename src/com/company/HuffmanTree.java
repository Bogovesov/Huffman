package com.company;

import java.io.*;
import java.util.*;

public class HuffmanTree implements Comparable<HuffmanTree> {
    /**
     * Корень дерева
     */
    private Node root;

    private HuffmanTree() {
    }

    private HuffmanTree(Node root) {
        this.root = root;
    }

    /**
     * Построение дерева Хаффмана
     *
     * @param
     * @return дерево Хаффмана
     */
    public static HuffmanTree buildTree(byte[] content) {
        return buildTree(calculateFrequency(content));
    }

    /**
     * Создание дерева из файла
     *
     * @param fileNode файл в который был сохранен обьект
     * @return дерево Хаффмана
     */
    public static HuffmanTree buildTree(String fileNode) {
        Node root = null;
        try (FileInputStream fileInputStream = new FileInputStream(fileNode);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            root = (Node) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new HuffmanTree(root);
    }

    /**
     * Формирование дерева
     * В последнем элементе будет корень дерева, который содержит ссылки на своих потомков
     *
     * @param
     * @return
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
        return huffmanQueue.poll();
    }

    /**
     * Формирования таблицы повторяемости символов
     *
     * @param
     * @return
     */
    public static int[] calculateFrequency(byte[] content) {
        int[] frequency = new int[255];
        for (int i = 0; i < content.length; i++) {
            byte letter = content[i];
            frequency[letter]++;
        }
        return frequency;
    }

    /**
     * Декодирование строки
     *
     * @param
     * @return
     */
    public String decode(byte[] content) {
        String result = "";
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
                    result += (char) node.character;
//                    if (result.length() == sizeSourceString) {
//                        break;
//                    }
                    node = root;
                }
            }
        }
        return result;
    }

    /**
     * Формирование закодированной строки
     *
     * @param
     * @return Закодированнная строка
     */
    public String code(byte[] content) {
        Map<Byte, String> codes = codeTable();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < content.length; i++) {
            if (codes.containsKey(content[i])) {
                result.append(codes.get(content[i]));
            }
        }
        return result.toString();
    }

    /**
     * Формирование кодовой таблицы
     *
     * @return
     */
    private Map<Byte, String> codeTable() {
        Map<Byte, String> codeTable = new HashMap();
        codeTable(root, new StringBuilder(), codeTable);
        return codeTable;
    }

    private void codeTable(Node node, StringBuilder code, Map<Byte, String> codeTable) {
        if (node.isLeaf()) {
            codeTable.put(node.character, code.toString());
            System.out.println("char[" + node.character + "] ----> code[" + code.toString() + "]");
            return;
        }
        codeTable(node.left, code.append('0'), codeTable);
        code.deleteCharAt(code.length() - 1);
        codeTable(node.right, code.append('1'), codeTable);
        code.deleteCharAt(code.length() - 1);
    }

    /**
     * Для сортировки очереди по необходимому признаку
     *
     * @param tree
     * @return
     */
    @Override
    public int compareTo(HuffmanTree tree) {
        return root.frequency - tree.root.frequency;
    }

    /**
     * Обьект узлов
     */
    private static class Node implements Serializable {
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

    public Node getRoot() {
        return root;
    }
}

package com.company;

import java.util.*;

public class CustomHuffman implements Comparable<CustomHuffman> {
    /**
     * Корень дерева
     */
    private Node root;

    private CustomHuffman(Node root) {
        this.root = root;
    }

    /**
     * Построение дерева Хаффмана
     *
     * @param strings
     * @return дерево Хаффмана
     */
    public static CustomHuffman buildTree(List<String> strings) {
        /**
         * Формирования таблицы повторяемости символов
         */
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

        /**
         * Заполнение очереди символов и их количество повторяимости
         */
        Queue<CustomHuffman> huffmanQueue = new PriorityQueue<CustomHuffman>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            huffmanQueue.offer(new CustomHuffman(new Node(entry.getValue(), entry.getKey())));
        }

        /**
         * Формирование дерева
         * В последнем элементе будет корень дерева, который содержит ссылки на своих потомков
         */
        while (huffmanQueue.size() != 1) {
            CustomHuffman childLeft = huffmanQueue.poll();
            CustomHuffman childRight = huffmanQueue.poll();
            huffmanQueue.offer(new CustomHuffman(new Node(childLeft, childRight)));
        }
        return huffmanQueue.poll();
    }

    /**
     * Декодирование строки
     *
     * @param text
     * @return
     */
    public String decode(String text, int sizeSourceString) {
        String result = "";
        Node node = root;
        char buf = '\u0000';

        for (int i = 0; i < text.length(); i++) {
            buf = text.charAt(i);
            for (int j = 0; j < 8; j++) {
                if ((buf & (1 << (7 - j))) != 0) {
                    node = node.right;
                } else {
                    node = node.left;
                }
                if ((node.left == null) && (node.right == null)) {
                    result += node.character;
                    if (result.length() == sizeSourceString) {
                        break;
                    }
                    node = root;
                }
            }
        }
        return result;
    }

    /**
     * Формирование закодированной строки
     *
     * @param text Входная строка
     * @return Закодированнная строка
     */
    public String code(String text) {
        Map<Character, String> codes = codeTable();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            Character key = text.charAt(i);
            if (codes.containsKey(key)) {
                result.append(codes.get(key));
            }
        }
        return result.toString();
    }

    /**
     * Формирование кодовой таблицы
     *
     * @return
     */
    private Map<Character, String> codeTable() {
        Map<Character, String> codeTable = new HashMap();
        codeTable(root, new StringBuilder(), codeTable);
        return codeTable;
    }

    private void codeTable(Node node, StringBuilder code, Map<Character, String> codeTable) {
        if (node.character != null) {
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
    public int compareTo(CustomHuffman tree) {
        return root.frequency - tree.root.frequency;
    }

    /**
     * Обьекты узлов
     */
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

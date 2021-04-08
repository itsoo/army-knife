package com.cupshe.ak.text;

import java.util.*;

/**
 * TrieFilter
 * <p>Trie-tree data structure, used to capture keywords.
 *
 * @author zxy
 */
public class TrieFilter {

    private final TrieNode root;

    private TrieFilter(Collection<String> dict) {
        this.root = Tks.createEmptyNode();
        this.init(Tks.getOrDefault(dict));
    }

    public static TrieFilter create(Collection<String> dict) {
        return new TrieFilter(dict);
    }

    public boolean mightContain(String content) {
        for (int i = 0, len = content.length(); i < len; i++) {
            for (TrieNode node : root.children) {
                if (contains(content, i, node)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean contains(String str, int i, TrieNode node) {
        if (i < str.length() && node.equals(str.charAt(i))) {
            if (node.isEnd) {
                return true;
            }

            for (TrieNode childNode : node.children) {
                if (contains(str, ++i, childNode)) {
                    return true;
                }

                --i;
            }
        }

        return false;
    }

    private void init(Collection<String> src) {
        for (String s : src) {
            TrieNode curr = root;
            for (int i = 0, len = s.length(); i < len; i++) {
                TrieNode node = new TrieNode(s.charAt(i));
                if (curr.children.add(node)) {
                    node.isEnd = (i == s.length() - 1);
                    curr = node;
                } else {
                    for (TrieNode childNode : curr.children) {
                        if (childNode.equals(node)) {
                            childNode.isEnd = !childNode.isEnd && (i == s.length() - 1);
                            curr = childNode;
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * TrieNode
     */
    private static class TrieNode {

        final char literal;

        final Set<TrieNode> children;

        boolean isEnd;

        TrieNode(char literal) {
            this.literal = Character.toLowerCase(literal);
            this.children = new LinkedHashSet<>();
            this.isEnd = false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(literal);
        }

        @Override
        public boolean equals(Object tar) {
            return (tar instanceof Character)
                    ? equals((char) tar)
                    : (tar instanceof TrieNode && equals((TrieNode) tar));
        }

        boolean equals(char tar) {
            return literal == Character.toLowerCase(tar);
        }

        boolean equals(TrieNode tar) {
            return (this == tar) || (Objects.nonNull(tar) && equals(tar.literal));
        }
    }

    /**
     * The private constants and optionals
     */
    private static class Tks {

        private static final char EMPTY_CHAR = '\0';

        static TrieNode createEmptyNode() {
            return new TrieNode(EMPTY_CHAR);
        }

        static Collection<String> getOrDefault(Collection<String> set) {
            return Objects.isNull(set) ? Collections.emptySet() : set;
        }
    }
}

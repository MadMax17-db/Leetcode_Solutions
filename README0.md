# 101. Symmetric Tree

## Problem Statement

Given the `root` of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).

Return `true` if the tree is symmetric; otherwise, return `false`.

## Example 1

**Input:**

root = [1,2,2,3,4,4,3]

**Output:**

true

## Example 2

**Input:**

root = [1,2,2,null,3,null,3]

**Output:**

false

## Constraints

- The number of nodes in the tree is in the range `[1, 1000]`.
- `-100 <= Node.val <= 100`

## Approach

- Compare the left and right subtrees recursively.
- If both nodes are `null`, they are symmetric.
- If only one node is `null` or their values differ, the tree is not symmetric.
- Recursively compare:
  - Left child of the left subtree with the right child of the right subtree.
  - Right child of the left subtree with the left child of the right subtree.
- The tree is symmetric only if all corresponding pairs match.

## Complexity Analysis

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

Where:
- **n** = Number of nodes in the tree
- **h** = Height of the tree

## Key Concepts

- Binary Tree
- Recursion
- Depth-First Search (DFS)
- Tree Traversal

## Learning Outcome

This problem demonstrates how recursion can be used to compare two subtrees as mirror images, reinforcing concepts of tree traversal and recursive problem solving.

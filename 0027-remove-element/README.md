# LeetCode 27 - Remove Element

## Problem Statement

Given an integer array `nums` and an integer `val`, remove all occurrences of `val` in-place. The order of the elements may be changed. Return the number of elements in `nums` which are not equal to `val`.

The first `k` elements of `nums` should contain the elements that are not equal to `val`.

## Example 1

**Input:**

* nums = [3,2,2,3]
* val = 3

**Output:**

* 2

**Explanation:**
The first 2 elements of the array become `[2,2]`.

## Example 2

**Input:**

* nums = [0,1,2,2,3,0,4,2]
* val = 2

**Output:**

* 5

**Explanation:**
The first 5 elements of the array become `[0,1,3,0,4]`.

## Approach

* Traverse the array from left to right.
* Keep track of the position where the next valid element should be placed.
* Whenever an element is not equal to `val`, place it at the current valid position.
* Increase the valid position counter.
* After processing all elements, the counter represents the number of elements remaining in the array.

## Complexity Analysis

* **Time Complexity:** O(n)
* **Space Complexity:** O(1)

## Key Concepts

* Arrays
* In-Place Modification
* Two Pointers

## Learning Outcome

This problem helps in understanding how to modify an array in-place without using extra memory and introduces the two-pointer technique, which is widely used in array-based problems.

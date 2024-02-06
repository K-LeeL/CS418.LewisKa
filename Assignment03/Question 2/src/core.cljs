; Assignment 03: Getting started with Clojure
; Due: Mon Feb 5, 2024 11:59pm
; Kaylee Lewis
;
; Problem 2:
; The Tree abstract datatype can be recursively defined using lists. 
; We will define a tat as a tree of atoms, where we will denote an empty tree as being the empty 
; list () or as a list (atom leftChild RightChild) where leftChild and rightChild are trees of 
; atoms that are the respective left and right children.

; Assuming that we have the ability to partial order items (i.e., we have the ability to use the >, 
; ’<, and==` operators on atoms), implement this data type in Racket, assuming you include:

;    (insert tat atom): insert an atom into a tree,
;    (delete tat atom): delete the the first occurrence of an atom from a tree,
;    (empty? tat atom): predicate to test to see if a tree of atoms is empty,
;    (member? tat atom): predicate to check to see if an atom is in a tree of atoms
;    (in-order tat exp): traverse a tree of atoms in ‘in-order fashion’ applying an expression to each
;        node as you go and building a list of results.
;    (pre-order tat exp): traverse a tree of atoms in ‘pre-order fashion’ applying an expression to 
;        each node as you go and building a list of results.
;    (post-order tat exp): traverse a tree of atoms in ‘post-order fashion’ applying an

; https://marketplace.visualstudio.com/items?itemName=evzen-wybitul.magic-racket
; https://www.youtube.com/watch?v=cGE6EQRt0ro
; https://www.youtube.com/watch?v=vx6vOXwyaXU
; https://www.youtube.com/watch?v=FSLQu4pKpXs

; used Microsoft copilot to help troubleshoot translation from Racket to Clojure

; Test to see if a tree of atoms is empty
(defn empty? [tat]
  ; If the tree is empty, return true
  (nil? tat))

; Insert an atom into a tree
(defn insert [tat atom]
  (cond 
    ; If the tree is empty, return a tree with the atom as the first atom and two empty subtrees
    (empty? tat) (list atom nil nil)
    ; If the atom is greater than the first atom in the tree, insert the atom into the right subtree
    (< atom (first tat)) (list (first tat) (insert (nth tat 1) atom) (nth tat 2))
    ; If the atom is less than the first atom in the tree, insert the atom into the left subtree
    (> atom (first tat)) (list (first tat) (nth tat 1) (insert (nth tat 2) atom))
    ; If the atom is equal to the first atom in the tree, return the tree
    :else (list (first tat) (insert (nth tat 1) atom) (nth tat 2))))

; Delete the first occurrence of an atom from a tree
(defn delete [tat atom]
  (cond
    ; If the tree is empty, return an empty tree
    (empty? tat) nil
    ; If the atom is greater than the first atom in the tree, delete the atom from the right subtree
    (> atom (first tat)) (list (first tat) (nth tat 1) (delete (nth tat 2) atom))
    ; If the atom is less than the first atom in the tree, delete the atom from the left subtree
    (< atom (first tat)) (list (first tat) (delete (nth tat 1) atom) (nth tat 2))
    ; If the atom is equal to the first atom in the tree, and if the left subtree is empty, return the right subtree
    :else (if (empty? (nth tat 1)) (nth tat 2) (list (first (nth tat 1)) (nth tat 2 1) (nth tat 2 2)))))

; Test to see if an atom is a member of the tree
(defn member? [tat atom]
  (cond
    ; If the tree is empty, return false
    (empty? tat) false
    ; If the atom is greater than the first atom in the tree, check the right subtree
    (> atom (first tat)) (member? (nth tat 2) atom)
    ; If the atom is less than the first atom in the tree, check the left subtree 
    (< atom (first tat)) (member? (nth tat 1) atom)
    ; If the atom is equal to the first atom in the tree, return true
    :else true))

; Traverse a tree of atoms in 'in-order fashion'
(defn in-order [tat exp]
  ; If the tree is empty, return an empty list
  (if (empty? tat) 
    ; If the tree is not empty, return a list of the results of applying the expression to each
    '()
    (concat (in-order (nth tat 1) exp) (list (exp (first tat))) (in-order (nth tat 2) exp))))

; Traverse a tree of atoms in 'pre-order fashion'
(defn pre-order [tat exp]
  ; If the tree is empty, return an empty list
  (if (empty? tat)
    ; If the tree is not empty, return a list of the results of applying the expression to each
    '()
    (concat (list (exp (first tat))) (pre-order (nth tat 1) exp) (pre-order (nth tat 2) exp))))

; Traverse a tree of atoms in 'post-order fashion'
(defn post-order [tat exp]
  ; If the tree is empty, return an empty list
  (if (empty? tat)
    ; If the tree is not empty, return a list of the results of applying the expression to each
    '()
    (concat (post-order (nth tat 1) exp) (post-order (nth tat 2) exp) (list (exp (first tat))))))

; Test the functions
(def my-tree (list 5 (list 3 (list 2 nil nil) (list 4 nil nil)) (list 7 nil nil)))

(println "Original tree: " my-tree) 

(println "Insert 6: " (insert my-tree 6))

(println "Delete 3: " (delete my-tree 3))

(println "Is 4 a member? " (member? my-tree 4))

(println "In-order traversal: " (in-order my-tree identity))

(println "Pre-order traversal: " (pre-order my-tree identity))

(println "Post-order traversal: " (post-order my-tree identity))
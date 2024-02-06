#lang Racket

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

; Insert an atom into a tree
(define (insert tat atom)
    (cond 
        ; If the tree is empty, return a tree with the atom as the first atom and two empty subtrees
        [(empty? tat) (list atom empty empty)] 
        ; If the atom is greater than the first atom in the tree, insert the atom into the right 
        ; subtree
        [(< atom (car tat)) (list (car tat) (insert (cadr tat) atom) (caddr tat))] 
        ; If the atom is less than the first atom in the tree, insert the atom into the left subtree
        [(> atom (car tat)) (list (car tat) (cadr tat) (insert (caddr tat) atom))] 
        ; If the atom is equal to the first atom in the tree, return the tree
        [else (list (car tat) (insert (cadr tat) atom) (caddr tat))])) 

; Delete the first occurrence of an atom from a tree
(define (delete tat atom) 
    (cond
        ; If the tree is empty, return an empty tree
        [(empty? tat) empty]
        ; If the atom is greater than the first atom in the tree, delete the atom from the right 
        ; subtree
        [(> atom (car tat)) (list (car tat) (cadr tat) (delete (caddr tat) atom))]
        ; If the atom is less than the first atom in the tree, delete the atom from the left subtree
        [(< atom (car tat)) (list (car tat) (delete (cadr tat) atom) (caddr tat))]
        ; If the atom is equal to the first atom in the tree, and if the left subtree is empty, 
        ; return the right subtree
        [else (if (empty? (cadr tat)) (caddr tat) (list (car (cadr tat)) (caddr (cadr tat)) 
        (caddr tat)))])) 

; Test to see if a tree of atoms is empty
(define (empty? tat) 
    ; If the tree is empty, return true
    (null? tat))

; Test to see if a tree of atoms is empty
(define (member? tat atom)
    (cond
        ; If the tree is empty, return false
        [(empty? tat) 'false]
        ; If the atom is greater than the first atom in the tree, check the right subtree
        [(> atom (car tat)) (member? (caddr tat) atom)]
        ; If the atom is less than the first atom in the tree, check the left subtree 
        [(< atom (car tat)) (member? (cadr tat) atom)] 
        ; If the atom is equal to the first atom in the tree, return true
        [else 'true])) 

; Traverse a tree of atoms in ‘in-order fashion’ 
(define (in-order tat exp)
    ; If the tree is empty, return an empty list
    (if (empty? tat) 
        ; If the tree is not empty, return a list of the results of applying the expression to each
        '()
        (append (in-order (cadr tat) exp) (list (exp (car tat))) (in-order (caddr tat) exp))))

; traverse a tree of atoms in ‘pre-order fashion’ 
(define (pre-order tat exp)
    ; If the tree is empty, return an empty list
    (if (empty? tat)
        ; If the tree is not empty, return a list of the results of applying the expression to each
        '()
        (cons (exp (car tat)) (append (pre-order (cadr tat) exp) (pre-order (caddr tat) exp)))))

; traverse a tree of atoms in ‘post-order fashion’
(define (post-order tat exp)
    ; If the tree is empty, return an empty list
    (if (empty? tat)
        ; If the tree is not empty, return a list of the results of applying the expression to each
        '()
        (append (post-order (cadr tat) exp) (post-order (caddr tat) exp) (list (exp (car tat))))))        

; Test the functions
(define my-tree (list 5 (list 3 (list 2 empty empty) (list 4 empty empty)) (list 7 empty empty)))

(display "Original tree: ")
(display my-tree) 
(newline) 

(display "Insert 6: ")
(display (insert my-tree 6))
(newline)

(display "Delete 3: ")
(display (delete my-tree 3))
(newline)

(display "Is 4 a member? ")
(display (member? my-tree 4))
(newline)

(display "In-order traversal: ")
(display (in-order my-tree identity))
(newline)

(display "Pre-order traversal: ")
(display (pre-order my-tree identity))
(newline)

(display "Post-order traversal: ")
(display (post-order my-tree identity))
(newline)
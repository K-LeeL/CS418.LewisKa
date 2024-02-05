;; Assignment 03: Getting started with Clojure
;; Due: Mon Feb 5, 2024 11:59pm
;; Kaylee Lewis
;;
;; Problem 1:
;; Many Lisp-variants provide a predicate member? tells whether some atom 
;; appears at least once in a list. Write a predicate member-twice? 
;; That tells whether some atom appears at least twice in a collection. 
;; Look carefully at the Clojure documentation for the every?, not-every? 
;; and some functions in Clojure.

;; https://clojuredocs.org/quickref
;; https://clojuredocs.org/clojure.core/every_q 

;; defn defines a function
;; member-twice? takes a collection and an atom
;; coll is the collection
;; the function returns true if the atom appears at least twice in the collection
(defn member-twice? [coll atom]
  (>= (count (filter #(= % atom) coll)) 2))

;; def is used to define a variable
;; test-coll is a collection of numbers
(def test-coll [1 2 3 4 5 6 7 8 9 2])

;; test with 1
(prn "1:" (member-twice? test-coll 1)) 

;; test with 2
(prn "2:" (member-twice? test-coll 2))

;; test with 3
(prn "3:" (member-twice? test-coll 3))

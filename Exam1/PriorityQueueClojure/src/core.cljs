;; Purpose:
;; The priority queue data type works like a regular queue with two additional invariants:
;; the contents of the queue are stored in some sorted order, and popping an item off the
;; priority queue returns the item with the highest priority rather than the item at the head
;; of the queue.
;; Inserts will place the new item based upon its priority.
;; Define data types in Clojure that implement this form of a priority queue.

;; https://www.programiz.com/dsa/priority-queue
;; https://www.geeksforgeeks.org/priority-queue-in-cpp-stl/

;; function to insert an item into the priority queue
(defn insert [pq item pred]
  (let [insert-position (count (filter #(pred item %) pq))]
    (concat (take insert-position pq) (list item) (drop insert-position pq))))

;; function to remove the item with the highest priority from the priority queue
(defn pop [pq]
  (if (empty? pq)
    (throw (js/Error. "Priority queue is empty"))
    (let [highest-priority-item (first pq)]
      (list highest-priority-item (rest pq)))))

;; function to traverse the priority queue
(defn traverse [pq]
  (doseq [item pq]
    (println item)))

;; main function
(defn main []
  ;; create a priority queue
  (let [priority-queue (atom [])
        priority-fn (atom nil)]

    ;; function to push an item into the priority queue
    (defn push [item]
      (swap! priority-queue #(insert % item @priority-fn)))

    ;; function to set the priority function
    (defn set-priority-fn [fn]
      (reset! priority-fn fn))

    ;; function to test the priority queue
    (defn test-priority-queue []
      ;; set the priority function
      (set-priority-fn >)
      ;; push items into the priority queue
      (push 3)
      (push 1)
      (push 2)
      ;; print the priority queue
      (println "Priority queue:")
      ;; traverse the priority queue
      (traverse @priority-queue)
      ;; print the item with the highest priority
      (println "Popped item with highest priority:")

      ;; pop the item with the highest priority
      (let [[item _] (pop @priority-queue)]
        (println item))))

  ;; test the priority queue
  (test-priority-queue))

;; call the main function
(main)
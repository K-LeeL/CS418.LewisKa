;; Purpose:
;; The priority queue data type works like a regular queue with two additional invariants:
;; the contents of the queue are stored in some sorted order, and popping an item off the
;; priority queue returns the item with the highest priority rather than the item at the head
;; of the queue.
;; Inserts will place the new item based upon its priority.
;; Define data types in Clojure that implement this form of a priority queue.

;; https://www.programiz.com/dsa/priority-queue
;; https://www.geeksforgeeks.org/priority-queue-in-cpp-stl/

;; Function to insert an item into the priority queue
(defn insert [pq item pred]
  (let [insert-position (count (filter #(pred item %) pq))]
    (concat (take insert-position pq) (list item) (drop insert-position pq))))

;; Function to remove the item with the highest priority from the priority queue
(defn pop [pq]
  (if (empty? pq)
    (throw (js/Error. "Priority queue is empty"))
    (let [highest-priority-item (first pq)]
      (list highest-priority-item (rest pq)))))

;; Function to traverse the priority queue
(defn traverse [pq]
  (doseq [item pq]
    (println item)))

;; Main function
(defn main []
  ;; Create a priority queue
  (let [priority-queue (atom [])
        priority-fn (atom nil)]

    ;; Function to push an item into the priority queue
    (defn push [item]
      (swap! priority-queue #(insert % item @priority-fn)))

    ;; Function to set the priority function
    (defn set-priority-fn [fn]
      (reset! priority-fn fn))

    ;; Function to test the priority queue
    (defn test-priority-queue []
      ;; Set the priority function
      (set-priority-fn >)
      ;; Push items into the priority queue
      (push 3)
      (push 1)
      (push 2)
      ;; Print the priority queue
      (println "Priority queue:")
      ;; Traverse the priority queue
      (traverse @priority-queue)
      ;; Print the item with the highest priority
      (println "Popped item with highest priority:")

      ;; Pop the item with the highest priority
      (let [[item _] (pop @priority-queue)]
        (println item))))

  ;; Test the priority queue
  (test-priority-queue))

;; Call the main function
(main)

;; 
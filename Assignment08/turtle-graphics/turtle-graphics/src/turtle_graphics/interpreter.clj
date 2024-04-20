;; Assignment 08: Terry the Turtle
;; Due: Fri Apr 19, 2024 11:59pm
;;
;;Problem 1
;; We discussed Uncle Bob’s construction of a Turtle Graphics package for Clojure built around the Quil graphics package.
;; Using that package as a starting point, write an interpreter for the turtle graphics language:
;;
;; forward <units>
;; turn <angle>
;; pen <upOrDown>
;;
;;where <units> is the number of units to move forward, <angle> is number of degrees to turn, with negative values indicating
;; turn left that many degrees and positive values saying to turn right that many degrees, and <upOrDown> being the either 1 
;; for pen down or 0 for pen up. You may assume all values are of type double.

(ns turtle-graphics.interpreter
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.core.async :as async]
            [turtle-graphics.turtle :as turtle]))

;; Define a channel for communication with the turtle
(def channel (async/chan))

;; Command functions
(defn forward [distance] (async/>!! channel [:forward distance]))
(defn turn [angle] (async/>!! channel [:turn angle]))
(defn pen [upOrDown] (async/>!! channel [:pen upOrDown]))

;; Turtle script interpreter
(defn interpret-command [command]
  (let [[action value] command]
    (case action
      :forward (forward value)
      :turn (turn value)
      :pen (pen value))))

(defn interpret-script [script]
  (doseq [command script]
    (interpret-command command)))

;; Test script
(def test-script [[:forward 50] [:turn 90] [:forward 50] [:pen 0] [:turn -90] [:forward 100]])

(defn setup []
  (q/frame-rate 60)
  (q/color-mode :rgb)
  (let [state {:turtle (turtle/make)
               :channel channel}]
    (async/go
      (interpret-script test-script) ; Pass test-script as an argument
      (prn "Turtle script complete"))
    state))

(defn handle-commands [channel turtle]
  (loop [turtle turtle]
    (let [command (if (= :idle (:state turtle))
                    (async/poll! channel)
                    nil)]
      (if (nil? command)
        turtle
        (recur (turtle/handle-command turtle command))))))


(defn update-state [{:keys [channel] :as state}]
  (let [turtle (:turtle state)
        turtle (turtle/update-turtle turtle)]
    (assoc state :turtle (handle-commands channel turtle))))

(defn draw-state [state]
  (q/background 240)
  (q/with-translation
    [500 500]
    (let [{:keys [turtle]} state]
      (turtle/draw turtle))))

(declare turtle-graphics)

(defn ^:export -main [& args]
  (q/defsketch turtle-graphics
    :title "Turtle Graphics"
    :size [1000 1000]
    :setup setup
    :update update-state
    :draw draw-state
    :features [:keep-on-top]
    :middleware [m/fun-mode])
  args)

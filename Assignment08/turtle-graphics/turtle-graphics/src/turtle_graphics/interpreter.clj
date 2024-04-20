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

 (defn back [distance] (async/>!! channel [:back distance]))
 (defn right [angle] (async/>!! channel [:right angle]))
 (defn left [angle] (async/>!! channel [:left angle]))
 (defn pen-up [] (async/>!! channel [:pen-up]))
 (defn pen-down [] (async/>!! channel [:pen-down]))
 (defn hide [] (async/>!! channel [:hide]))
 (defn show [] (async/>!! channel [:show]))
 (defn weight [weight] (async/>!! channel [:weight weight]))
 (defn speed [speed] (async/>!! channel [:speed speed]))

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

 

;; Initialize Quil sketch
 (defn -main [& args]
   (q/sketch
    :host "turtle-interpreter"
    :size [500 500]
    :setup #(async/go (interpret-script test-script))
    :middleware [m/fun-mode]))


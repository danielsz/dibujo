(ns nieuwe-beelding.abstractions.point
  (:require [nieuwe-beelding.utils.math :as math]))

(defprotocol IPoint
  (add [this p])
  (equal? [this p])
  (to-float [_])
  (to-double [_])
  (round [_])
  (set-color [_ v])
  (get-color [_])
  (to-native [_]))

;; recursive definition, compilation warning is emitted in clojurescript, but code is OK http://dev.clojure.org/jira/browse/CLJS-817
(defrecord Point [x y]
  IPoint
  (add [this p] (->Point (+ x (:x p)) (+ y (:y p))))
  (equal? [this p] (and (= x (:x p)) (= y (:y p))))
  (to-double [_] (->Point (double x) (double y)))
  (to-float [_] (->Point (float x) (float y)))
  (round [_] (->Point (math/round x) (math/round y)))
  (set-color [this v] (assoc this :color v))
  (get-color [this] (:color this))
  (to-native [this] #?(:clj (java.awt.Point. x y)
                       :cljs (clj->js this))))

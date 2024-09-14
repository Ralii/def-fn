(ns def-fn.core
  (:require
   [cljs.analyzer.api :as api]
   [clojure.set :as set]
   [clojure.walk :as walk]))

(defonce ^:private def-fn-forms (atom {}))

(defn- def-fn? [form]
  (if (and (list? form)
           (map? (second form))
           (contains?  @def-fn-forms (keyword (first form))))
    true
    false))

(defn- validate-form-params [form]
  (if (= ((keyword (first form)) @def-fn-forms)
         (->> form
              second
              keys
              set))
    true
    (do
      (prn "====== ==========================TYPE ERROR=====================================")
      (prn "FORM:             " form)
      (prn "PARAMS SHOULD BE: " ((keyword (first form)) @def-fn-forms))
      (prn "PARAMS WERE:      " (second form))
      (prn "META:             " (meta (first form)))
      (prn "=================================TYPE ERROR====================================="))))

(defmacro checker []
  (->> (api/all-ns)
       (mapv #(api/ns-publics %))
       (mapv (fn [item] (walk/prewalk (fn [form] (if (def-fn? form) (do
                                                                      (validate-form-params form)
                                                                      form) form)) item))))
  nil)

(defmacro def-fn [name & forms]
  (let [[docstring? forms] (if (= (first forms) string?)
                             [(first forms) forms]
                             [nil forms])
        defn-args (list 'defn name docstring? forms)
        defn-args-without-nil (remove nil? defn-args)
        destructure-args (set (map keyword (-> forms first first first second)))
        default-args (set (map keyword (->> forms first first second second keys)))
        args-in-defn-and-not-default (set/difference destructure-args default-args)]
    (swap! def-fn-forms
           assoc (keyword name) args-in-defn-and-not-default)
    `(~@defn-args-without-nil)))

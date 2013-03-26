(ns hardhat.core-test
  (:use clojure.test
        hardhat.core))

(deftest a-test
  (testing "Converts a regular string"
    (let [actual (str (html "hello"))]
      (is (= actual "hello"))))

  (testing "Converts a regular vector"
    (let [actual (str (html [:h1 "hello"]))]
      (is (= actual "<h1>hello</h1>"))))

  (testing "Can be nested"
    (let [actual (str (html [:h1 "hello " [:em "world"]]))]
      (is (= actual "<h1>hello <em>world</em></h1>"))))

  (testing "strips HTML by default"
    (let [actual (str (html "<script>hello</script>"))]
      (is (= actual "&lt;script&gt;hello&lt;/script&gt;"))))

  (testing "can nest itself"
    (let [actual (str (html (html [:script "hello"])))]
      (is (= actual "<script>hello</script>"))))

  (testing "can walk really deeply"
    (let [n 100
          actual (str (html (last (take n (iterate #(vector :p %) "bottom")))))
          expected (apply str (last (take n (iterate #(concat "<p>" % "</p>") "bottom"))))]
      (is (= actual expected)))))

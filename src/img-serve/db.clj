(ns img-serve.db)

(defn get-image
  [{:keys [images]} id]
  (let [image-map (filter #(= id (:id %)) images)]
    (:location (first image-map))))

(defn find-images
  [{:keys [images]} tag]

  (map #(let [id (:id %)
              tag (:tag %)]
         {:id id
          :location (str "image/" id)
          :tag tag})
   (filter #(some #{(keyword tag)} (:tag %)) images)))

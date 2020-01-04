(ns hack-news.config)

(def db-spec
  {:dbtype   "postgres"
   :dbname   "postgres"
   :user     "postgres"
   :password "postgres"})

(def swagger-config
  {:ui      "/swagger"
   :spec    "/swagger.json"
   :options {:ui   {:validatorUrl nil}
             :data {:info {:version "1.0.0", :title "Restful CRUD API"}}}})
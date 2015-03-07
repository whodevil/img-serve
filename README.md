# img-serve
This is a simple micro service that provides an api for images.

## Running

```lein run `pwd`/db```

## API
+ ```GET image/:id``` - retrieve a specific image.
+ ```GET tag/:tag-name``` - retrieve a list of images that match the tag.

## Configuration
This app expects as an argument the location of the flat file DB. The database directory is formatted as such:
+ ```<database-dir>/db.edn```
+ ```<database-dir>/images/```
### Example db.edn
```clojure
[{:id "1" 
  :location "images/some-image.jpg"
  :tag [:front :digital-art]}
{:id "2"
  :location "images/some-other-image.jpg"
  :tag [:front :dogs]}]
```

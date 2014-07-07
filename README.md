# lein-sassc

Leiningen plugin to compile SASS/SCSS files with [SassC](https://github.com/sass/sassc).

## Installation

```clj
(defproject example "1.0.0"
  :plugins [[lein-sassc "0.9.0"]])
```

Make sure you have a [sassc](https://github.com/sass/sassc) command.

## Configuration

The configuration for sassc is specified under the `:sassc` sections of your `project.clj`.

```
(defproject example "1.0.0"
  :sassc [{:src          "src/scss/page1.scss" ;; default "src/scss/main.scss"
           :output-to    "dist/page1.css"      ;; default "target/sassc/main.css"
           :style        "compressed"          ;; "nested" or "compressed", default "nested"
           :import-path  "src/scss"}           ;; default "src/scss"
          {:src       "src/scss/page2.scss"
           :output-to "dist/page2.css"}
          {:src       "src/scss/page3.scss"
           :output-to "dist/page3.css"}])
```

The above configuration run the following commands.

```sh
$ sass -o dist/page1.css -t compressed -I src/scss src/scss/page1.scss
$ sass -o dist/page2.css -t nested -I src/scss src/scss/page2.scss
$ sass -o dist/page3.css -t nested -I src/scss src/scss/page3.scss
```

## Usage

Compile your files once:

```sh
$ lein sassc once
```

To delete all the files generated by lein-sassc

```
$ lein sassc clean
```

## Hooks

The following hooks are supported by lein-sassc:

```
$ lein compile
$ lein clean
```

To enable the hooks, add the following lein to your `project.clj` file:

```clj
:hooks [leiningen.sassc]
```

## License

Copyright (C) 2014 kei@apribase.net

Distributed under the Eclipse Public License, the same as Clojure.

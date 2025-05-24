# def-fn

NOTE: OBSOLETE. It relied heavily on shadow-cljs `:root-source-form` information that has been removed from the update. I will leave the code incase someone finds it useful for some other purpose. Check my other library for a bit similar functionality that relies on malli: [defnk!](https://github.com/Ralii/defnk)

Def-fn is a macro that acts like a defn but requires map as its first argument.

``` clojure
(def-fn func [{:keys [first second third] :or {third 3}} ...]
  [first second third])
```

Argument use is checked at compile time instead of runtime. func requires `first` and `second` to be defined at call-site but since `third` is defined as optional it is not required.

``` clojure
(func {:first 1
       :second 2})
```

## Installation

TODO: clojars

## Status
Early stage testing. Code is public but is still under testing by the author and subject to change.

## License

Copyright © 2024 Lari Saukkonen

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

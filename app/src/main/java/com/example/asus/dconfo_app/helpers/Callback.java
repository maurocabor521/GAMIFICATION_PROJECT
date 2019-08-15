package com.example.asus.dconfo_app.helpers;

/**
 * Created by Andrés Cabal on 5/05/2018.
 */

public interface Callback<T> {
    void success(T result);

    void error(Exception error);
}

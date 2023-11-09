package com.web.letscoffee.exception;

public class Over10FavoriteException extends RuntimeException {
        public Over10FavoriteException() {
            super();
        }

        public Over10FavoriteException(String message) {
            super(message);
        }

        public Over10FavoriteException(String message, Throwable cause) {
            super(message, cause);
        }

        public Over10FavoriteException(Throwable cause) {
            super(cause);
        }

}

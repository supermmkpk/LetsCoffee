package com.graduate2.project.exception;

public class Over5FavoriteException extends RuntimeException {
        public Over5FavoriteException() {
            super();
        }

        public Over5FavoriteException(String message) {
            super(message);
        }

        public Over5FavoriteException(String message, Throwable cause) {
            super(message, cause);
        }

        public Over5FavoriteException(Throwable cause) {
            super(cause);
        }

}

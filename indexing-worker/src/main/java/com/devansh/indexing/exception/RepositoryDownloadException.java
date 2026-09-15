/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.devansh.indexing.exception;


public class RepositoryDownloadException extends RuntimeException {

    public RepositoryDownloadException(String message) {
        super(message);
    }

    public RepositoryDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

}

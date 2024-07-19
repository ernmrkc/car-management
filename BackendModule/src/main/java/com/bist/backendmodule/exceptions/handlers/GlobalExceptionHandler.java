package com.bist.backendmodule.exceptions.handlers;

import com.bist.backendmodule.exceptions.*;
import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Global exception handler to manage custom exceptions and provide response.
 * Handles various custom exceptions, logs the error details, and returns a response entity
 * with the error message and HTTP Status code.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles BrandNotValidException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(BrandNotValidException.class)
    public ResponseEntity<SimpleResponse> handleBrandNotValidException(CustomBaseException exception) {
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles BrandAlreadyExistsException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<SimpleResponse> handleBrandAlreadyExistsException(CustomBaseException exception) {
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles BrandNotFoundException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<SimpleResponse> handleBrandNotFoundException(CustomBaseException exception) {
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles FileEmptyException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(FileEmptyException.class)
    public ResponseEntity<SimpleResponse> handleFileEmptyException(CustomBaseException exception) {
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles FileUploadException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<SimpleResponse> handleFileUploadException(CustomBaseException exception) {
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles ImageNotFoundException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<SimpleResponse> handleImageNotFoundException(CustomBaseException exception) {
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles ImageNotValidException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(ImageNotValidException.class)
    public ResponseEntity<SimpleResponse> handleImageNotValidException(CustomBaseException exception) {
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles CarCommandNotValidException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(CarCommandNotValidException.class)
    public ResponseEntity<SimpleResponse> handleCarCreateDTONotValidException(CustomBaseException exception) {
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles CarNotFoundException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<SimpleResponse> handleCarNotFoundException(CustomBaseException exception) {
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles UserCreateDTONotValidException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(UserCreateDTONotValidException.class)
    public ResponseEntity<SimpleResponse> handleUserCreateDTONotValidException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles UserNotValidException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(UserNotValidException.class)
    public ResponseEntity<SimpleResponse> handleUserNotValidException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles UserAlreadyExistsException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<SimpleResponse> handleUserAlreadyExistsException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles UserNotFoundException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<SimpleResponse> handleUserNotFoundException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles RoleNotValidException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(RoleNotValidException.class)
    public ResponseEntity<SimpleResponse> handleRoleNotValidException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles RoleAlreadyExistsException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<SimpleResponse> handleRoleAlreadyExistsException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles RoleNotFoundException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<SimpleResponse> handleRoleNotFoundException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles PermissionNotValidException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(PermissionNotValidException.class)
    public ResponseEntity<SimpleResponse> handlePermissionNotValidException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles PermissionAlreadyExistsException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(PermissionAlreadyExistsException.class)
    public ResponseEntity<SimpleResponse> handlePermissionAlreadyExistsException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles PermissionNotFoundException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(PermissionNotFoundException.class)
    public ResponseEntity<SimpleResponse> handlePermissionNotFoundException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles GroupNotValidException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(GroupNotValidException.class)
    public ResponseEntity<SimpleResponse> handleGroupNotValidException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles GroupAlreadyExistsException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(GroupAlreadyExistsException.class)
    public ResponseEntity<SimpleResponse> handleGroupAlreadyExistsException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles GroupNotFoundException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<SimpleResponse> handleGroupNotFoundException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles AlgorithmNotFoundException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(AlgorithmNotFoundException.class)
    public ResponseEntity<SimpleResponse> handleAlgorithmNotFoundException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles InvalidTokenSignatureException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(InvalidTokenSignatureException.class)
    public ResponseEntity<SimpleResponse> handleInvalidTokenSignatureException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles TokenValidationException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<SimpleResponse> handleTokenValidationException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles InvalidRolesFormatException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(InvalidRolesFormatException.class)
    public ResponseEntity<SimpleResponse> handleInvalidRolesFormatException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles InvalidPermissionsFormatException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(InvalidPermissionsFormatException.class)
    public ResponseEntity<SimpleResponse> handleInvalidPermissionsFormatException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles InvalidGroupsFormatException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(InvalidGroupsFormatException.class)
    public ResponseEntity<SimpleResponse> handleInvalidGroupsFormatException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles LoginRequestNotValidException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(LoginRequestNotValidException.class)
    public ResponseEntity<SimpleResponse> handleLoginRequestNotValidException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }

    /**
     * Handles InvalidCredentialsException and logs the error.
     *
     * @param exception The custom base exception
     * @return The response entity with the error message and status
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<SimpleResponse> handleInvalidCredentialsException(CustomBaseException exception){
        Logger logger = LoggerFactory.getLogger(exception.getClazz());
        logger.error(exception.getSimpleResponse().getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }
}

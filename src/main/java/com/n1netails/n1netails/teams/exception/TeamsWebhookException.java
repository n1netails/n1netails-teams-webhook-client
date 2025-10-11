package com.n1netails.n1netails.teams.exception;

/**
 * Teams Webhook Exception (Base Exception)
 * @author shahid foy
 */
public class TeamsWebhookException extends Exception {

  /**
   * Teams Webhook Exception Constructor
   * @param message message
   */
  public TeamsWebhookException(String message) { super(message); }

  /**
   * Teams Webhook Exception Constructor
   * @param message message
   * @param cause cause
   */
  public TeamsWebhookException(String message, Throwable cause) { super(message, cause); }
}

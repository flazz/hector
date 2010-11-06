package me.prettyprint.cassandra.service;

/**
 * What should the client do if a call to cassandra node fails and we suspect that the node is
 * down. (e.g. it's a communication error, not an application error).
 *
 * {@value #FAIL_FAST} will return the error as is to the user and not try anything smart
 *
 * {@value #ON_FAIL_TRY_ONE_NEXT_AVAILABLE} will try one more random server before returning to the
 * user with an error
 *
 * {@value #ON_FAIL_TRY_ALL_AVAILABLE} will try all available servers in the cluster before giving
 * up and returning the communication error to the user.
 *
 */
public enum FailoverPolicy {

  /** On communication failure, just return the error to the client and don't retry */
  FAIL_FAST(0, 0),
  /** On communication error try one more server before giving up */
  ON_FAIL_TRY_ONE_NEXT_AVAILABLE(1, 0),
  /** On communication error try all known servers before giving up */
  ON_FAIL_TRY_ALL_AVAILABLE(Integer.MAX_VALUE - 1, 0);

  public final int numRetries;

  public final int sleepBetweenHostsMilli;

  FailoverPolicy(int numRetries, int sleepBwHostsMilli) {
    this.numRetries = numRetries;
    sleepBetweenHostsMilli = sleepBwHostsMilli;
  }
}

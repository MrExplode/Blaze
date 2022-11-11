package me.sunstorm.blaze;

/**
 * Represents an ease.
 */
@FunctionalInterface
public interface Ease {
    /**
     * Applies this ease to the given progress.
     *
     * @param progress the current progress value
     * @return the ease value
     */
    double apply(double progress);
}

package Controller;

/**
 * Enumeration of all possible operations that can be performed on the animal data.
 * This includes filtering operations by various animal attributes and sorting operations.
 */
public enum Operation {
    /** Filter animals by their type (dog, cat, etc.). */
    FILTER_BY_TYPE,          // Filter by animal type (dog, cat, etc.)
    
    /** Filter animals by their specific breed/species. */
    FILTER_BY_SPECIES,       // Filter by breed of the animal
    
    /** Filter animals by their size (micro, small, medium, big). */
    FILTER_BY_SIZE,          // Filter by size (micro, small, medium, big)
    
    /** Filter animals by their pattern (solid, tabby, etc.). */
    FILTER_BY_PATTERN,       // Filter by pattern (solid or tabby)
    
    /** Filter animals by their color (dark, light, mix). */
    FILTER_BY_COLOR,         // Filter by animal color (dark or light or mix)
    
    /** Filter animals by their gender (female, male, unclear). */
    FILTER_BY_GENDER,        // Filter by gender (female, male, unclear)
    
    /** Filter animals by their age category (baby, young, adult, old). */
    FILTER_BY_AGE,           // Filter by age (baby, young, adult, old)
    
    /** Filter animals by their location area (Seattle, Bellevue, etc.). */
    FILTER_BY_AREA,          // Filter by location (Seattle, Bellevue, etc.)

    /** Sort animals by date in ascending order. */
    SORT_BY_DATE_ASC,        // Sort by Date ascending
    
    /** Sort animals by date in descending order. */
    SORT_BY_DATE_DESC        // Sort by Date descending
}

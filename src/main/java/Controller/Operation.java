package Controller;

public enum Operation {
    // Filter operations
    FILTER_BY_TYPE,          // Filter by animal type (dog, cat, etc.)
    FILTER_BY_SPECIES,       // Filter by breed of the animal
    FILTER_BY_SIZE,          // Filter by size (micro, small, medium, big)
    FILTER_By_PATTERN,       // Filter by pattern (solid or tabby)
    FILTER_BY_COLOR,         // Filter by animal color (dark or light or mix)
    FILTER_BY_GENDER,        // Filter by gender (female, male, unclear)
    FILTER_BY_AGE,           // Filter by age (baby, young, adult, old)
    FILTER_BY_LOCATION,      // Filter by location (Seattle, Bellevue, etc.)

    // Sort operations
    SORT_BY_Date_ASC,        // Sort by Date ascending
    SORT_BY_Date_DESC,       // Sort by Date descending
    SORT_BY_SIZE_ASC,        // Sort by size ascending
    SORT_BY_SIZE_DESC,       // Sort by size descending
}

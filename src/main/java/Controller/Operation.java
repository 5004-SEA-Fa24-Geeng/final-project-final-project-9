package Controller;

public enum Operation {
    // Filter operations
    FILTER_BY_TYPE,          // Filter by animal type (dog, cat, etc.)
    FILTER_BY_LOCATION,      // Filter by location (Seattle, Bellevue, etc.)
    FILTER_BY_SIZE,          // Filter by size (micro, small, medium, big)
    FILTER_BY_GENDER,        // Filter by gender (female, male, unclear)
    FILTER_BY_AGE,           // Filter by age (baby, young, adult, old)
    
    // Search operations
    SEARCH_BY_KEYWORD,       // Keyword search
    SEARCH_BY_COLOR,         // Search by color
    SEARCH_BY_PATTERN,       // Search by pattern
    SEARCH_BY_SPECIES,       // Search by species
    
    // Sort operations
    SORT_BY_TIME_ASC,        // Sort by time ascending
    SORT_BY_TIME_DESC,       // Sort by time descending
    SORT_BY_LOCATION_ASC,    // Sort by location ascending
    SORT_BY_LOCATION_DESC,   // Sort by location descending
    SORT_BY_SIZE_ASC,        // Sort by size ascending
    SORT_BY_SIZE_DESC,       // Sort by size descending
    
    // Location operations
    FIND_NEARBY,             // Find nearby animals
    CALCULATE_DISTANCE       // Calculate distance
}

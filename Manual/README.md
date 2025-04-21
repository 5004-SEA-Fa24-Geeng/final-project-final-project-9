# Manual 

You will update this manual and add any files here that you need for an 'application' manual for your program. Make sure to include screenshots of various features. 


**An application "manual" that highlights how to use the application, ideally with screen shots. The manual should be stored in the ../manual directory.**

# Stray Animal Spotted - User Manual

### 1. Introduction
Stray Animal Spotted is a desktop application designed to help track and manage information about stray animals. The application allows users to report sightings, view animal information, and manage animal data through various features.

### 2. Getting Started

#### 2.1 Launching the Application
Run `main` class to start the application. The main view is like this with all the information on animals loaded.
   ![Main Application Window](run_main.png)
### 3. Realized Features

#### 3.1 List View
The List View tab displays animals in a list format with the following information: Animal Type, Breed, Date Seen, Time Seen, Area, Address
![Main Application Window](animal_list.jpg)
- Click `View Details` to open a detailed view of the animal
![Main Application Window](animal_list_1.jpeg)
Animal details include
![Main Application Window](detail.jpg)

  
#### 3.2 Build a list 
- "Add to List": Add the animal to your selected list.
  ![Main Application Window](animal_list_2.png)
  For convenience, click "Add all" button to add all the animals to your selected list.
  ![Main Application Window](add_all.png)
  Animals that have been added will show in the below `Selected Animals` panel.
  ![Main Application Window](add_to_list.jpg)
- Click specific piece of animal information in the `Selected Animals` list, which has the same effect with `View Details` button to open `Animal Detail` window.
- Click the `Delete` button, this piece of animal information can be removed from the list.
  ![Main Application Window](delete.jpg)

#### 3.3 Filtering Animals
Use the filter panel to narrow down the animal list:
![Main Application Window](filter_panel.png)
1. Select filter criteria from dropdown menus:
    - Animal Type (Dog, Cat, Bird, etc.)
    - Breed (specific to animal type)
    - Size (Small, Medium, Large)
    - Gender (Male, Female)
    - Pattern (Solid, Striped, Spotted)
    - Color
    - Age (Young, Adult, Senior)
    - City
    - Date Range
2. Click `Apply Filter` to update the list. For example, choose animal type "DOG" and breed "LABRADOR", then click `Apply Filter`, it will show the filtered animals.
Similarly, use `View Details`, `Add To List` `Add all` button to see animal details and add animals to the below `Selected Animals` list.
![Main Application Window](filter_display.png)
3. Click `Reset` to clear all filters, all filter conditions will be removed and all the animals will show in the list panel.
![Main Application Window](reset.png)

#### 3.4 Sorting Animals
To sort the animal list:
1. Select sort order from dropdown:
    - Ascending
    - Descending
2. Click `Sort` button to apply sorting  
For example, as the below image shows, if choose `Ascending`, the animals can be listed by the dates when they were seen in ascending order.
![Main Application Window](sort_by_date.png)

#### 3.5 Map View
The Map View tab shows the locations of all animals in the list on a map:
- Animals are marked with pins on the map
- Click on a pin to open `Animal Details` window and view animal details.
- Use mouse wheel to zoom in/out
- Click and drag to pan the map  
This picture shows all the animals' location on map.
  ![Main Application Window](map_all.jpg)
This picture shows all the filtered dogs' location on map.
  ![Main Application Window](map_dog.jpg)

#### 3.6 Reporting New Animals
To report a new animal:
1. Click the "Report Animal" button at the top
2. Fill in the animal details:
    - Select animal type
    - Choose breed
    - Select size
    - Choose gender
    - Select pattern
    - Choose color
    - Select age
    - Choose city
    - Enter date and time
    - Input address
    - Add location description
    - Write detailed description
    - Upload an image (optional)
3. Click "Submit" to save the report and this animal can be added to our data file.

#### 3.7 Exporting Data
To export the selected animal data, in other words, animals in the below "Selected Animals" panel:
1. Select animals using "Add to List" button. For convenience, you can click "Add all" button to add all the animals in the above panel to the below "Selected Animal" list.
2. Choose export format from dropdown:
    - TXT
    - XML
    - JSON
    - CSV
3. Click "Export" button
4. Data will be exported in selected format and store in the file `final-project-final-project-9`.
![Main Application Window](export.png)

### 4. Tips and Tricks

#### 4.1 Efficient Filtering
- Use multiple filters together for precise results
- Reset filters before starting a new search
- Use the "Add all" button to include all filtered animals in your selection

#### 4.2 Map Navigation
- Double-click to zoom in
- Use mouse wheel for smooth zooming
- Click and hold to pan the map
- Click on markers to view animal details

#### 4.3 Data Management
- Regularly export your data for backup
- Use the "Add to List" feature to create custom groups
- Delete unwanted entries using the delete button

### 5. Troubleshooting

#### 5.1 Common Issues
1. **Images not loading**
    - Ensure image file exists
    - Check file format (supported formats: JPG, PNG)
    - Verify file permissions

2. **Map not displaying**
    - Check internet connection
    - Verify location services are enabled
    - Ensure address format is correct

3. **Export failures**
    - Verify write permissions in target directory
    - Ensure sufficient disk space
    - Check if file is not in use by another program

#### 5.2 Getting Help
If you encounter issues:
1. Check the error message displayed
2. Try resetting the application
3. Contact support with specific error details

### 6. Best Practices

#### 6.1 Data Entry
- Provide accurate location information
- Include clear descriptions
- Upload high-quality images
- Use specific breed names when known

#### 6.2 Data Management
- Regularly export important data
- Keep descriptions up to date
- Remove duplicate entries
- Verify information accuracy

This manual provides a comprehensive guide to using the Stray Animal Spotted application. For additional support or questions, please contact the development team.

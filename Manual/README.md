
## Manual Of STRAY PETS WALL - A Platform for Reporting and Browsing Stray Pets

### 1. Introduction
"Wall" is a very popular online information-sharing platform used within specific communities, like an University. As its name indicates, anyone in the community could post and view information on the "wall".
Similarly, our application allows users to efficiently browse reports of stray animals submitted by people across the Great Seattle area.

### 2. Getting Started

#### 2.1 Launching the Application
Run `Main` class to start the application. The main view is like this with all the information on animals loaded.
<br>If it fails to run, try:
<br>go to the tool bar -> Run -> Edit Configurations -> In `Application/Main`, in `Build and Run` section, choose `java 17` and `-cp final-project-final-project-9.main` -> click Apply and OK -> Run `Main` again
### 3. Realized Features

#### 3.1 List View
The List View tab displays animals in a list format with the following information: Animal Type, Breed, Date Seen, Time Seen, Area, Address
![Main Application Window](screen_shot/animal_list.png)
- Click `View Details` to open a detailed view of the animal
![Main Application Window](screen_shot/animal_list_1.png)
Animal details include type, breed, size, gender, pattern, color, age, date seen, time seen, city, address, location description and animal description.
![Main Application Window](screen_shot/detail.jpg)

  
#### 3.2 Build a list 
- "Add to List": Add the animal to your selected list.
  ![Main Application Window](screen_shot/animal_list_2.png)
  For convenience, click "Add all" button to add all the animals to your selected list.
  ![Main Application Window](screen_shot/add_all.png)
  Animals that have been added will show in the below `Selected Animals` panel.
  ![Main Application Window](screen_shot/selected_animals.png)
- Click specific piece of animal information in the `Selected Animals` list, which has the same effect with `View Details` button to open `Animal Detail` window.
  ![Main Application Window](screen_shot/detail1.png)
- Click the `Delete` button, this piece of animal information can be removed from the list.
  ![Main Application Window](screen_shot/delete.png)

#### 3.3 Filtering Animals
Use the filter panel to narrow down the animal list:
![Main Application Window](screen_shot/filter_panel.png)
1. Select filter criteria from dropdown menus:
    - Animal Type (Dog, Cat, Bird, etc.)
    - Breed (specific to animal type)
    - Size (Small, Medium, Large)
    - Gender (Male, Female)
    - Pattern (Solid, Striped, Spotted)
    - Color (Yellow, Black, White, etc.)
    - Age (Young, Adult, Senior)
    - City (Seattle, LynnWood, Bellevue, etc.)
    - Date Range (within 1 week, within 2 weeks, within 1 month, within 3 months)
2. Click `Apply Filter` to update the list. For example, choose animal type "DOG" and breed "LABRADOR", then click `Apply Filter`, it will show the filtered animals.
Similarly, use `View Details`, `Add To List` `Add all` button to see animal details and add animals to the below `Selected Animals` list.
![Main Application Window](screen_shot/filter_display.png)
3. Click `Reset` to clear all filters, all filter conditions will be removed and all the animals will show in the list panel.
![Main Application Window](screen_shot/reset.png)

#### 3.4 Sorting Animals
To sort the animal list:
1. Select sort order from dropdown (**If no order is chosen, the default order is ascending**):
    - Ascending
    - Descending
2. Click `Sort` button to apply sorting  
For example, as the below image shows, if choose `Ascending`, the animals can be listed by the dates when they were seen in ascending order.
![Main Application Window](screen_shot/sort_by_date.png)

#### 3.5 Map View
Click here to enter `Map View`:<br>(It takes about half minute for the API to access the location of animals at first map initialization)
![Main Application Window](screen_shot/open_map.png)
The Map View tab shows the locations of all animals in the list on a map:
- Animals are marked with pins on the map
- Click on a pin to open `Animal Details` window and view animal details, you will see page like this:
![Main Application Window](screen_shot/detail_map.png)
- Use mouse wheel to zoom in/out
- Click and drag to pan the map  
This picture shows all the animals' locations on map.
  ![Main Application Window](screen_shot/map_all.jpg)
This picture shows all the filtered dogs' locations on map.
  ![Main Application Window](screen_shot/map_dog.jpg)

#### 3.6 Reporting New Animals
To report a new animal:
1. Click the "Report Animal" button at the top
   ![Main Application Window](screen_shot/report.png)
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
2. Choose export format from dropdown (**If no formated is chosen, the default format is XML**):
    - TXT
    - XML
    - JSON
    - CSV
3. Click "Export" button
4. Data will be exported in selected format and store in the file `final-project-final-project-9`.
![Main Application Window](screen_shot/export.png)

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



This manual provides a comprehensive guide to using the Stray Pets Wall application. For additional support or questions, please contact the development team.

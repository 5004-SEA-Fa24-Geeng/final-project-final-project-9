[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/IE0ITl4j)
# Final Project for CS 5004 - (Group 9)
Member:  
Lihan Zhou(https://github.com/Eternity1824)  
Lijun Wan(https://github.com/lietcooper)  
Jiaming Li(https://github.com/jiaming2li)


# 🐶🐱🐰 STRAY PETS WALL - A Platform for Reporting and Browsing Stray Pets

"Wall" is a very popular online information-sharing platform used within specific communities, like an University. As its name indicates, anyone in the community could post and view information on the "wall".
Similarly, our application allows users to efficiently browse reports of stray animals submitted by people across the Greater Seattle area.   
The application is built using the MVC (Model-View-Controller) design pattern and features a graphical user interface developed with Java Swing to support its core functionalities.


## Realized Features

* **Be able to [view](Manual/screen_shot/run_main.png) all items in the collection - in a logical order**
* **Be able to build a list of items from the collection** [(data)](data/posted_animals.csv)
* **Be able to save out that list in `animal_data` .txt, .xml, .json, or .csv.** [(save list)](Manual/screen_shot/export.png)
* Graphical User Interface (Java Swing) [(view)](Manual/screen_shot/map_all.jpg)
* Be able to load in lists of items / previously saved lists, and modify them. [(item example)](Manual/screen_shot/animal_list.png)
* Be able to [sort](Manual/screen_shot/sort_by_date.png) items in the collection
* Be able to [filter](Manual/screen_shot/filter_panel.png) items in the collection
* Have your original item list come from an online API/online access
* Include [images](data/image) for your items
* If your items have geographic data, be able to display them on a [map](Manual/screen_shot/map_all.jpg).



## How to run or compile
Please read the [Manual](Manual/Manual.md) to see how to run or compile the application.   
If there is problem of running, it's most likely a version compatibility issue. Please follow the setting in [Manual](Manual/Manual.md).   
If the issue persists, feel free to reach out to any group member.


## Documentation
UML of Initial Design: [Initial UML](DesignDocuments/InitialDesign/InitialDesign.md)  
UML of Final Design: [Final UML](DesignDocuments/FinalDesign.md)  
Manual: [Manual](Manual/Manual.md)  
GitHub Project board: https://github.com/orgs/5004-SEA-Fa24-Geeng/projects/15  



## Statement on Test Coverage
As our project involves building a GUI using Java Swing, many methods in the View component are not easily testable. This results in the overall test coverage falling below 70%.
We discussed this issue with Chris, she confirmed that the lower test coverage is acceptable in this case, as long as we have adequately tested the remaining logic and non-GUI components of the project.
We have indeed tested all other testable parts of the codebase to ensure correctness and reliability.

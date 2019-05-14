whole scenario is run by runThis.bat

to create new scenario:
create txt file inside the ¨scenarios¨ folder, the file must contain all nodes and their atributes.
Add line ¨This : newScenarioFileName.txt : worldFileName.txt¨ to whichScenario.txt file, where newScenarioFileName is the name of the new scenario file you have just created and worldFileName is name of file in which world is the scenario played in, and add // before previously used scenario line.

Thx to Gavin we now have tool for scenario creation

to create new world:
create txt file inside the ¨world¨ folder, the file must contain all flavor texts needed.
Add line ¨This : scenarioFileName.txt : newWorldFileName.txt¨ to whichScenario.txt file, where scenarioFileName is the name of the scenario file you want to play and newWorldFileName is name of file you have just created, and add // before previously used scenario line.
